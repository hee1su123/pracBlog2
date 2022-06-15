package spring.pracblog2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.pracblog2.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
