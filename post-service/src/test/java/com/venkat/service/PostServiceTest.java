package com.venkat.service;

import com.venkat.model.Post;
import com.venkat.repository.PostRepository;
import com.venkat.vo.PostVO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
class PostServiceTest {


    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;  //SUT

    @Test
    public void createPostTest(){

        //input
        PostVO postVO = new PostVO(null,1, "core java article",
                "core java article explained", new Date(), new Date());

        //output
        Post createdPost = new Post(1, "core java article", "core java article explained");
        createdPost.setId(1);
        createdPost.setCreatedAt(postVO.createdAt());
        createdPost.setModifiedAt(postVO.modifiedAt());

        //when(postRepository.save(any(Post.class))).thenReturn(createdPost);
        when(postRepository.save(any(Post.class))).thenReturn(createdPost);

        Post result = postService.addPost(postVO);

        verify(postRepository).save(any(Post.class));

        assert result != null;

    }

    @Test
    public void addPostTest(){

        long currentTimeInMillis = System.currentTimeMillis();
        // Mock data
        PostVO mockPostVO = new PostVO(1,1, "core java article", "core java article explained",
                new Date(currentTimeInMillis), new Date(currentTimeInMillis));

        Post mockSavedPost = new Post(1, 1, "core java article", "core java article explained");

        mockSavedPost.setCreatedAt(new Date(currentTimeInMillis));
        mockSavedPost.setModifiedAt(new Date(currentTimeInMillis));

        // Mock repository stubbing
        when(postRepository.save(Mockito.any(Post.class))).thenReturn(mockSavedPost);

        // Perform the service method
        Post savedPost = postService.addPost(mockPostVO);

        // Verify that the repository save method was called with the correct argument
        Mockito.verify(postRepository).save(Mockito.any(Post.class));

        // Verify that the returned post is not null
        assertNotNull(savedPost);

        // Verify that the created and modified dates are set
        assertNotNull(savedPost.getCreatedAt());
        assertNotNull(savedPost.getModifiedAt());

        // Verify that the properties of the saved post match the expected values
        assertEquals(mockSavedPost.getId(), savedPost.getId());
        assertEquals(mockSavedPost.getUserId(), savedPost.getUserId());
        assertEquals(mockSavedPost.getTitle(), savedPost.getTitle());
        assertEquals(mockSavedPost.getBody(), savedPost.getBody());
        assertEquals(mockSavedPost.getCreatedAt(), savedPost.getCreatedAt());
        assertEquals(mockSavedPost.getModifiedAt(), savedPost.getModifiedAt());
    }

    @Test
    public void updatePostTest() {

        long currentTimeInMillis = System.currentTimeMillis();
        // Mock data
        PostVO mockPostVO = new PostVO(1, 1,"core java article", "core java article explained",
                new Date(currentTimeInMillis), new Date(currentTimeInMillis));

        Post mockUpdatedPost = new Post(1, 1, "updated title", "updated body");
        mockUpdatedPost.setCreatedAt(new Date(currentTimeInMillis));
        mockUpdatedPost.setModifiedAt(new Date(currentTimeInMillis));

        // Mock repository stubbing
        when(postRepository.save(Mockito.any(Post.class))).thenReturn(mockUpdatedPost);

        // Perform the service method
        Post updatedPost = postService.update(mockPostVO);

        // Verify that the repository save method was called with the correct argument
        Mockito.verify(postRepository).save(Mockito.any(Post.class));

        // Verify that the returned post is not null
        assertNotNull(updatedPost);

        // Verify that the modified date is set
        assertNotNull(updatedPost.getModifiedAt());

        // Verify that the properties of the updated post match the expected values
        assertEquals(mockUpdatedPost.getId(), updatedPost.getId());
        assertEquals(mockUpdatedPost.getUserId(), updatedPost.getUserId());
        assertEquals(mockUpdatedPost.getTitle(), updatedPost.getTitle());
        assertEquals(mockUpdatedPost.getBody(), updatedPost.getBody());
        assertEquals(mockUpdatedPost.getCreatedAt(), updatedPost.getCreatedAt());
        assertEquals(mockUpdatedPost.getModifiedAt(), updatedPost.getModifiedAt());
    }

    @Test
    public void removePostTest() {

        long currentTimeInMillis = System.currentTimeMillis();
        // Mock data
        Integer postId = 1;
        Post mockPost = new Post(1, 1, "core java article", "core java article explained");
        mockPost.setCreatedAt(new Date(currentTimeInMillis));
        mockPost.setModifiedAt(new Date(currentTimeInMillis));

        // Perform the service method
        postService.remove(postId);

        // Verify that the repository deleteById method was called with the correct argument
        Mockito.verify(postRepository).deleteById(postId);
    }

    @Test
    public void testRemovePostNotFound() {
        // Mock data
        Integer postId = 1;
        doThrow(EntityNotFoundException.class).when(postRepository).deleteById(postId);
        // Perform the service method and expect an exception
        assertThrows(EntityNotFoundException.class, () -> postService.remove(postId));
        // Verify that the repository deleteById method was not called
        Mockito.verify(postRepository).deleteById(postId);
    }


    @Test
    public void findAllTest() {

        long currentTimeInMillis = System.currentTimeMillis();
        // Mock data
        Post post1 = new Post(1, 1, "title1", "body1");
        post1.setCreatedAt(new Date(currentTimeInMillis));
        post1.setModifiedAt(new Date(currentTimeInMillis));
        Post post2 = new Post(2, 1, "title2", "body2");
        post2.setCreatedAt(new Date(currentTimeInMillis));
        post2.setModifiedAt(new Date(currentTimeInMillis));

        List<Post> mockPostList = Arrays.asList(post1, post2);

        // Mock repository stubbing
        when(postRepository.findAll()).thenReturn(mockPostList);

        // Perform the service method
        List<PostVO> postVOList = postService.findAll();

        // Verify that the repository findAll method was called
        Mockito.verify(postRepository).findAll();

        // Verify that the returned list is not null and has the correct size
        assertEquals(2, postVOList.size());

        // Verify that the converted PostVO objects have the expected values
        assertEquals(post1.getId(), postVOList.get(0).id());
        assertEquals(post1.getUserId(), postVOList.get(0).userId());
        assertEquals(post1.getTitle(), postVOList.get(0).title());
        assertEquals(post1.getBody(), postVOList.get(0).body());
        assertEquals(post1.getCreatedAt(), postVOList.get(0).createdAt());
        assertEquals(post1.getModifiedAt(), postVOList.get(0).modifiedAt());

        assertEquals(post2.getId(), postVOList.get(1).id());
        assertEquals(post2.getUserId(), postVOList.get(1).userId());
        assertEquals(post2.getTitle(), postVOList.get(1).title());
        assertEquals(post2.getBody(), postVOList.get(1).body());
        assertEquals(post2.getCreatedAt(), postVOList.get(1).createdAt());
        assertEquals(post2.getModifiedAt(), postVOList.get(1).modifiedAt());
    }

    @Test
    public void findByIdTest() {
        long currentTimeInMillis = System.currentTimeMillis();
        // Mock data
        Integer postId = 1;
        Post mockPost = new Post(postId, 1, "core java article", "core java article explained");
        mockPost.setCreatedAt(new Date(currentTimeInMillis));
        mockPost.setModifiedAt(new Date(currentTimeInMillis));

        // Mock repository stubbing
        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));

        // Perform the service method
        Optional<PostVO> postVOOptional = postService.findById(postId);

        // Verify that the repository findById method was called
        Mockito.verify(postRepository).findById(postId);

        // Verify that the returned Optional is not empty
        assertTrue(postVOOptional.isPresent());

        // Verify that the converted PostVO object has the expected values
        PostVO postVO = postVOOptional.get();
        assertEquals(mockPost.getId(), postVO.id());
        assertEquals(mockPost.getUserId(), postVO.userId());
        assertEquals(mockPost.getTitle(), postVO.title());
        assertEquals(mockPost.getBody(), postVO.body());
        assertEquals(mockPost.getCreatedAt(), postVO.createdAt());
        assertEquals(mockPost.getModifiedAt(), postVO.modifiedAt());
    }


}