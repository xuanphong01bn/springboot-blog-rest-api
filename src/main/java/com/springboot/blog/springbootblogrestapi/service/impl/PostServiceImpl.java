package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.PostDto;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository; // extends Java Repository

    @Autowired
    public PostServiceImpl(PostRepository postRepository) { // autowired de tiem thang PostRepository vao do phai lam kieu new()
        this.postRepository = postRepository;
    }

    //lop ke thua interface
    @Override
    public PostDto createPost(PostDto postDto) {
        //convert DTO to entity
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);

        //covert entity to DTO
        PostDto postResponse = mapToDTO(newPost);

        return
                postResponse;
    }

    // GET ALL POST PAGING
    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // create pageable
        Pageable pageale = PageRequest.of(pageNo, pageSize, sort);

        // covert to DTO
        Page<Post> posts = postRepository.findAll(pageale);

        // get content for page object
        List<Post> listOfPost = posts.getContent();

        List<PostDto> content = listOfPost.stream().map(post ->mapToDTO(post)).collect(Collectors.toList());
//        PostResponse postResponse = new PostResponse(content,posts.getNumber(), posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast() );
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    // GET POST BY ID
    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id", id));

        return mapToDTO(post);
    }

    @Override
    // UPDATE POST BY ID
    public PostDto updatePostById(PostDto postDto, Long id) { // postDto la payload
        // get post by ID database
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id", id));
        post.setTitle(postDto.getTitle());
        postDto.setDescription(postDto.getDescription());
        postDto.setContent(postDto.getContent());

        Post postUpdated = postRepository.save(post);

        return mapToDTO(postUpdated);
    }

    @Override
    public PostDto updatePostByIdBody(PostDto postDto) {
        Post post = postRepository.findById(postDto.getId()).orElseThrow(()-> new ResourceNotFoundException("Post","id", postDto.getId()));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post postUpdated = postRepository.save(post);

        return mapToDTO(postUpdated);
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id", id));
        postRepository.delete(post);
    }

    // convert entity to DTO
    private PostDto mapToDTO(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        return postDto;
    }

    // convert DTO to entity
    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
