package com.udemy.blogproject.springbootblogrestapi.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;

@Data
@ApiModel(description = "Comment model information")
public class CommentDto {

    @ApiModelProperty(value = "Blog comment id")
    private long id;

    @NotEmpty(message = "Name cannot be empty or null")
    @Min(2)
    @Pattern(regexp="^[A-Za-z]*$",message = "Invalid Input")  // validates that it doesn't have numeric values
    @ApiModelProperty(value = "Blog comment name")
    private String name;

    @NotEmpty(message = "Email is a mandatory field")
    @Email
    @ApiModelProperty(value = "Blog comment email")
    private  String email;

    @NotEmpty(message = "Body cannot be empty or null")
    @Size(min = 10,message = "Minimum 10 chars required")
    @ApiModelProperty(value = "Blog comment body")
    private String body;
}
