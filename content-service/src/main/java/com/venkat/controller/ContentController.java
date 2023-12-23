package com.venkat.controller;

import com.venkat.service.PostClient;
import com.venkat.vo.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    private static final Logger logger = LoggerFactory.getLogger(ContentController.class);

    private final PostClient postClient;

    public ContentController(PostClient postClient) {
        this.postClient = postClient;
    }

    @GetMapping("/posts")
    public List<Post> getAll(){
        logger.info("Fetching all the records");
        return this.postClient.findAll();
    }

    @GetMapping("/posts/{id}")
    public Post getById(@PathVariable Integer id){
        logger.info("Fetching the record for {} ", id);
        return this.postClient.findById(id).get();
    }

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Post post){
        logger.info("Creating the record for {} ", post);
        this.postClient.create(post);
    }

    @PutMapping("/posts")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Post post){
        logger.info("Updating the record for {} ", post);
        this.postClient.update(post);
    }

    @DeleteMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id){
        logger.info("Delete the record for {} ", id);
        this.postClient.delete(id);
    }
}
