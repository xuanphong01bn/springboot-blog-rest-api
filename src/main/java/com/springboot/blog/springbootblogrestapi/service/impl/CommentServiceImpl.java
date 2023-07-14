package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Comment;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.BlogAPIException;
import com.springboot.blog.springbootblogrestapi.repository.CommentRepository;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.CommentDto;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    private PostRepository postRepository;
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        // retrieve post entity by id

        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id", postId));

        comment.setPost(post);

        // save comments
        commentRepository.save(comment); // truyen id kieu gi cx dc, tai no tu dong tao

        return mapToDTO(comment);
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment ->mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment not be long to post");

        }
        else return mapToDTO(comment);
    }

    @Override
    public CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id", commentId));

        // check if comment exists in post

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);

    }

    private CommentDto mapToDTO(Comment comment) {
        CommentDto commentDto = new CommentDto(comment.getId(), comment.getName(), comment.getEmail(), comment.getBody());
        return commentDto;
    }
    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(comment.getId());
        comment.setName(commentDto.getName());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        return comment;
    }
}
