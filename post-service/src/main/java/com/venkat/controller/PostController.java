package com.venkat.controller;

import com.venkat.service.PostService;
import com.venkat.vo.PostVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostVO> findAll(){
        logger.info("Fetching the all records ");
        return postService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<PostVO> findById(@PathVariable Integer id){
        logger.info("Fetching the record {} ", id);
        return postService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody PostVO post){
        logger.info("Creating the record {} ", post);
        postService.addPost(post);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody PostVO post){
        logger.info("Updating the record {} ", post);
        postService.update(post);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        logger.info("Deleting the record {} ", id);
        postService.remove(id);
    }
}
