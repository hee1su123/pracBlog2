package spring.pracblog2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.pracblog2.domain.FileDb;
import spring.pracblog2.domain.Post;
import spring.pracblog2.dto.request.PostRequestDto;
import spring.pracblog2.dto.response.PostResponseDto;
import spring.pracblog2.left.CommentRepository;
import spring.pracblog2.repository.PostRepository;
import spring.pracblog2.security.UserDetailsImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    /* 사진 없이 게시글 저장 Done
    * requestDto 값과 userDetails 로 Post 생성 후 DB 에 저장
    * */
    public void save(
            PostRequestDto requestDto,
            UserDetailsImpl userDetails
    ) {
        Post post = new Post(requestDto, userDetails);
        postRepository.save(post);
    }


    /* 사진 포함 게시글 저장 Done
    * reqestDto 값과 userDetails 그리고 fileDb(사진) 으로 Post 생성 후 저장
    * */
    public void save(
            PostRequestDto requestDto,
            UserDetailsImpl userDetails,
            FileDb fileDb
    ) {
        Post post = new Post(requestDto, userDetails, fileDb);
        postRepository.save(post);
    }


    /* 아이디로 게시글 찾기 Done
    * count +1
    * */
    public Post findById(Long postid) {
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다")
        );
        post.setCount();
        return post;
    }


    /* 게시글 전체 찾기 Done
    * */
    public List<Post> findAll() {
        return postRepository.findAll();
    }


    public PostResponseDto udate(
            PostRequestDto requestDto,
            UserDetailsImpl userDetails,
            Long postid
    ) {
        Long userid = userDetails.getUser().getId();
        Optional<Post> match = postRepository.findById(postid);
        if (match.isEmpty() || !match.get().getUser().getId().equals(userid)) {
            throw new IllegalArgumentException("게시글의 작성자가 아닙니다");
        }
        Post post = postRepository.save(match.get());
        return new PostResponseDto(post);
    }


    public void delete(UserDetailsImpl userDetails, Long postid) {
        Long userid = userDetails.getUser().getId();
        Optional<Post> match = postRepository.findById(postid);
        if (match.isEmpty() || !match.get().getUser().getId().equals(userid)) {
            throw new IllegalArgumentException("게시글의 작성자가 아닙니다");
        }
        postRepository.deleteById(postid);
    }

}
