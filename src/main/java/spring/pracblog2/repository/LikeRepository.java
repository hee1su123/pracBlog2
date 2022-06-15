package spring.pracblog2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.pracblog2.domain.Post;
import spring.pracblog2.domain.User;
import spring.pracblog2.left.Likes;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByPostAndUser(Post post, User user);
}
