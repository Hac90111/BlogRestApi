package com.udemy.blogproject.springbootblogrestapi.service;

import com.udemy.blogproject.springbootblogrestapi.payload.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateCommentById(Long postId, long commentId, CommentDto commentDto);

    void deleteCommentById(Long postId, Long commentId);
}
