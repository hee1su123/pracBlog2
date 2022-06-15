package spring.pracblog2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.pracblog2.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postid);
}
