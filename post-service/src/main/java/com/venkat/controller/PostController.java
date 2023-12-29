package com.venkat.controller;

import com.venkat.model.Post;
import com.venkat.service.PostService;
import com.venkat.vo.PostVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<PostVO> create(@RequestBody PostVO post){
        logger.info("Creating the record {} ", post);
        Post newPost = postService.addPost(post);
        PostVO postVO = new PostVO(newPost.getId(), newPost.getUserId(), newPost.getTitle(), newPost.getBody());
        return ResponseEntity.ok(postVO);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<PostVO> update(@RequestBody PostVO post){
        logger.info("Updating the record {} ", post);
        Post updatePost = postService.update(post);
        PostVO updatedPostVO = new PostVO(updatePost.getId(), updatePost.getUserId(), updatePost.getTitle(),updatePost.getBody());
        return ResponseEntity.ok(updatedPostVO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        logger.info("Deleting the record {} ", id);
        postService.remove(id);
    }
}
