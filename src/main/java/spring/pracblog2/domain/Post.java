package spring.pracblog2.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import spring.pracblog2.dto.request.PostRequestDto;
import spring.pracblog2.left.Comment;
import spring.pracblog2.left.Like;
import spring.pracblog2.security.UserDetailsImpl;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long count;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String template;

    @OneToOne(mappedBy = "post")
    private FileDb fileDb;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<Like> likes;

    public Post(PostRequestDto requestDto, UserDetailsImpl userDetails) {

        this.count = 0L;
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.template = requestDto.getTemplate();
        this.user = userDetails.getUser();

    }

    public void setCount() {
        this.count += 1;
    }

    public void update(PostRequestDto requestDto) {

        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.template = requestDto.getTemplate();

    }

}
