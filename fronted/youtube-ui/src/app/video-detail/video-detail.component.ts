import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {VideoService} from "../video.service";

@Component({
  selector: 'app-video-detail',
  templateUrl: './video-detail.component.html',
  styleUrl: './video-detail.component.css'
})
export class VideoDetailComponent implements OnInit{

  videoId!: string
  videoUrl!: string
  videoTitle!: string
  videoDescription!: string
  videoTags: Array<string> = []
  videoAvailable: boolean = false;
  constructor(private activatedRoute: ActivatedRoute, private videoService:VideoService) {
    this.videoId = this.activatedRoute.snapshot.params['videoId'];

    this.videoService.getVideo(this.videoId).subscribe(data => {
      this.videoUrl = data.videoUrl
      this.videoTitle = data.title;
      this.videoDescription = data.description;
      this.videoTags = data.tags;
      this.videoAvailable = true

    })

  }
  ngOnInit():void {
  }

}
