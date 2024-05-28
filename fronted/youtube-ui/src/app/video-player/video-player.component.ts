import {Component, Input, OnInit} from '@angular/core';
import {VgApiService} from '@videogular/ngx-videogular/core';


@Component({
  selector: 'app-video-player',
  templateUrl: './video-player.component.html',
  styleUrl: './video-player.component.css'
})
export class VideoPlayerComponent{

  @Input()
  videoUrl!: string | '';
  constructor() {
  }
}
