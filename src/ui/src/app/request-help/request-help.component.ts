import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { UserService } from "../user/user.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-request-help',
  templateUrl: './request-help.component.html',
  styleUrls: ['./request-help.component.scss']
})
export class RequestHelpComponent implements OnInit {

  form: FormGroup;

  constructor(private fb: FormBuilder,
              private userService: UserService,
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

    }
  }
}
