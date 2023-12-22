package com.venkat.controller;

import com.venkat.service.PostService;
import com.venkat.vo.PostVO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostVO> findAll(){
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<PostVO> findById(@PathVariable Integer id){
        return postService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody PostVO post){
        postService.addPost(post);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody PostVO post){
        postService.update(post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        postService.remove(id);
    }
}
