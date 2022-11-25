package com.udemy.blogproject.springbootblogrestapi.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDto {
    private long id;
    //title should not be empty or null, have at least 2 chars
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    @Pattern(regexp="^[A-Za-z]*$",message = "Invalid Input")  // validates that it doesn't have numeric values
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post-description should have at least 10 characters")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
}
