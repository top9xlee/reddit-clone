package com.Website.Step2.controller;
import com.Website.Step2.dto.PostRequest;
import com.Website.Step2.dto.PostResponse;
import com.Website.Step2.service.PostService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("by-sub/{name}")
    public ResponseEntity<List<PostResponse>> getPostsBySub(@PathVariable String name) {
        return status(HttpStatus.OK).body(postService.getPostsBySub(name));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String name) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(name));
    }
    @GetMapping("by-des/{name}")
    public ResponseEntity<List<PostResponse>> getPostByDescription(@PathVariable String name){
        return status (HttpStatus.OK).body(postService.getPostByDescription(name));
    }
    @GetMapping("by-post/{name}")
    public ResponseEntity<List<PostResponse>> getPostByPost(@PathVariable String name){
        return status (HttpStatus.OK).body(postService.getPostByPost(name));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        postService.delete(id);
        return new ResponseEntity<>(null,
                HttpStatus.valueOf(204));
    }


}
