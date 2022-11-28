package com.udemy.blogproject.springbootblogrestapi.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@ApiModel(description = "Post model information")
public class PostDto {

    @ApiModelProperty(value = "Blog post id")
    private long id;
    //title should not be empty or null, have at least 2 chars
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    @Pattern(regexp="^[A-Za-z]*$",message = "Invalid Input")  // validates that it doesn't have numeric values
    @ApiModelProperty(value = "Blog post title")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post-description should have at least 10 characters")
    @ApiModelProperty(value = "Blog post description")
    private String description;

    @NotEmpty
    @ApiModelProperty(value = "Blog post content")
    private String content;
    @ApiModelProperty(value = "Blog post comments")
    private Set<CommentDto> comments;
}
