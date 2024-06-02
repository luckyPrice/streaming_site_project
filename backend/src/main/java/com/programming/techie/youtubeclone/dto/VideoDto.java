package com.programming.techie.youtubeclone.dto;

import com.programming.techie.youtubeclone.model.Video;
import com.programming.techie.youtubeclone.model.videostatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {
    private String id;
    private String title;
    private String description;
    private Set<String> tags;
    private String videoUrl;
    private videostatus VideoStatus;
    private String thumbnailUrl;
    private Integer likeCount;
    private Integer dislikeCount;
    private Integer ViewCount;
}
