package spring.pracblog2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import spring.pracblog2.domain.Comment;
import spring.pracblog2.domain.FileDb;
import spring.pracblog2.domain.Post;
import spring.pracblog2.dto.request.PostRequestDto;
import spring.pracblog2.repository.CommentRepository;
import spring.pracblog2.repository.FileDbRepository;
import spring.pracblog2.repository.LikeRepository;
import spring.pracblog2.repository.PostRepository;
import spring.pracblog2.security.UserDetailsImpl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {


    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final FileDbRepository fileDbRepository;
    private final LikeRepository likeRepository;


    /* 게시글 및 이미지 저장 */
    @Transactional
    public void save(
            PostRequestDto requestDto,
            UserDetailsImpl userDetails,
            MultipartFile file
    ) throws IOException {
        Post post = new Post(requestDto, userDetails);
        postRepository.save(post);
        if (file != null) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            FileDb fileDb = new FileDb(fileName, file.getContentType(), file.getBytes(), post);
            fileDbRepository.save(fileDb);
        }
    }


    /* 특정 게시글 조회 */
    @Transactional
    public Post findById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다")
        );
        post.setCount();
        return post;
    }


    /* 게시글 전체 조회 */
    public List<Post> findAll() {
        return postRepository.findAll();
    }


    /* 게시글 수정 */
    @Transactional
    public void update(
            PostRequestDto requestDto,
            UserDetailsImpl userDetails,
            MultipartFile file,
            Long postId
    ) throws IOException {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다")
        );
        if (!Objects.equals(post.getUser().getId(), userDetails.getUser().getId())) {
            throw new IllegalArgumentException("게시글의 작성자가 아닙니다");
        }
        post.update(requestDto);
        if (post.getFileDb() != null) {
            FileDb fileDb = fileDbRepository.findById(post.getFileDb().getId()).orElseThrow(
                    () -> new IllegalArgumentException("게시글 찾기 오류")
            );
            if (file != null) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                fileDb.update(fileName, file.getContentType(), file.getBytes(), post);
            }
            else {
                fileDb.setPost(null);
                fileDbRepository.delete(fileDb);
            }
        }
        else {
            if (file != null) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                FileDb fileDb = new FileDb(fileName, file.getContentType(), file.getBytes(), post);
                fileDbRepository.save(fileDb);
            }
        }
    }


    /* 게시글 삭제 */
    @Transactional
    public void delete(UserDetailsImpl userDetails, Long postId) {
        Long userid = userDetails.getUser().getId();
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다")
        );
        if (!post.getUser().getId().equals(userid)) {
            throw new IllegalArgumentException("게시글의 작성자가 아닙니다");
        }
        if (post.getFileDb() != null)
            fileDbRepository.deleteById(post.getFileDb().getId());
        if (!post.getCommentList().isEmpty())
            commentRepository.deleteAll(post.getCommentList());
        if (!post.getLikesList().isEmpty())
            likeRepository.deleteAll(post.getLikesList());
        postRepository.deleteById(postId);
    }

}