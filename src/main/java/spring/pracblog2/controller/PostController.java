package spring.pracblog2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.pracblog2.domain.FileDb;
import spring.pracblog2.domain.Post;
import spring.pracblog2.dto.request.PostRequestDto;
import spring.pracblog2.dto.response.PostResponseDto;
import spring.pracblog2.dto.response.ResponseMessage;
import spring.pracblog2.left.Comment;
import spring.pracblog2.left.CommentService;
import spring.pracblog2.service.FileDbService;
import spring.pracblog2.service.PostService;
import spring.pracblog2.security.UserDetailsImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final FileDbService fileDbService;


    /* 게시글 작성 Not Yet -> 반환값에 추가할 내용 있음..
    *  */
    @PostMapping("/api/posts")
    public ResponseEntity<ResponseMessage> writePost(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart(value = "key") PostRequestDto requestDto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        try {
            postService.save(requestDto, userDetails, file);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("파일 업로드 및 게시글 작성 완료"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("파일 업로드 실패"));
        }
    }


    /* 특정 게시글 조회 Not Yet
     * 이미지
     * 댓글
     * post.cnt +1
     * */
    @GetMapping("/api/posts/{id}")
    public ResponseEntity<byte[]> getPost(
            @PathVariable Long id
    ) {
        Post post = postService.findById(id);
        PostResponseDto responseDto = new PostResponseDto(post);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(responseDto);
        FileDb fileDb = post.getFileDb();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDb.getName() + "\"")
                .body(fileDb.getData());

    }


    /* 게시글 전체 조회 Not Yet
    * 댓글빼고 전체 가져와야함
    * */
    @GetMapping("/api/posts")
    public ResponseEntity<List<PostResponseDto>> getAllPost() {
        List<PostResponseDto> responseDtoList = new ArrayList<>();
        List<Post> postList = postService.findAll();
        for (Post p : postList) {
            responseDtoList.add(new PostResponseDto(p));
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }


    /* 게시글 수정 Not Yet -> 반환값에 추가할 내용 있음
    * 해당 게시글 작성자인지 확인
    * */
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


    /* 게시글 삭제 Not Yet -> 반환값에 추가할 내용 있음
    * */
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