import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FileSystemFileEntry} from "ngx-file-drop";
import {Observable} from "rxjs";
import {UploadVideoResponse} from "./upload-video/UploadVideoResponse";
import {VideoDto} from "./video-dto";

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) { }

  uploadVideo(fileEntry: File):Observable<UploadVideoResponse> {
    const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name)//requestParam name은 같아야함

    //HTTP Post call to upload the video
    return this.httpClient.post<UploadVideoResponse>("http://localhost:8080/api/videos/", formData);
  }

  uploadTumbnail(fileEntry: File, videoId: string):Observable<string> {
    const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name)//requestParam name은 같아야함
    formData.append('videoId', videoId)

    //HTTP Post call to upload the tumbnail
    return this.httpClient.post("http://localhost:8080/api/videos/thumbnail", formData, {
      responseType: 'text'
    });
  }

  getVideo(videoId: String): Observable<VideoDto>{
    return this.httpClient.get<VideoDto>("http://localhost:8080/api/videos/" + videoId)
  }
}
