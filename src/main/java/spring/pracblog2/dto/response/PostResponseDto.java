package spring.pracblog2.dto.response;

import lombok.Getter;
import spring.pracblog2.domain.Post;
import spring.pracblog2.domain.Comment;

import java.util.List;

@Getter
public class PostResponseDto {

    private Long id;
    private Long count;
    private String title;
    private String content;
    private String template;
    private byte[] data;
    private List<Comment> comments;


    public PostResponseDto(Post post) {

        this.id = post.getId();
        this.count = post.getCount();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.template = post.getTemplate();
        if (post.getFileDb() != null)
            this.data = post.getFileDb().getData();
        if (post.getComments() != null)
            this.comments = post.getComments();

    }

}
