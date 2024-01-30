package com.venkat.controller;

import com.venkat.exception.ContentAPIRequestException;
import com.venkat.service.PostClient;
import com.venkat.vo.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    private static final Logger logger = LoggerFactory.getLogger(ContentController.class);

    private final PostClient postClient;

    //
    public ContentController(PostClient postClient) {
        this.postClient = postClient;
    }

    @GetMapping("/posts")   //  /api/content/posts
    public ResponseEntity<List<Post>> getAll(){
        logger.info("Fetching all the records");
        return ResponseEntity.ok(this.postClient.findAll());
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getById(@PathVariable Integer id){
        logger.info("Fetching the record for {} ", id);
        return ResponseEntity.ok(this.postClient.findById(id).get());
    }

    @PostMapping("/posts")   //  /api/conten/posts
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Post> create(@RequestBody Post post){
        if(post.title().isEmpty() || post.body().isEmpty()){
            throw new ContentAPIRequestException("All fields should have values");
        }
        Post newPost = this.postClient.create(post);
        logger.info("Creating the record for {} ", newPost);
        return ResponseEntity.ok(newPost);
    }

    @PutMapping("/posts")   //  /api/conten/posts
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Post> update(@RequestBody Post post){
        logger.info("Updating the record for {} ", post);
        return ResponseEntity.ok(this.postClient.update(post));
    }

    @DeleteMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id){
        logger.info("Delete the record for {} ", id);
        this.postClient.delete(id);
    }
}
