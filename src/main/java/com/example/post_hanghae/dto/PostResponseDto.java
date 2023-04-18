package com.example.post_hanghae.dto;

import com.example.post_hanghae.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
//@RequiredArgsConstructor
public class PostResponseDto { // 내가 원하는 것만 보여주기 위해
    private String username; //final - 한번 정하면 절대 변하지 않음 (상수) 왜 final 쓰면 빨간줄이지?
    private String title;
    private String content;
    // private final Stirng password; ->password는 보여주지 않겠다!!
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String msg;
    // statusCode 도 보여질 필요가 없기때문에 지워버렸음


    public PostResponseDto(Post post) { // -> 내부에서 생성자만 건들이도록 public 해줌
        this.username = post.getUsername();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.id = post.getId();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}