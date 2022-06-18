package spring.pracblog2.dto.response;

import lombok.Getter;
import spring.pracblog2.domain.Post;
import spring.pracblog2.domain.Comment;
import spring.pracblog2.domain.Likes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponseDto {

    private Long id;
    private Long viewCount;
    private String nickname;
    private String title;
    private String content;
    private Long template;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private String email;

    private boolean likeByMe;
    private Long likeCount;

    private List<CommentResponseDto> responseDtoList = new ArrayList<>();
    private String imageUrl;


    public PostResponseDto(Post post) {

        this.id = post.getId();
        this.viewCount = post.getCount();
        this.nickname = post.getUser().getNickname();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.template = post.getTemplate();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();

        this.email = post.getUser().getEmail();

        this.likeByMe = post.isLikeByMe();
        this.likeCount = (long) post.getLikesList().size();

        if (post.getCommentList() != null) {
            for (Comment c : post.getCommentList())
                responseDtoList.add(new CommentResponseDto(c));
        }
        if (post.getFileDb() != null)
            this.imageUrl = "http://localhost:8080/api/posts/image/"+post.getId();

    }

}
