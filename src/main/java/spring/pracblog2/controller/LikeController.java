package spring.pracblog2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.pracblog2.dto.response.ResponseMessage;
import spring.pracblog2.security.UserDetailsImpl;
import spring.pracblog2.service.LikeService;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/api/posts/{postId}/like")
    ResponseEntity<ResponseMessage> postLike(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long postId
    ) {
        likeService.postLike(userDetails, postId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("좋아요"));
    }

    @DeleteMapping("/api/posts/{postId}/like")
    ResponseEntity<ResponseMessage> deleteLike(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long postId
    ) {
        likeService.deleteLike(userDetails, postId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("좋아요 취소"));
    }
}