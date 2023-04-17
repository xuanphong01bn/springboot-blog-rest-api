package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto); // interface chi co phuong thuc
    // ma ko co phan xu li
    List<PostDto> getAllPosts();
}
