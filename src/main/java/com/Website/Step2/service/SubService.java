package com.Website.Step2.service;


import com.Website.Step2.dto.SubDto;
import com.Website.Step2.exception.SpringException;
import com.Website.Step2.mapper.SubMapper;
import com.Website.Step2.model.Sub;
import com.Website.Step2.repository.SubRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class SubService {


    private final SubRepository subRepository;
    private final SubMapper subMapper;

    @Transactional
    public SubDto save(SubDto subDto) {
        Sub save = subRepository.save(subMapper.mapDtoToSub(subDto));
        subDto.setId(save.getId());
        return subDto;
    }

    @Transactional(readOnly = true)
    public List<SubDto> getAll() {
        return subRepository.findAll()
                .stream()
                .map(subMapper::mapSubToDto)
                .collect(toList());
    }

    public SubDto getSub(Long id) {
        Sub sub = subRepository.findById(id)
                .orElseThrow(() -> new SpringException("No sub found with ID - " + id));
        return subMapper.mapSubToDto(sub);
    }
    }