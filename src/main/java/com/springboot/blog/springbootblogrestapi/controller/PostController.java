package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.payload.PostDto;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import com.springboot.blog.springbootblogrestapi.utils.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;


    @Autowired
    public PostController(PostService postService, ModelMapper mapper) {
        this.postService = postService;
    }

    //create blog post api
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    //get all post rest api
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value="sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value="sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir

            ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    //get post by ID
    @GetMapping("{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id) {

        return ResponseEntity.ok(postService.getPostById(id));
    }

    //upadte post by id
    @PutMapping("{id}")
    public ResponseEntity<PostDto> updatePostById(@PathVariable(name="id") Long id, @RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.updatePostById(postDto, id));
    }
    //update post by id in pass in body
    @PutMapping()
    public ResponseEntity<PostDto> updatePostByIdBody(@RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.updatePostById(postDto, postDto.getId()));
    }

    //delete post
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name="id") Long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok("Post delete OK !");
    }
}
