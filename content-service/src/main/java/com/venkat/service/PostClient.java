package com.venkat.service;

import com.venkat.vo.Post;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;
import java.util.Optional;

public interface PostClient {

    @GetExchange("/posts")
    List<Post> findAll();

    @GetExchange("/posts/{id}")
    Optional<Post> findById(@PathVariable Integer id);

    @PostExchange("/posts")
    void create(@RequestBody Post posts);

    @PutExchange("/posts")
    void update(@RequestBody Post posts);

    @DeleteExchange("/posts/{id}")
    void delete(@PathVariable Integer id);
}
