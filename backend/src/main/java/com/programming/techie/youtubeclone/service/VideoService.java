package com.programming.techie.youtubeclone.service;

import com.programming.techie.youtubeclone.dto.UploadVideoResponse;
import com.programming.techie.youtubeclone.dto.VideoDto;
import com.programming.techie.youtubeclone.model.Video;
import com.programming.techie.youtubeclone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final UserService userService;


    public UploadVideoResponse uploadVideo(MultipartFile multipartFile) {
        //AWS S3 이용
        //데이터 베이스에 비디오 데이터 저장
        String videoUrl = s3Service.uploadFile(multipartFile);
        var video = new Video();
        video.setVideoUrl(videoUrl);

        var savedVideo = videoRepository.save(video);
        return new UploadVideoResponse(savedVideo.getId(), savedVideo.getVideoUrl());
    }

    public VideoDto editVideo(VideoDto videoDto) {
        //find the video by videoID
        var savedVideo = getVideoById(videoDto.getId());
        //Map the videoDto fields to video
        savedVideo.setTitle(videoDto.getTitle());
        savedVideo.setDescription(videoDto.getDescription());
        savedVideo.setTags(videoDto.getTags());
        savedVideo.setThumbnailUrl(videoDto.getThumbnailUrl());
        savedVideo.setVideostatus(videoDto.getVideoStatus());

        videoRepository.save(savedVideo);
        return videoDto;
        //save the video to the database
    }

    public String uploadThumbnail(MultipartFile file, String videoId) {
        Video savedVideo = getVideoById(videoId);
        String thumbnailUrl = s3Service.uploadFile(file);
        savedVideo.setThumbnailUrl(thumbnailUrl);
        videoRepository.save(savedVideo);
        return thumbnailUrl;
    }

    Video getVideoById(String videoId){
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find video by id -" + videoId));
    }

    public VideoDto getVideoDetails(String videoId) {
        Video savedVideo = getVideoById(videoId);

        increaseVideoCount(savedVideo);
        userService.addVideoToHistory(videoId);

       return mapToVideoDto(savedVideo);
    }

    private void increaseVideoCount(Video savedVideo) {
        savedVideo.increamentViewCount();
        videoRepository.save(savedVideo);
    }

    public VideoDto likeVideo(String videoId) {
        //get video by id
        Video videoById = getVideoById(videoId);

        //increment like count

        //like -0 dislike -0
        //like -1 dislike -0
        //lije -0 dislike -0

        //like -0 dislike -1
        //like -1 dislike -0

        //if usetr already liked the video then decrement like count

        if(userService.ifLikedVideo(videoId)){
            videoById.decreaseLikes();
            userService.removeFromLikedVideos(videoId);
        }else if (userService.ifDisLikedVideo(videoId)){
            videoById.decreaseDisLikes();
            userService.removeFromDisLikedVideos(videoId);
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);
        }else {
            videoById.incrementLikes();
            userService.addToLikedVideos(videoId);
        }

        videoRepository.save(videoById);

        return mapToVideoDto(videoById);

    }

    public VideoDto disLikeVideo(String videoId) {
        //get video by id
        Video videoById = getVideoById(videoId);

        //increment like count

        //like -0 dislike -0
        //like -1 dislike -0
        //lije -0 dislike -0

        //like -0 dislike -1
        //like -1 dislike -0

        //if usetr already liked the video then decrement like count

        if(userService.ifDisLikedVideo(videoId)){
            videoById.decreaseDisLikes();
            userService.removeFromDisLikedVideos(videoId);
        }else if (userService.ifLikedVideo(videoId)){
            videoById.decreaseLikes();
            userService.removeFromLikedVideos(videoId);
            videoById.incrementDisLikes();
            userService.addToDisLikedVideos(videoId);
        }else {
            videoById.incrementDisLikes();
            userService.addToDisLikedVideos(videoId);
        }

        videoRepository.save(videoById);

        return mapToVideoDto(videoById);

    }

    private static VideoDto mapToVideoDto(Video videoById) {
        VideoDto videoDto = new VideoDto();
        videoDto.setVideoUrl(videoById.getVideoUrl());
        videoDto.setThumbnailUrl(videoById.getThumbnailUrl());
        videoDto.setId(videoById.getId());
        videoDto.setTitle(videoById.getTitle());
        videoDto.setDescription(videoById.getDescription());
        videoDto.setVideoStatus(videoById.getVideostatus());
        videoDto.setTags(videoById.getTags());
        videoDto.setLikeCount(videoById.getLikes().get());
        videoDto.setDislikeCount(videoById.getDisLikes().get());
        videoDto.setViewCount(videoById.getViewCount().get());
        return videoDto;
    }
}
