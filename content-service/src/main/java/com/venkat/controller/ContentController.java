package com.venkat.controller;

import com.venkat.service.PostClient;
import com.venkat.vo.Post;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    private final PostClient postClient;

    public ContentController(PostClient postClient) {
        this.postClient = postClient;
    }

    @GetMapping("/posts")
    public List<Post> getAll(){
        return this.postClient.findAll();
    }

    @GetMapping("/posts/{id}")
    public Post getById(@PathVariable Integer id){
        return this.postClient.findById(id).get();
    }

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Post post){
        this.postClient.create(post);
    }

    @PutMapping("/posts")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Post post){
        this.postClient.update(post);
    }

    @DeleteMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id){
        this.postClient.delete(id);
    }
}
