package spring.pracblog2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.pracblog2.domain.FileDb;
import spring.pracblog2.domain.Post;
import spring.pracblog2.dto.request.PostRequestDto;
import spring.pracblog2.dto.response.PostResponseDto;
import spring.pracblog2.dto.response.ResponseMessage;
import spring.pracblog2.service.CommentService;
import spring.pracblog2.service.PostService;
import spring.pracblog2.security.UserDetailsImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    /* 게시글 작성 */
    @PostMapping("/api/posts")
    public ResponseEntity<ResponseMessage> writePost(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart(value = "key") PostRequestDto requestDto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        postService.save(requestDto, userDetails, file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("파일 업로드 및 게시글 작성 완료"));
    }


    /* 특정 게시글 조회 */
    @GetMapping("/api/posts/{id}")
    public ResponseEntity<PostResponseDto> getPost(
            @PathVariable Long id
    ) {
        Post post = postService.findById(id);
        PostResponseDto responseDto = new PostResponseDto(post);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }


    /* 이미지 조회(이미지 URL) */
    @GetMapping("/api/posts/image/{id}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable Long id
    ) {
        Post post = postService.findById(id);
        FileDb fileDb = post.getFileDb();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", fileDb.getType());
        headers.add("Content-Length", String.valueOf(fileDb.getData().length));
        return new ResponseEntity<byte[]>(fileDb.getData(), headers, HttpStatus.OK);
    }


    /* 게시글 전체 조회 */
    @GetMapping("/api/posts")

    public ResponseEntity<List<PostResponseDto>> getAllPost() {
        List<PostResponseDto> responseDtoList = new ArrayList<>();
        List<Post> postList = postService.findAll();
        for (Post p : postList) {
            responseDtoList.add(new PostResponseDto(p));
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }


    /* 게시글 수정 */
    @PutMapping("/api/posts/{id}")
    ResponseEntity<ResponseMessage> updatePost(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id,
            @RequestPart(value = "key") PostRequestDto requestDto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        postService.update(requestDto, userDetails, file, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("게시글 수정 완료"));
    }


    /* 게시글 삭제 */
    @DeleteMapping("/api/posts/{id}")
    ResponseEntity<ResponseMessage> deletePost(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id
    ) {
        postService.delete(userDetails, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseMessage("게시글 삭제 완료"));
    }

}