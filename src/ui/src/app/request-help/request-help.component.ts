import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { HelpRequestsService } from "../service/help-requests.service";
import { HelpRequest } from "../model/help-request.model";

@Component({
  selector: 'app-request-help',
  templateUrl: './request-help.component.html',
  styleUrls: ['./request-help.component.scss']
})
export class RequestHelpComponent implements OnInit {

  form: FormGroup;

  constructor(private fb: FormBuilder,
              private service: HelpRequestsService,
              private router: Router) {
  }

  ngOnInit() {
    this.form = this.fb.group({
      timebox: this.fb.control('', Validators.required),
      category: this.fb.control('', Validators.required),
      description: this.fb.control(''),
      localizationConsent: this.fb.control(false),
    });
  }

  get timeboxControl(): FormControl {
    return this.form.get('timebox') as FormControl;
  }

  get categoryControl(): FormControl {
    return this.form.get('category') as FormControl;
  }

  get descriptionControl(): FormControl {
    return this.form.get('description') as FormControl;
  }

  get localizationConsentControl(): FormControl {
    return this.form.get('localizationConsent') as FormControl;
  }

  onSubmit(): void {
    if (this.form.valid) {
      const locationConsent = this.localizationConsentControl.value;
      if (locationConsent) {
        navigator.geolocation.getCurrentPosition(
          position => this.saveHelpRequest(position.coords));
      } else {
        this.saveHelpRequest(null);
      }
    }
  }

  private saveHelpRequest(coords: Coordinates): void {
    const req = {
      timebox: this.timeboxControl.value,
      category: this.categoryControl.value,
      description: this.descriptionControl. value,
      coords: coords
    } as HelpRequest;
    this.service.save(req).subscribe(() => this.router.navigate(['']));
  }
}
