package com.example.post_hanghae.controller;

import com.example.post_hanghae.dto.PostRequestDto;
import com.example.post_hanghae.dto.PostResponseDto;
import com.example.post_hanghae.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //JSON 형식으로 내리는 것
@RequiredArgsConstructor
public class PostController {

    private final PostService postService; // 외부에서 절대 건들이지 못하도록 private!

    // 게시글 작성
    @PostMapping("/post")
    public PostResponseDto creatPost(@RequestBody PostRequestDto postRequestDto) { //Body를 Request 함
        return postService.createPost(postRequestDto);
    }

    //게시글 조회
    @GetMapping("/get")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    //게시글 선택조회

    @GetMapping("/get/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    //게시글 수정
    @PutMapping("/put/{id}") // public 에는 updatePost 이고 return 값은 update ???
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) { //@RequestBody : http의 요청이 그대로 적힘
        return postService.update(id,postRequestDto);
    }


    //게시글 삭제
    @DeleteMapping("/post/{id}")
    public String deleteAll(@PathVariable Long id, @RequestBody String password) { // RequestBody로 password를 입력받으면 그 값을 비교해서 삭제시킴
        return postService.deleteAll(id,password);
    }
}