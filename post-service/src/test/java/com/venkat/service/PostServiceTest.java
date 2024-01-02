package com.venkat.service;

import com.venkat.model.Post;
import com.venkat.repository.PostRepository;
import com.venkat.vo.PostVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    public void createPostTest(){

        //input
        PostVO postVO = new PostVO(null,1, "core java article",
                "core java article explained", new Date(), new Date());
        /*//input
        Post newPost = new Post(1, "core java article", "core java article explained");
        newPost.setCreatedAt(postVO.createdAt());
        newPost.setModifiedAt(postVO.modifiedAt());*/

        //output
        Post createdPost = new Post(1, "core java article", "core java article explained");
        createdPost.setId(1);
        createdPost.setCreatedAt(postVO.createdAt());
        createdPost.setModifiedAt(postVO.modifiedAt());

        when(postRepository.save(any(Post.class))).thenReturn(createdPost);

        Post result = postService.addPost(postVO);

        verify(postRepository).save(any(Post.class));

        assert result != null;

    }

}