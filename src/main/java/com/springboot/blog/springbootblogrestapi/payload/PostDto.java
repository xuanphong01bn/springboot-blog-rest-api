package com.springboot.blog.springbootblogrestapi.payload;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

// thang nay gan giong cai payload
@Data //getter setter .... for all fields
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private String content;

}
