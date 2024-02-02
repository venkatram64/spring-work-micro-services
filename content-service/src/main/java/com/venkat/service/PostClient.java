package com.venkat.service;

import com.venkat.vo.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.*;

import java.util.List;
import java.util.Optional;

@HttpExchange
public interface PostClient {//which is interacting to other restful webservice

    @GetExchange("/posts")
    List<Post> findAll();

    @GetExchange("/posts/{id}")
    Optional<Post> findById(@PathVariable Integer id);

    @PostExchange("/posts")
    Post create(@RequestBody Post posts);

    @PutExchange("/posts")
    Post update(@RequestBody Post posts);

    @DeleteExchange("/posts/{id}")
    void delete(@PathVariable Integer id);
}
