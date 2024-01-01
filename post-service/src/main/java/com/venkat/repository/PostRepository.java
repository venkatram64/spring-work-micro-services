package com.venkat.repository;

import com.venkat.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT a FROM Post a order by a.createdAt desc")
    public List<Post> findByPostCreatedAtDesc(Pageable pageable);
}
