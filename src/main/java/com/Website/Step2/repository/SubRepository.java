package com.Website.Step2.repository;

import com.Website.Step2.model.Sub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SubRepository extends JpaRepository<Sub, Long> {
    Optional<Sub> findByName(String subName);
}