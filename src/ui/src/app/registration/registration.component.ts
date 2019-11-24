import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {User} from "../model/user.model";
import {Role} from "../model/role.model";
import {UserService} from "../user/user.service";
import {switchMap} from "rxjs/operators";
import {NotificationService} from "./notification.service";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  form: FormGroup;

  constructor(private fb: FormBuilder,
              private userService: UserService,
              private router: Router,
              private notificationService: NotificationService,
  ) {
  }

  ngOnInit() {
    const userFromBackend = this.userService.user;
    const user = {
      ...userFromBackend,
      age: null,
      disabilities: [],
      roles: [Role.NECESSITOUS, Role.VOLUNTEER],
    } as User;
    this.form = this.fb.group({
      firstName: this.fb.control(user.firstName, Validators.required),
      lastName: this.fb.control(user.lastName, Validators.required),
      age: this.fb.control(user.age, Validators.required),
      disabilities: this.fb.control(user.disabilities),
      needsHelp: this.fb.control(user.roles.includes(Role.NECESSITOUS)),
      givesHelp: this.fb.control(user.roles.includes(Role.VOLUNTEER)),
    });

    console.log("Subscribing to actions");
  }

  get firstNameControl(): FormControl {
    return this.form.get('firstName') as FormControl;
  }

  get lastNameControl(): FormControl {
    return this.form.get('lastName') as FormControl;
  }

  get ageControl(): FormControl {
    return this.form.get('age') as FormControl;
  }

  get disabilitiesControl(): FormControl {
    return this.form.get('disabilities') as FormControl;
  }

  get needsHelpControl(): FormControl {
    return this.form.get('needsHelp') as FormControl;
  }

  get givesHelpControl(): FormControl {
    return this.form.get('givesHelp') as FormControl;
  }

  onSubmit(): void {
    this.notificationService.subscribeToNotifications();

    if (this.form.valid) {
      const user = {
        firstName: this.firstNameControl.value,
        lastName: this.lastNameControl.value,
        age: this.ageControl.value,
        disabilities: this.disabilitiesControl.value,
        roles: this.createRoles(),
      } as User;
      console.log(user);
      this.userService.save(user).pipe(
        switchMap(() => this.userService.reload())
      ).subscribe(
        () => this.router.navigate(['']),
        (error) => console.log(error)
      )
    }
  }

  private createRoles(): Role[] {
    const roles = [];
    if (this.needsHelpControl.value) {
      roles.push(Role.NECESSITOUS);
    }
    if (this.givesHelpControl.value) {
      roles.push(Role.VOLUNTEER);
    }
    console.log(roles);
    return roles;
  }
}
