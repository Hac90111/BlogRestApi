package com.udemy.blogproject.springbootblogrestapi.controller;

import com.udemy.blogproject.springbootblogrestapi.entity.Comment;
import com.udemy.blogproject.springbootblogrestapi.entity.Post;
import com.udemy.blogproject.springbootblogrestapi.payload.CommentDto;
import com.udemy.blogproject.springbootblogrestapi.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "CRUD REST APIs for Comment resource")
@RestController
@RequestMapping("/api/v1/posts")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/{post_id}/comments")
    @ApiOperation(value ="Create comment REST API")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "post_id") long postId,
                                                    @Valid @RequestBody CommentDto commentDto){
       CommentDto newResponse= commentService.createComment(postId, commentDto);
       return  new ResponseEntity<>(newResponse, HttpStatus.CREATED);
    }


    @GetMapping("/{post_id}/comments")
    @ApiOperation(value ="Get all comments by postId REST API")
    public List<CommentDto> getCommentsByPostId(@PathVariable(value = "post_id") Long postId){
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/{post_id}/comments/{comment_id}")
    @ApiOperation(value ="Get a comment by commentId by postId REST API")
    public ResponseEntity<CommentDto> getCommentByIdByPostId(
            @PathVariable(value = "post_id") Long postId,
            @PathVariable(value = "comment_id") Long commentId){
        CommentDto commentDto= commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @PutMapping("/{post_id}/comments/{comment_id}")
    @ApiOperation(value ="Update a comment by commendId by postId REST API")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable(value = "post_id") Long postId,
            @PathVariable(value = "comment_id") long commentId,
            @Valid @RequestBody CommentDto commentDto){
        CommentDto newResponse= commentService.updateCommentById(postId, commentId, commentDto);
        return new ResponseEntity<>(newResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{post_id}/comments/{comment_id}")
    @ApiOperation(value ="Delete a comment by commentId by postId REST API")
    public ResponseEntity<String> deleteComment(
            @PathVariable(value = "post_id") Long postId,
            @PathVariable(value = "comment_id") Long commentId){
        commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>("String",HttpStatus.OK);
    }
}
