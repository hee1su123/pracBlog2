package spring.pracblog2.left;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.pracblog2.domain.Post;
import spring.pracblog2.domain.Timestamped;
import spring.pracblog2.domain.User;
import spring.pracblog2.security.UserDetailsImpl;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    public Comment(CommentRequestDto requestDto, UserDetailsImpl userDetails, Post post) {
        this.content = requestDto.getContent();
        this.user = userDetails.getUser();
        this.post = post;
    }
}
