import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HelpListItem} from "../model/help-list-item.model";
import {HelpCategory} from "../model/help-category.model";
import {Timebox} from "../model/timebox.model";
import {HelpService} from "./help.service";
import {Help} from "../model/help-request.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-help-list',
  templateUrl: './help-list.component.html',
  styleUrls: ['./help-list.component.scss']
})
export class HelpListComponent implements OnInit {

  items: HelpListItem[] = [];
  mockedItem: HelpListItem;

  @ViewChild('mapRef', {static: true}) mapElement: ElementRef;
  map: google.maps.Map;

  constructor(
    private helpService: HelpService,
    private router: Router
  ) {
  }

  ngOnInit() {

    this.mockedItem = {
      id: 999,
      category: HelpCategory.TRANSPORT,
      description: 'Potrzebuję pomocy w pomocy w dotarciu do Starej Iwnicznej',
      timeBox: Timebox.DAY,
      localization: '123_123',
      open: false,
    } as HelpListItem;

    this.loadItems();


    const mapProperties = {
      center: new google.maps.LatLng(52.239381, 21.047299),
      zoom: 15,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    this.map = new google.maps.Map(this.mapElement.nativeElement, mapProperties);

    var marker = new window['google'].maps.Marker({
      position: {lat: 52.239381, lng: 21.047299},
      map: this.map,
      title: 'Hello World!',
      draggable: false,
      animation: window['google'].maps.Animation.DROP,
    });
  }


  offerHelp(item: HelpListItem): void {
    this.helpService.assign(item.id)
      .subscribe(
        (chat: { chatId }) => {
          item.chatId = chat.chatId
          item.state = "RESERVED"
        }
      )
  }

  openChat(chatId: string): void {
    this.router.navigate(["chat"], {queryParams: {id: chatId}})

  }

  private loadItems() {
    this.helpService.load()
      .subscribe(
        (response: Help[]) => this.items = HelpListComponent.itemsFromResponse(response),
        err => console.error(err)
      );
  }


  private static itemsFromResponse(response: Help[]): HelpListItem[] {
    return response.map(h => HelpListComponent.itemFromHelp(h))
  }

  private static itemFromHelp(help: Help): HelpListItem {
    return {
      id: help.id,
      category: help.category,
      description: help.description,
      timeBox: help.helpTimeBox,
      localization: null,
      open: false,
    } as HelpListItem
  }
}
