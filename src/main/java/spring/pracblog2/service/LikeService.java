package spring.pracblog2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.pracblog2.domain.Likes;
import spring.pracblog2.domain.Post;
import spring.pracblog2.domain.User;
import spring.pracblog2.error.ErrorCode;
import spring.pracblog2.error.exception.BusinessException;
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

    public void postLike(UserDetailsImpl userDetails, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BusinessException("해당 게시글을 찾을 수 없습니다", ErrorCode.NO_DATA_IN_DB)
        );
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new BusinessException("해당 사용자를 찾을 수 없습니다", ErrorCode.NO_DATA_IN_DB)
        );
        Optional<Likes> found = likeRepository.findByPostAndUser(post, user);
        if (found.isPresent())
            throw new BusinessException("이미 좋아요를 눌렀습니다", ErrorCode.INVALID_LIKES);
        else {
            post.setLikeByMe(true);
            Likes like = new Likes(post, user);
            likeRepository.save(like);
        }
    }

    public void deleteLike(UserDetailsImpl userDetails, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BusinessException("해당 게시글을 찾을 수 없습니다", ErrorCode.NO_DATA_IN_DB)
        );
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new BusinessException("해당 사용자를 찾을 수 없습니다", ErrorCode.NO_DATA_IN_DB)
        );
        Optional<Likes> found = likeRepository.findByPostAndUser(post, user);
        if (found.isEmpty())
            throw new BusinessException("좋아요를 누른적이 없습니다", ErrorCode.INVALID_LIKES);
        else {
            post.setLikeByMe(false);
            likeRepository.delete(found.get());
        }
    }
}
