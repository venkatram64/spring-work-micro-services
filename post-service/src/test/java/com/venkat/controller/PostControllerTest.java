package com.venkat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.venkat.model.Post;
import com.venkat.service.PostService;
import com.venkat.vo.PostVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(controllers = PostController.class)
class PostControllerTest {

    @MockBean
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createPostTest() throws Exception {
        //input
        PostVO postVO = new PostVO(null,1, "core java article",
                "core java article explained", new Date(), new Date());
        //output
        Post newPost = new Post(postVO.userId(), postVO.title(), postVO.body());
        newPost.setCreatedAt(postVO.createdAt());
        newPost.setModifiedAt(postVO.modifiedAt());

        when(postService.addPost(postVO)).thenReturn(newPost);

        mockMvc.perform(post("/api/posts")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(postVO))
        ).andExpect(MockMvcResultMatchers.status().isOk());

        /*mockMvc.perform(post("/api/posts")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(postVO))
        ).andExpect(MockMvcResultMatchers.content().json(asJsonString(newPost)));*/
    }

    private static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}