package com.venkat.service;


import com.venkat.model.Post;
import com.venkat.repository.PostRepository;
import com.venkat.vo.PostVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    //constructor injection
    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public List<PostVO> findAll(){
        List<Post> postList = this.postRepository.findAll();
        List<PostVO> postVOList = postList
                .stream()
                .map(post -> new PostVO(post.getId(), post.getUserId(), post.getTitle(), post.getBody()))
                .collect(Collectors.toList());
        return postVOList;
    }

    public Optional<PostVO> findById(Integer id){
        return this.postRepository
                .findById(id)
                .map(post -> new PostVO(post.getId(),post.getUserId(), post.getTitle(), post.getBody()));
    }

    public void addPost(PostVO post){
        Post p = new Post(post.userId(), post.title(), post.body());
        this.postRepository.save(p);
    }

    public void update(PostVO post){
        Post p = new Post(post.id(), post.userId(), post.title(), post.body());
        this.postRepository.save(p);
    }

    public void remove(Integer id){
        this.postRepository.deleteById(id);
    }

    /*List<PostVO> posts = List.of(
            new PostVO(1,"working", "I am working on task"),
            new PostVO(2, "Exercise", "I am do doing exercise"),
            new PostVO(3, "Java", "I am reading java")
    );

    public List<PostVO> findAll(){
        return posts;
    }

    public Optional<PostVO> findById(Integer id){
        return posts.stream().filter(post -> post.id().equals(id)).findFirst();
    }

    public void add(PostVO post){
        posts.add(post);
    }

    public void update(PostVO post, Integer id){
        Optional<PostVO> first = posts.stream().filter(p -> p.id().equals(id)).findFirst();
        first.ifPresent(p -> posts.set(posts.indexOf(p), post));
    }

    public void remove(Integer id){
        posts.removeIf(p -> p.id().equals(id));
    }*/
}


