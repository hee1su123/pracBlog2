package spring.pracblog2.left;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.pracblog2.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comments/{id}")
    CommentResponsetDto postComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id,
            @RequestBody CommentRequestDto requestDto
    ) {
        return commentService.save(requestDto, userDetails, id);
    }


    @PutMapping("/api/comments/{id}")
    CommentResponsetDto updateComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id,
            @RequestBody CommentRequestDto requestDto
    ) {
        return commentService.udate(requestDto, userDetails, id);
    }


    @DeleteMapping("/api/comments/{id}")
    Boolean deleteComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id
    ) {
        commentService.delete(userDetails, id);
        return true;
    }

}
