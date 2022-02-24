package com.Website.Step2.repository;


import com.Website.Step2.model.Post;
import com.Website.Step2.model.Sub;
import com.Website.Step2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySub(Sub sub);

    List<Post> findByUser(User user);

    List<Post> findAllByDescriptionContaining(String description);

    List<Post> findAllByPostNameContaining(String postName);
}
