package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.payload.PostDto;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto); // interface chi co phuong thuc
    // ma ko co phan xu li
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePostById(PostDto postDto,Long id);

    PostDto updatePostByIdBody(PostDto postDto);

    void deletePostById(Long id);
}
