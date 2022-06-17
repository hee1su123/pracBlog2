package spring.pracblog2.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.pracblog2.domain.Post;
import spring.pracblog2.domain.Timestamped;
import spring.pracblog2.domain.User;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Likes extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public Likes(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}
