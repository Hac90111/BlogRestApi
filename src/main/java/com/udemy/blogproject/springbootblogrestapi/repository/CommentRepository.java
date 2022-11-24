package com.udemy.blogproject.springbootblogrestapi.repository;

import com.udemy.blogproject.springbootblogrestapi.entity.Comment;
import com.udemy.blogproject.springbootblogrestapi.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(long postId);
}
