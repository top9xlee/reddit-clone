package com.Website.Step2.mapper;

import com.Website.Step2.dto.SubDto;
import com.Website.Step2.model.Post;
import com.Website.Step2.model.Sub;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubMapper {


        @Mapping(target = "numberOfPosts", expression = "java(mapPosts(sub.getPosts()))")
        SubDto mapSubToDto(Sub sub);

        default Integer mapPosts(List<Post> numberOfPosts) {
            return numberOfPosts.size();
        }


        @InheritInverseConfiguration
        @Mapping(target = "posts", ignore = true)
        Sub mapDtoToSub(SubDto subDto);
    }
