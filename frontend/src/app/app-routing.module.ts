import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './home.page/home.page.component';
import { EventPageComponent } from './event.page/event.page.component';

const routes: Routes = [
  { path: '', component: HomePageComponent },
  { path: 'event', component: EventPageComponent },
  { path: 'event/:eventId', component: EventPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
