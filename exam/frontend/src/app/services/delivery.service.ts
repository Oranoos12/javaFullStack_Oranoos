import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Slot } from '../models/delivery';

@Injectable({ providedIn: 'root' })
export class DeliveryService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/delivery`;

  findSlots(): Observable<Slot[]> {
    return this.http.get<Slot[]>(`${this.baseUrl}/slots`);
  }
}
