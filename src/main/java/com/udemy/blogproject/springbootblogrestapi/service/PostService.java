package com.udemy.blogproject.springbootblogrestapi.service;

import com.udemy.blogproject.springbootblogrestapi.payload.PostDto;
import com.udemy.blogproject.springbootblogrestapi.payload.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);
}
