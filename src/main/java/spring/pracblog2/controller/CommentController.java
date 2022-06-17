package spring.pracblog2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.pracblog2.dto.response.ResponseMessage;
import spring.pracblog2.dto.request.CommentRequestDto;
import spring.pracblog2.service.CommentService;
import spring.pracblog2.security.UserDetailsImpl;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /* 댓글 작성 */
    @PostMapping("/api/comments/{postId}")
    ResponseEntity<ResponseMessage> postComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long postId,
            @RequestBody CommentRequestDto requestDto
    ) {
        commentService.save(requestDto, userDetails, postId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("댓글 등록 완료"));
    }


    /* 댓글 수정 */
    @PutMapping("/api/comments/{commentId}")
    ResponseEntity<ResponseMessage> updateComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto
    ) {
        commentService.update(requestDto, userDetails, commentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("댓글 수정 완료"));
    }


    /* 댓글 삭제 */
    @DeleteMapping("/api/comments/{commentId}")
    ResponseEntity<ResponseMessage> deleteComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long commentId
    ) {
        commentService.delete(userDetails, commentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("댓글 삭제 완료"));
    }

}
