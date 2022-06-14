package spring.pracblog2.dto.response;

import org.springframework.context.annotation.Primary;
import spring.pracblog2.domain.FileDb;
import spring.pracblog2.domain.Post;
import spring.pracblog2.left.Comment;

import java.util.List;

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
        this.data = post.getFileDb().getData();
        this.comments = post.getComments();

    }

}
