package spring.pracblog2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.pracblog2.domain.Comment;
import spring.pracblog2.domain.Post;
import spring.pracblog2.dto.request.CommentRequestDto;
import spring.pracblog2.repository.CommentRepository;
import spring.pracblog2.repository.PostRepository;
import spring.pracblog2.security.UserDetailsImpl;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    /* 댓글 저장 */
    public void save(
            CommentRequestDto requestDto,
            UserDetailsImpl userDetails,
            Long postid
    ) {
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다")
        );
        Comment comment = new Comment(requestDto, userDetails, post);
        commentRepository.save(comment);
    }


    /* 게시글 번호로 댓글 전부 찾기 */
    public List<Comment> findAllByPostId(Long postid) {
        return commentRepository.findAllByPostId(postid);
    }


    /* 댓글 수정 */
    @Transactional
    public void udate(
            CommentRequestDto requestDto,
            UserDetailsImpl userDetails,
            Long commentid
    ) {
        Comment comment = commentRepository.findById(commentid).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다")
        );
        if (!Objects.equals(comment.getId(), userDetails.getUser().getId())) {
            throw new IllegalArgumentException("댓글의 작성자가 아닙니다");
        }
        comment.update(requestDto);
    }


    public void delete(
            UserDetailsImpl userDetails,
            Long commentid
    ) {
        Comment comment = commentRepository.findById(commentid).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다")
        );
        if (!Objects.equals(comment.getId(), userDetails.getUser().getId())) {
            throw new IllegalArgumentException("댓글의 작성자가 아닙니다");
        }
        commentRepository.deleteById(commentid);
    }
}