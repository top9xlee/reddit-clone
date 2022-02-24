package com.Website.Step2.mapper;


import com.Website.Step2.dto.PostRequest;
import com.Website.Step2.dto.PostResponse;
import com.Website.Step2.model.Post;
import com.Website.Step2.model.Sub;
import com.Website.Step2.model.User;
import com.Website.Step2.repository.CommentRepository;
import com.Website.Step2.repository.VoteRepository;
import com.Website.Step2.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.marlonlom.utilities.timeago.TimeAgo;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;


    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "sub", source = "sub")
    @Mapping(target = "user",source = "user")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post map(PostRequest postRequest, Sub sub, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subName", source = "sub.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

}