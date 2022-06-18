package spring.pracblog2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.pracblog2.domain.Comment;
import spring.pracblog2.domain.Post;
import spring.pracblog2.dto.request.CommentRequestDto;
import spring.pracblog2.error.ErrorCode;
import spring.pracblog2.error.exception.BusinessException;
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
            Long postId
    ) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BusinessException("해당 게시글을 찾을 수 없습니다", ErrorCode.NO_DATA_IN_DB)
        );
        Comment comment = new Comment(requestDto, userDetails, post);
        commentRepository.save(comment);
    }


    /* 댓글 수정 */
    @Transactional
    public void update(
            CommentRequestDto requestDto,
            UserDetailsImpl userDetails,
            Long commentId
    ) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new BusinessException("해당 댓글을 찾을 수 없습니다", ErrorCode.NO_DATA_IN_DB)
        );
        if (!Objects.equals(comment.getId(), userDetails.getUser().getId())) {
            throw new BusinessException("댓글의 작성자가 아닙니다", ErrorCode.NOT_AUTHORIZED);
        }
        comment.update(requestDto);
    }


    public void delete(
            UserDetailsImpl userDetails,
            Long commentId
    ) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new BusinessException("해당 댓글을 찾을 수 없습니다", ErrorCode.NO_DATA_IN_DB)
        );
        if (!Objects.equals(comment.getId(), userDetails.getUser().getId())) {
            throw new BusinessException("댓글의 작성자가 아닙니다", ErrorCode.NOT_AUTHORIZED);
        }
        commentRepository.deleteById(commentId);
    }
}
