package spring.pracblog2.dto.request;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class CommentRequestDto {
    @NotNull(message = "내용을 입력하세요")
    @Max(value = 1000)
    private String content;
}
