import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService, HTTPError } from '../api/api.service';
import { EventRequestDTO, EventResponseDTO } from '../../interfaces/event';


@Injectable({
  providedIn: 'root',
})
export class EventService {
  private endpoint = '/events';

  constructor(private apiService: ApiService) {}

  createEvent(eventData: EventRequestDTO): Observable<EventResponseDTO | HTTPError> {
    return this.apiService.post(this.endpoint, eventData);
  }

}
