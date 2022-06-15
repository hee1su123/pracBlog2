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
    @PostMapping("/api/comments/{id}")
    ResponseEntity<ResponseMessage> postComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id,
            @RequestBody CommentRequestDto requestDto
    ) {
        commentService.save(requestDto, userDetails, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("댓글 등록 완료"));
    }


    /* 댓글 수정 */
    @PutMapping("/api/comments/{id}")
    ResponseEntity<ResponseMessage> updateComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id,
            @RequestBody CommentRequestDto requestDto
    ) {
        commentService.udate(requestDto, userDetails, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("댓글 수정 완료"));
    }


    /* 댓글 삭제 */
    @DeleteMapping("/api/comments/{id}")
    ResponseEntity<ResponseMessage> deleteComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id
    ) {
        commentService.delete(userDetails, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("댓글 삭제 완료"));
    }

}
