package spring.pracblog2.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.pracblog2.dto.request.PostRequestDto;
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

    @Column
    private Long template;

    @Column(nullable = false)
    private boolean likeByMe;



    @OneToOne(mappedBy = "post")
    private FileDb fileDb;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "post")
    private List<Likes> likesList;

    public Post(PostRequestDto requestDto, UserDetailsImpl userDetails) {

        this.count = 0L;
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.template = requestDto.getTemplate();
        this.user = userDetails.getUser();
        this.likeByMe = false;

    }

    public void update(PostRequestDto requestDto) {

        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.template = requestDto.getTemplate();

    }

    public void setCount() {
        this.count += 1;
    }

    public void setLikeByMe(boolean likeByMe) {
        this.likeByMe = likeByMe;
    }

}
