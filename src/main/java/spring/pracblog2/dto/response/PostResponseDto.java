package spring.pracblog2.dto.response;

import lombok.Getter;
import spring.pracblog2.domain.Post;
import spring.pracblog2.domain.Comment;
import spring.pracblog2.left.Likes;

import java.util.List;

@Getter
public class PostResponseDto {

    private Long id;
    private Long count;
    private String title;
    private String content;
    private String template;
    private byte[] data;
    private List<Comment> commentList;
    private List<Likes> likesList;


    public PostResponseDto(Post post) {

        this.id = post.getId();
        this.count = post.getCount();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.template = post.getTemplate();
        if (post.getFileDb() != null)
            this.data = post.getFileDb().getData();
        if (post.getCommentList() != null)
            this.commentList = post.getCommentList();
        if (post.getLikesList() != null)
            this.likesList = post.getLikesList();

    }

}
