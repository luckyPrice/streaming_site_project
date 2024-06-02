package com.programming.techie.youtubeclone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Document(value = "Video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    private String id;
    private String title;
    private String description;
    private String userId;
    private AtomicInteger likes = new AtomicInteger(0);
    private AtomicInteger disLikes = new AtomicInteger(0);
    private Set<String> tags;
    private String videoUrl;
    private videostatus Videostatus;
    private AtomicInteger viewCount = new AtomicInteger(0);
    private String thumbnailUrl;
    private List<comment> commentList;

    public void incrementLikes() {
        likes.incrementAndGet();
    }
    public void decreaseLikes() {
       likes.decrementAndGet();
    }

    public void incrementDisLikes() {
        disLikes.incrementAndGet();
    }
    public void decreaseDisLikes() {
        disLikes.decrementAndGet();
    }

    public void increamentViewCount() {
        viewCount.incrementAndGet();
    }
}
