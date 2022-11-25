package com.udemy.blogproject.springbootblogrestapi.payload;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;

@Data
public class CommentDto {
    private long id;

    @NotEmpty(message = "Name cannnot be empty or null")
    @Min(2)
    @Pattern(regexp="^[A-Za-z]*$",message = "Invalid Input")  // validates that it doesn't have numeric values
    private String name;

    @NotEmpty(message = "Email is a mandatory field")
    @Email
    private  String email;

    @NotEmpty(message = "body cannot be empty or null")
    @Size(min = 10,message = "minimum 10 chars required")
    private String body;
}
