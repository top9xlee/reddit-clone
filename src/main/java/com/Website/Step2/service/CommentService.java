package com.Website.Step2.service;


import com.Website.Step2.dto.CommentsDto;
import com.Website.Step2.exception.CommentNotFoundException;
import com.Website.Step2.exception.PostNotFoundException;
import com.Website.Step2.mapper.CommentMapper;
import com.Website.Step2.model.Comment;
import com.Website.Step2.model.NotificationEmail;
import com.Website.Step2.model.Post;
import com.Website.Step2.model.User;
import com.Website.Step2.repository.CommentRepository;
import com.Website.Step2.repository.PostRepository;
import com.Website.Step2.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CommentService {

    private static final String POST_URL = "";
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(authService.getCurrentUser() + " posted a comment on your post." + POST_URL);
        //sendCommentNotification(message, post.getUser());
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    public void updateComment(Long id, CommentsDto commentsDto) {
        Comment comment = commentRepository.findById(commentsDto.getId())
                .orElseThrow(() -> new CommentNotFoundException(commentsDto.getId().toString()));
        commentRepository.save(comment);
    }


    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }
//    public List<CommentsDto> getAllCommentsForPost(Long postId) {
//        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
//        return commentRepository.findByPost(post)
//                .stream()
//                .map(commentMapper::mapToDto).collect(toList());
//    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        var listComments = commentRepository.findByPostAndParent(post, 0)
                .stream()
                .map(comment -> {
                    var view = commentMapper.mapToDto(comment);
                    var dtoList = getListCommentWithParent(post, comment.getId());
                    view.setDtoList(dtoList);
                    return view;
                }).collect(Collectors.toList());
        return listComments;

    }

    private List<CommentsDto> getListCommentWithParent(Post post, long parent) {
        return commentRepository.findByPostAndParent(post, parent)
                .stream().map(item -> {
                    var view = commentMapper.mapToDto(item);
                    var dtoList = getCommentDtoWithLevel(post, item.getId());
                    view.setDtoList(dtoList);
                    return view;
                })
                .collect(toList());

    }

   /* private List<CommentsDto> getCommentDtoWithLevel(Post post, long parent) {
        var result = commentRepository.findByPostAndParent(post, parent)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
        return result;
    }*/
    private List<CommentsDto> getCommentDtoWithLevel(Post post, long parent) {
        return commentRepository.findByPostAndParent(post, parent)
                .stream().map(item -> {
                    var view = commentMapper.mapToDto(item);
                    var dtoList = getCommentDtoWithLevel2(post, item.getId());
                    view.setDtoList(dtoList);
                    return view;
                })
                .collect(toList());
    }

    private List<CommentsDto> getCommentDtoWithLevel2(Post post, long parent) {
        var result = commentRepository.findByPostAndParent(post, parent)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
        return result;
    }

   /*public List<CommentsDto> getAllCommentsForPost(Long postId) {
       Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
       return commentRepository.findByPost(post)
               .stream()
               .map(commentMapper::mapToDto).collect(toList());
   }*/
    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

}