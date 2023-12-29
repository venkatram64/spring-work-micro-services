package com.venkat.service;


import com.venkat.model.Post;
import com.venkat.repository.PostRepository;
import com.venkat.vo.PostVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;

    //constructor injection
    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public List<PostVO> findAll(){
        logger.info("Fetching all the records");
        List<Post> postList = this.postRepository.findAll();
        List<PostVO> postVOList = postList
                .stream()
                .map(post -> new PostVO(post.getId(), post.getUserId(), post.getTitle(), post.getBody()))
                .collect(Collectors.toList());
        return postVOList;
    }

    public Optional<PostVO> findById(Integer id){
        logger.info("Fetching record for given id {} ", id);
        return this.postRepository
                .findById(id)
                .map(post -> new PostVO(post.getId(),post.getUserId(), post.getTitle(), post.getBody()));
    }

    public Post addPost(PostVO post){
        logger.info("Saving the record {} ", post);
        Post p = new Post(post.userId(), post.title(), post.body());
        return this.postRepository.save(p);
    }

    public Post update(PostVO post){
        logger.info("Update the record {} ", post);
        Post p = new Post(post.id(), post.userId(), post.title(), post.body());
        return this.postRepository.save(p);
    }

    public void remove(Integer id){
        logger.info("Deleting the record {} ", id);
        this.postRepository.deleteById(id);
    }

}


