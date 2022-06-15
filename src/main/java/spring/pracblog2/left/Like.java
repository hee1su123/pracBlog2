package spring.pracblog2.left;

import lombok.Getter;
import spring.pracblog2.domain.Post;
import spring.pracblog2.domain.User;

import javax.persistence.*;

@Getter
@Entity
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Post post;

    @ManyToOne
    @JoinColumn
    private User user;

}
