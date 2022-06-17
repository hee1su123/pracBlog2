package spring.pracblog2.dto.response;

import lombok.Getter;
import spring.pracblog2.domain.Post;
import spring.pracblog2.domain.Comment;
import spring.pracblog2.domain.Likes;

import java.time.LocalDateTime;
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

    private boolean likeByMe;
    private Long likeCount;

    private List<Comment> commentList;
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

        this.likeByMe = post.isLikeByMe();
        this.likeCount = (long) post.getLikesList().size();

        if (post.getCommentList() != null)
            this.commentList = post.getCommentList();
        if (post.getFileDb() != null)
            this.imageUrl = "http://localhost:8080/api/posts/image/"+post.getId();

    }

}
