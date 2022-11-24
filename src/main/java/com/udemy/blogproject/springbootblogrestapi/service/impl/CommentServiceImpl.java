package com.udemy.blogproject.springbootblogrestapi.service.impl;

import com.udemy.blogproject.springbootblogrestapi.entity.Comment;
import com.udemy.blogproject.springbootblogrestapi.entity.Post;
import com.udemy.blogproject.springbootblogrestapi.exception.BlogAPIException;
import com.udemy.blogproject.springbootblogrestapi.exception.ResourceNotFoundException;
import com.udemy.blogproject.springbootblogrestapi.payload.CommentDto;
import com.udemy.blogproject.springbootblogrestapi.repository.CommentRepository;
import com.udemy.blogproject.springbootblogrestapi.repository.PostRepository;
import com.udemy.blogproject.springbootblogrestapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment= mapToEntity(commentDto);

        //retrieve the post by id
        Post post= postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));

        //set post to comment entity
        comment.setPost(post);
        // comment entity to DB
        Comment newComment= commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        //retrieve comments by postId
        List<Comment> comments= commentRepository.findByPostId(postId);

        //convert comments into commentDtos
       return comments.stream().map((comment)->mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // find the post with id
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "id", postId));
        // find the comment with id

        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateCommentById(Long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "id", postId));
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to this post");
        }else{
            comment.setName(commentDto.getName());
            comment.setEmail(commentDto.getEmail());
            comment.setBody(commentDto.getBody());

            Comment updatedComment = commentRepository.save(comment);
            return mapToDto(updatedComment);
        }
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "id", postId));
        Comment comment=commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to this post");
        }else{
            commentRepository.deleteById(commentId);
        }

    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto= new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }
    private Comment mapToEntity(CommentDto commentdto){
        Comment comment= new Comment();
        comment.setId(commentdto.getId());
        comment.setName(commentdto.getName());
        comment.setEmail(commentdto.getEmail());
        comment.setBody(commentdto.getBody());
        return comment;
    }

}
