package com.programming.techie.youtubeclone.dto;

import com.programming.techie.youtubeclone.model.Video;
import com.programming.techie.youtubeclone.model.videostatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String thumnailUrl;
}
