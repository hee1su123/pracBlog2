package spring.pracblog2.left;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.pracblog2.domain.Post;
import spring.pracblog2.repository.PostRepository;
import spring.pracblog2.security.UserDetailsImpl;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;



    public CommentResponsetDto save(
            CommentRequestDto requestDto,
            UserDetailsImpl userDetails,
            Long postid
    ) {
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다")
        );
        Comment comment = new Comment(requestDto, userDetails, post);
        Comment obj = commentRepository.save(comment);
        CommentResponsetDto responseDto = new CommentResponsetDto(obj);
        return responseDto;
    }


    /* 게시글 번호로 댓글 전부 찾기 */
    public List<Comment> findAllByPostId(Long postid) {
        return commentRepository.findAllByPostId(postid);
    }


    public CommentResponsetDto udate(
            CommentRequestDto requestDto,
            UserDetailsImpl userDetails,
            Long commentid
    ) {
        Long userid = userDetails.getUser().getId();
        Comment found = commentRepository.findById(commentid).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다")
        );
        if (!found.getId().equals(userid)) {
            throw new IllegalArgumentException("댓글의 작성자가 아닙니다");
        }
        Comment comment = commentRepository.save(found);
        return new CommentResponsetDto(comment);
    }


    public void delete(
            UserDetailsImpl userDetails,
            Long commentid
    ) {
        Long userid = userDetails.getUser().getId();
        Optional<Comment> match = commentRepository.findById(commentid);
        if (match.isEmpty() || !match.get().getUser().getId().equals(userid)) {
            throw new IllegalArgumentException("댓글의 작성자가 아닙니다");
        }
        commentRepository.deleteById(commentid);
    }
}
