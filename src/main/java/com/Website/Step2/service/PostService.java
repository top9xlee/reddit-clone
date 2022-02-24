package com.Website.Step2.service;

import com.Website.Step2.dto.PostRequest;
import com.Website.Step2.dto.PostResponse;
import com.Website.Step2.exception.PostNotFoundException;
import com.Website.Step2.exception.SubNotFoundException;
import com.Website.Step2.mapper.PostMapper;
import com.Website.Step2.model.Post;
import com.Website.Step2.model.Sub;
import com.Website.Step2.model.User;
import com.Website.Step2.repository.PostRepository;
import com.Website.Step2.repository.SubRepository;
import com.Website.Step2.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static java.util.stream.Collectors.toList;
@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final SubRepository subRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
    //tìm hiểu bài viết bằng mô tả
    @Transactional(readOnly = true)
    public List<PostResponse> getPostByDescription(String description){
        List<Post> posts = null;
        if (StringUtils.hasText(description)){
             posts = postRepository.findAllByDescriptionContaining(description);
        }
        else{
             posts = postRepository.findAll();
        };
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }
// tìm bài viết bằng post
    @Transactional(readOnly = true)
    public List<PostResponse> getPostByPost(String postName){
        List<Post> posts = null;
        if (StringUtils.hasText(postName)){
            posts = postRepository.findAllByPostNameContaining(postName);
        }
        else{
            posts = postRepository.findAllByDescriptionContaining(postName);
        };
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    public void save(PostRequest postRequest) {
        Sub sub = subRepository.findByName(postRequest.getSubName())
                .orElseThrow(() -> new SubNotFoundException(postRequest.getSubName()));
        postRepository.save(postMapper.map(postRequest, sub, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySub(String name) {
        Sub sub = subRepository.findByName(name) .orElseThrow(() -> new SubNotFoundException(name.toString()));
        List<Post> posts = postRepository.findAllBySub(sub);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

}
