package com.Website.Step2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String postName;
    private String url;
    private String description;
    private String userName;
    private String subName;
    private Integer voteCount;
    private Integer commentCount;
    private String duration;
    public Integer getVoteCount() {
        if (this.voteCount == null) {
            return 0;
        }
        return this.voteCount;
    }
}