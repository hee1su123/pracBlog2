package spring.pracblog2.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.pracblog2.dto.request.CommentRequestDto;
import spring.pracblog2.security.UserDetailsImpl;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Post post;

    public Comment(CommentRequestDto requestDto, UserDetailsImpl userDetails, Post post) {
        this.content = requestDto.getContent();
        this.user = userDetails.getUser();
        this.post = post;
    }

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}