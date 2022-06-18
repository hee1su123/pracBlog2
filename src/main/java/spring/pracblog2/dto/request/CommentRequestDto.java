package spring.pracblog2.dto.request;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class CommentRequestDto {
    @NotNull(message = "내용을 입력하세요")
    @Size(min = 1, max = 1000)
    private String content;
}
