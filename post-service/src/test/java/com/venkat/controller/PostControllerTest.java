package com.venkat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.venkat.model.Post;
import com.venkat.service.PostService;
import com.venkat.vo.PostVO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = PostController.class)
class PostControllerTest {

    @MockBean
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createPostTest() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        //formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        //input
        PostVO postVO = new PostVO(null,1, "core java article",
                "core java article explained", new Date(), new Date());
        //output
        Post newPost = new Post(1, postVO.userId(), postVO.title(), postVO.body());
        newPost.setCreatedAt(postVO.createdAt());
        newPost.setModifiedAt(postVO.modifiedAt());
        when(postService.addPost(postVO)).thenReturn(newPost);
        //output
        PostVO newPostVO = new PostVO(1,1, "core java article",
                "core java article explained", postVO.createdAt(), postVO.modifiedAt());

        mockMvc.perform(post("/api/posts")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(postVO))
        ).andExpect(status().isOk());

        /*mockMvc.perform(post("/api/posts")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(postVO))
        ).andExpect(MockMvcResultMatchers.content().json(asJsonString(newPost)));*/

        mockMvc.perform(post("/api/posts")
                .contentType(APPLICATION_JSON)
                .content(asJsonString(postVO))
        ).andExpect(jsonPath("$.id").value(newPostVO.id()))
                .andExpect(jsonPath("$.userId").value(newPostVO.userId()))
                .andExpect(jsonPath("$.title").value(newPostVO.title()))
                .andExpect(jsonPath("$.body").value(newPostVO.body()))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.modifiedAt").exists());

    }

    private static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateTest() throws Exception {

        long currentTimeInMillis = System.currentTimeMillis();
        // Mock data
        PostVO mockPostVO = new PostVO(1, 1, "core java article", "core java article explained",
                new Date(currentTimeInMillis), new Date(currentTimeInMillis));

        Post mockUpdatedPost = new Post(1, 1, "core java article updated", "core java article explained updated");
        mockUpdatedPost.setCreatedAt(new Date(currentTimeInMillis));
        mockUpdatedPost.setModifiedAt(new Date(currentTimeInMillis));

        // Mock service method
        when(postService.update(Mockito.any(PostVO.class))).thenReturn(mockUpdatedPost);

        // Perform PUT request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mockPostVO))) // Convert PostVO to JSON string
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("core java article updated"))
                .andExpect(jsonPath("$.body").value("core java article explained updated"))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.modifiedAt").exists())
                .andReturn();
    }

    @Test
    public void testDelete() throws Exception {
        // Mock data
        Integer postId = 1;

        // Perform DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        // Verify that the service method was called with the correct argument
        verify(postService).remove(postId);
    }

    @Test
    public void findAllTest() throws Exception {

        List<PostVO> list = new ArrayList<>();

        long currentTimeInMillis = System.currentTimeMillis();

        /*Instant instant = Instant.ofEpochMilli(currentTimeInMillis);
        // Create a ZonedDateTime with UTC time zone
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));
        // Create a DateTimeFormatter with the desired pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");*/

        Date currentDate = new Date(currentTimeInMillis);
        PostVO post = new PostVO(1, 1, "core java article",
                "core java article explained", currentDate, currentDate);
        list.add(post);

        PostVO post2 = new PostVO(1, 1, "core java article",
                "core java article explained", currentDate, currentDate);
        list.add(post2);

        when(postService.findAll()).thenReturn(list);

        MvcResult result = mockMvc.perform(get("/api/posts")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                /*.andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("core java article"))
                .andExpect(jsonPath("$[0].body").value("core java article explained"))
                .andExpect(jsonPath("$[0].createdAt").exists())
                .andExpect(jsonPath("$[0].modifiedAt").exists())*/
                .andReturn();

        String expectedJson = convertObjectListToJson(list);
        String actualJson = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedNode = objectMapper.readTree(expectedJson);
        JsonNode actualNode = objectMapper.readTree(actualJson);

        assertEquals(expectedNode.size(), actualNode.size());

    }

    private String convertObjectListToJson(List<?> objectList) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(objectList);
    }


    @Test
    public void testFindById() throws Exception {
        // Mock data
        long currentTimeInMillis = System.currentTimeMillis();
        Integer postId = 1;
        PostVO mockPost = new PostVO(postId, 1,"core java article", "core java article explained",
                new Date(currentTimeInMillis), new Date(currentTimeInMillis));
        Optional<PostVO> optionalPost = Optional.of(mockPost);

        // Mock service stubbing
        when(postService.findById(postId)).thenReturn(optionalPost);

        // Perform GET request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value("core java article"))
                .andExpect(jsonPath("$.body").value("core java article explained"))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.modifiedAt").exists())
                .andReturn();

        // Verify that the response matches the expected JSON
        String actualJson = result.getResponse().getContentAsString();
        // Parse actual JSON and check the content
        ObjectMapper objectMapper = new ObjectMapper();
        PostVO actualPost = objectMapper.readValue(actualJson, PostVO.class);

        assertEquals(mockPost, actualPost);
    }

}