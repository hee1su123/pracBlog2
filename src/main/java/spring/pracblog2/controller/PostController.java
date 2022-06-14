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
import spring.pracblog2.left.Comment;
import spring.pracblog2.left.CommentService;
import spring.pracblog2.service.FileDbService;
import spring.pracblog2.service.PostService;
import spring.pracblog2.security.UserDetailsImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final FileDbService fileDbService;


    /* 게시글 작성 Not Yet -> 반환값에 추가할 내용 있음..
    * 이미지 업로드 유무에 따라 따로 저장
    *  */
    @PostMapping("/api/posts")
    public ResponseEntity<ResponseMessage> writePost(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart(value = "key") PostRequestDto requestDto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        String message = "";
        if (file == null) {
            postService.save(requestDto, userDetails);
            message = "Posted complete";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } else {
            try {
                FileDb fileDb = fileDbService.store(file);
                postService.save(requestDto, userDetails, fileDb);
                message = "Uploaded the file: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload file: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
    }


    /* 특정 게시글 조회 Not Yet -> 반환값에 추가할 내용 있음..
     * 이미지
     * 댓글
     * post.cnt +1
     * */
    @GetMapping("/api/posts/{id}")
    public ResponseEntity<PostResponseDto> getPost(
            @PathVariable Long id
    ) {
        Post post = postService.findById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + post.getFileDb().getName() + "\"")
                .body(new PostResponseDto(post));
    }


    /* 게시글 전체 조회 Not Yet -> 반환값에 추가할 내용 있음..
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

//    @GetMapping("/help")
//    public String help() {
//        System.out.println("help controller");
//        return "HELP";
//    }


    /* 게시글 수정 Not Yet
    * */
//    @PutMapping("/api/posts/{id}")
//    ResponseEntity<ResponseMessage> updatePost(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
//            @PathVariable Long id,
//            @RequestBody PostRequestDto requestDto
//    ) {
//        return postService.udate(requestDto, userDetails, id);
//    }


    /* 게시글 삭제 Not Yet
    * */
//    @DeleteMapping("/api/posts/{id}")
//    ResponseEntity<ResponseMessage> deletePost(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
//            @PathVariable Long id
//    ) {
//        postService.delete(userDetails, id);
//    }

}