package com.Website.Step2.controller;

import com.Website.Step2.dto.SubDto;
import com.Website.Step2.service.SubService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sub")
@AllArgsConstructor
@Slf4j
public class SubController {

    private final SubService subService;


    @PostMapping
    public ResponseEntity<SubDto> createSub(@RequestBody SubDto subDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subService.save(subDto));
    }

    @GetMapping
    public ResponseEntity<List<SubDto>> getAllSubs() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubDto> getSub(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subService.getSub(id));
    }
}
