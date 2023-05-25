package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.payload.CommentDto;
import com.springboot.blog.springbootblogrestapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value="postId" ) long id,@RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(id, commentDto), HttpStatus.CREATED);
    }

    // get all comments on a post
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getAllCommentsInPost(@PathVariable(name="postId") Long postId) {
        return commentService.getCommentByPostId(postId);
    }

    // get spec comment on a post by id of comment
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public CommentDto getCommetById(@PathVariable(name="postId") Long postId, @PathVariable(name="commentId") Long commentId){
        return commentService.getCommentById(postId, commentId);
    }
}
