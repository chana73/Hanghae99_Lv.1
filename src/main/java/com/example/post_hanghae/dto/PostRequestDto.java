package com.example.post_hanghae.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class PostRequestDto {
    private String username;
    private String title;
    private String content;
    private String password; // 문자열일 수도 있으니 Long 보다 String 이 더 효율적?
}