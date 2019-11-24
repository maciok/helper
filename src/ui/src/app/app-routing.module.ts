import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { RegistrationComponent } from './registration/registration.component';
import { RequestHelpComponent } from "./request-help/request-help.component";
import { HelpListComponent } from "./help-list/help-list.component";


const routes: Routes = [
  {path: 'dashboard', component: DashboardComponent},
  {path: 'registration', component: RegistrationComponent},
  {path: 'request-help', component: RequestHelpComponent},
  {path: 'help-list', component: HelpListComponent},
  {path: '**', redirectTo: '/dashboard'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
