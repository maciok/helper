import { AfterViewChecked, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { HelpListItem } from "../model/help-list-item.model";
import { HelpCategory } from "../model/help-category.model";
import { Timebox } from "../model/timebox.model";
import {} from 'googlemaps';

@Component({
  selector: 'app-help-list',
  templateUrl: './help-list.component.html',
  styleUrls: ['./help-list.component.scss']
})
export class HelpListComponent implements OnInit {

  items: HelpListItem[] = [];

  // @ViewChild('mapRef', {static: true}) mapElement: ElementRef;
  map: google.maps.Map;

  constructor() { }

  ngOnInit() {
    const sampleItem1 = {
      id: 1,
      category: HelpCategory.TRANSPORT,
      description: 'Some longer description for request from list',
      timeBox: Timebox.DAY,
      localization: '123_123',
      open: false,
    } as HelpListItem;
    const sampleItem2 = {
      id: 2,
      category: HelpCategory.TRANSPORT,
      description: 'Some longer description for request from list',
      timeBox: Timebox.DAY,
      localization: '123_123',
      open: false,
    } as HelpListItem;
    this.items.push(sampleItem1);
    this.items.push(sampleItem2);
  }

  /*ngAfterViewChecked(): void {
    const mapProperties = {
      center: new google.maps.LatLng(35.2271, -80.8431),
      zoom: 15,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    const map = new google.maps.Map(this.mapElement.nativeElement, mapProperties);

    const marker = new window['google'].maps.Marker({
      position: {lat: 35.2271, lng: -80.8431},
      map: map,
      title: 'Hello World!',
      draggable: false,
      animation: window['google'].maps.Animation.DROP,
    });
  }*/

  offerHelp(id: number): void {
    // TODO
  }
}
