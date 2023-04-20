package com.example.post_hanghae.service;

import com.example.post_hanghae.dto.PostRequestDto;
import com.example.post_hanghae.dto.PostResponseDto;
import com.example.post_hanghae.entity.Post;
import com.example.post_hanghae.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor //fianl이 붙거나 @NotNull이 붙은 필드의 생성자를 자동 생성
public class PostService {

    private final PostRepository postRepository;

    //@Autowired 를 안쓰는 이유 -> private "final"을 꼭 써야하기 때문!(안전)
    // 게시글 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        postRepository.save(post);

        // 이곳에 성공여부 체크 로직 있어야함.
        return new PostResponseDto(post);

    }

    // 전체 게시글 목록 조회
    @Transactional (readOnly=true) // 왜 안되ㅣ지? ->springframework에 있는 Transactional
    public List<PostResponseDto> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();

        return posts.stream().map(post -> new PostResponseDto(post)).toList();


    }

        /*
         List<PostResponseDto> postResponseDto = new ArrayList<>();
         for (Post post : postList) {
          postResponseDto.add(new PostResponseDto(post));
           return postResponseDto;
        */

    // 선택한 게시글 조회
    @Transactional (readOnly=true) // 왜 안되ㅣ지?
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));

        return new PostResponseDto(post);
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto update(Long id, PostRequestDto postRequestDto) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        PostResponseDto postResponseDto = new PostResponseDto(post);
        if (!post.getPassword().equals(postRequestDto.getPassword())) {
            postResponseDto.setMsg("업데이트 실패");
            return postResponseDto;
        }else {
            post.update(postRequestDto);
            postResponseDto = new PostResponseDto(post);// 위에 post는 업데이트가 안되었기 때문에
            postResponseDto.setMsg("업데이트 성공"); // 74,75번줄 순서 중요!! postResponseDto에 post를 업데이트 해버리기때문에 순서가 바뀌면 msg 값이 "Null"이 나옴
            return postResponseDto;
        }
    }
        /* -> 원래 하려고 했던 게시글 수정
            @Transactional
    public ResponseEntity<?> update(Long id, MemoRequestDto requestDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (memo.getPassword().equals(requestDto.getPassword())) {
            memo.update(requestDto);
            return ResponseEntity.ok(new MemoResponseDto(memo));
        } else {
            return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
            //오류 상태코드를 입력오류니 다른 오류를 찾지 말라고 알려주는 것! 리소스 낭비 방지. 입력오류라는 뜻!
        }
    }
        */



    // 게시글 삭제
    @Transactional
    public String deleteAll(Long id, String password) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        if (post.getPassword().equals(password)) { //password를 string 값으로 받았기 때문에 =은 안되고 equals로 비교
            postRepository.deleteById(id);
            return "삭제 성공"; // 다른 값들은 나오지 않고 삭제성공 msg 만 return하기 위해!
        } else {
            return "비밀번호가 다릅니다.";
        }

    }
}


/* -> 원래 하려고 했던 삭제
    @Transactional
    public MemoResponseDto deleteAll(Long id, String password) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글 존재하지 않습니다.")
        );

        if (memo.getPassword().equals(password)) {
            memoRepository.deleteById(id);
            return new MemoResponseDto("삭제되었습니다.", HttpStatus.OK.value());//.value 왜 쓸까?
        } else
            return new MemoResponseDto("비밀번호가 다릅니다.", HttpStatus.UNAUTHORIZED.value());//.value 왜 쓸까?
    }
}

 */