import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Recipe } from '../models/recipe';

@Injectable({ providedIn: 'root' })
export class RecipeService {
  private readonly http = inject(HttpClient);
  private readonly recipesUrl = `${environment.apiUrl}/recipes`;

  findByWeek(week: string): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(this.recipesUrl, { params: { week } });
  }
}
