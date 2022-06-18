package spring.pracblog2.dto.request;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class PostRequestDto {

    @NotNull(message = "제목을 입력하세요")
    @Size(min = 1, max = 25)
    private String title;

    @NotNull(message = "게시글 내용을 입력하세요")
    @Size(min = 1, max = 3000)
    private String content;

    private Long template;

}
