import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {} from 'googlemaps';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  @ViewChild('mapRef', {static: true}) mapElement: ElementRef;
  map: google.maps.Map;

  constructor() { }

  ngOnInit() {
    const mapProperties = {
      center: new google.maps.LatLng(35.2271, -80.8431),
      zoom: 15,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    this.map = new google.maps.Map(this.mapElement.nativeElement, mapProperties);

    var marker = new window['google'].maps.Marker({
      position: {lat: 35.2271, lng: -80.8431},
      map: this.map,
      title: 'Hello World!',
      draggable: false,
      animation: window['google'].maps.Animation.DROP,
    });
  }

}
