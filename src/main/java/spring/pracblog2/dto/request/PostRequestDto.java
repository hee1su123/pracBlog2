package spring.pracblog2.dto.request;

import lombok.Getter;

@Getter
public class PostRequestDto {

    private String title;
    private String content;
    private Long template;

}
