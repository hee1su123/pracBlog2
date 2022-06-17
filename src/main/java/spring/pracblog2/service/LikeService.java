package spring.pracblog2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.pracblog2.domain.Likes;
import spring.pracblog2.domain.Post;
import spring.pracblog2.domain.User;
import spring.pracblog2.repository.LikeRepository;
import spring.pracblog2.repository.PostRepository;
import spring.pracblog2.repository.UserRepository;
import spring.pracblog2.security.UserDetailsImpl;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public void post(UserDetailsImpl userDetails, Long postid) {
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다")
        );
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다")
        );
        Optional<Likes> found = likeRepository.findByPostAndUser(post, user);
        if (found.isPresent())
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다");
        else {
            Likes like = new Likes(post, user);
            likeRepository.save(like);
        }
    }

    public void delete(UserDetailsImpl userDetails, Long postid) {
        Post post = postRepository.findById(postid).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다")
        );
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 사용자입니다")
        );
        Optional<Likes> found = likeRepository.findByPostAndUser(post, user);
        if (found.isEmpty())
            throw new IllegalArgumentException("좋아요를 누른적이 없습니다");
        else {
            likeRepository.delete(found.get());
        }
    }
}
