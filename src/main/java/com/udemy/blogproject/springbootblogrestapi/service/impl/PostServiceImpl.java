package com.udemy.blogproject.springbootblogrestapi.service.impl;

import com.udemy.blogproject.springbootblogrestapi.entity.Post;
import com.udemy.blogproject.springbootblogrestapi.exception.ResourceNotFoundException;
import com.udemy.blogproject.springbootblogrestapi.payload.PostDto;
import com.udemy.blogproject.springbootblogrestapi.payload.PostResponse;
import com.udemy.blogproject.springbootblogrestapi.repository.PostRepository;
import com.udemy.blogproject.springbootblogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Override
    public PostDto createPost(PostDto postDto) {
        //convert Dto into entity
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);

        //convert entity to Dto
        PostDto postResponse = mapToDto(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize) {
        // create Pageable instance for pagination
        Pageable pageable= PageRequest.of(pageNo,pageSize);
     Page<Post> posts=postRepository.findAll(pageable);

     //get content from page object
        List<Post> postList= posts.getContent();

     List<PostDto> content= postList.stream().map(this::mapToDto).collect(Collectors.toList());

        PostResponse postResponse= new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
      Post post= postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("post", "id", id));
     return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        //get post by id from database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatePost = postRepository.save(post);
        return mapToDto(updatePost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        postRepository.delete(post);
    }

    //covert entity into dto
    private PostDto mapToDto(Post post){
        PostDto postDto= new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setDescription(post.getDescription());
        return postDto;
    }

    //convert dto into entity
    private Post mapToEntity(PostDto postDto){
        Post post= new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

}
