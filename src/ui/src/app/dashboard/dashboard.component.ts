import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { UserService } from "../user/user.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  constructor(private router: Router,
              private userService: UserService) {
  }

  ngOnInit() {
  }

  get name(): string {
    return this.userService.user ? this.userService.user.firstName : null;
  }

  getHelp() {
    this.router.navigate(['/request-help']);
  }

  giveHelp() {
    this.router.navigate(['/help-list']);
  }
}
