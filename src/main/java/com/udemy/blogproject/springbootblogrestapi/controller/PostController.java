package com.udemy.blogproject.springbootblogrestapi.controller;

import com.udemy.blogproject.springbootblogrestapi.payload.PostDto;
import com.udemy.blogproject.springbootblogrestapi.payload.PostResponse;
import com.udemy.blogproject.springbootblogrestapi.service.PostService;
import com.udemy.blogproject.springbootblogrestapi.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "CRUD REST APIs for Post resources")
@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    // create blog post
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "v1/posts")
    @ApiOperation(value = "Create Post RESTapi")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        PostDto post = postService.createPost(postDto);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    //get all post
    @GetMapping(value = "v1/posts")
    @ApiOperation(value = "Get all Post RESTapi")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false)String sortDir
    ){
     return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    // get post by id
    @GetMapping(value = "v1/posts/{id}")
    @ApiOperation(value = "Get Post by id RESTapi")
    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable("id") long id){
     return   ResponseEntity.ok(postService.getPostById(id));
    }


    //update post by id
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "v1/posts/{id}")
    @ApiOperation(value = "Update a Post by id RESTapi")
    public ResponseEntity<PostDto> updatePost(@Valid  @RequestBody PostDto postDto,@PathVariable("id") long id){
       PostDto postResponse= postService.updatePost(postDto, id);
       return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("v1/posts/{id}")
    @ApiOperation(value = "Delete a Post by id RESTapi")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post has been deleted", HttpStatus.OK);
    }

}
