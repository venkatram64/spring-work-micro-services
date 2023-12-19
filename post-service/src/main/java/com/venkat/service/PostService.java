package com.venkat.service;


import com.venkat.vo.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    List<Post> posts = List.of(
            new Post(1,"working", "I am working on task"),
            new Post(2, "Exercise", "I am do doing exercise"),
            new Post(3, "Java", "I am reading java")
    );

    public List<Post> findAll(){
        return posts;
    }

    public Optional<Post> findById(Integer id){
        return posts.stream().filter(post -> post.id().equals(id)).findFirst();
    }

    public void add(Post post){
        posts.add(post);
    }

    public void update(Post post, Integer id){
        Optional<Post> first = posts.stream().filter(p -> p.id().equals(id)).findFirst();
        first.ifPresent(p -> posts.set(posts.indexOf(p), post));
    }

    public void remove(Integer id){
        posts.removeIf(p -> p.id().equals(id));
    }
}


