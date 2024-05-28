import {Component, inject, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatChipEditedEvent, MatChipInputEvent} from "@angular/material/chips";
import {LiveAnnouncer} from "@angular/cdk/a11y";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
@Component({
  selector: 'app-save-video-details',
  templateUrl: './save-video-details.component.html',
  styleUrl: './save-video-details.component.css'
})
export class SaveVideoDetailsComponent implements OnInit{

  saveVideoDetailsForm: FormGroup;
  title: FormControl = new FormControl('');
  description: FormControl = new FormControl('');
  videoStatus: FormControl = new FormControl('')

  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: string[] = [];

  announcer = inject(LiveAnnouncer);

  constructor() {
    this.saveVideoDetailsForm = new FormGroup({
      title: this.title,
      description: this.description,
      videoStatus: this.videoStatus,
    })
  }

  ngOnInit(): void {
  }

  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    // Add our fruit
    if (value) {
      this.tags.push(value);
    }

    // Clear the input value
    event.chipInput!.clear();
  }

  remove(tags: string): void {
    const index = this.tags.indexOf(tags);

    if (index >= 0) {
      this.tags.splice(index, 1);

      this.announcer.announce(`Removed ${tags}`);
    }
  }

  edit(tags: string, event: MatChipEditedEvent) {
    const value = event.value.trim();

    // Remove tags if it no longer has a name
    if (!value) {
      this.remove(tags);
      return;
    }

    // Edit existing tags
    const index = this.tags.indexOf(tags);
    if (index >= 0) {
      this.tags[index] = value;
    }
  }
}
