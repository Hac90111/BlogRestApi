package com.udemy.blogproject.springbootblogrestapi.controller;

import com.udemy.blogproject.springbootblogrestapi.payload.PostDto;
import com.udemy.blogproject.springbootblogrestapi.payload.PostDto2;
import com.udemy.blogproject.springbootblogrestapi.payload.PostResponse;
import com.udemy.blogproject.springbootblogrestapi.service.PostService;
import com.udemy.blogproject.springbootblogrestapi.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    // create blog post
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "v1/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        PostDto post = postService.createPost(postDto);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    //get all post
    @GetMapping(value = "v1/posts")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false)String sortDir
    ){
     return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    // get post by id
    @GetMapping(value = "/posts/{id}",produces = "application/vnd.udemy.v1+json")
    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable("id") long id){
     return   ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping(value = "/posts/{id}",produces = "application/vnd.udemy.v2+json")
    public ResponseEntity<PostDto2> getPostByIdV2(@PathVariable("id") long id){
        PostDto postDto= postService.getPostById(id);
        PostDto2 postDto2= new PostDto2();
        postDto2.setId(postDto.getId());
        postDto2.setTitle(postDto.getTitle());
        postDto2.setDescription(postDto.getDescription());
        postDto2.setContent(postDto.getContent());
        List<String> tags= new ArrayList<>();
        tags.add("Java");
        tags.add("SpringBoot");
        tags.add("AWS");
        postDto2.setTags(tags);
        return   ResponseEntity.ok(postDto2);
    }

    //update post by id
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "v1/posts/update/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid  @RequestBody PostDto postDto,@PathVariable("id") long id){
       PostDto postResponse= postService.updatePost(postDto, id);
       return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("v1/posts/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post has been deleted", HttpStatus.OK);
    }

}
