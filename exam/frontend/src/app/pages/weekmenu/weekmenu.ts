import { Component, OnInit, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { environment } from '../../../environments/environment';
import { AuthService } from '../../services/auth.service';
import { OrderStore } from '../../stores/order.store';
import { RecipeStore } from '../../stores/recipe.store';

@Component({
  selector: 'app-weekmenu',
  imports: [RouterLink],
  templateUrl: './weekmenu.html'
})
export class Weekmenu implements OnInit {
  protected readonly recipeStore = inject(RecipeStore);
  protected readonly orderStore = inject(OrderStore);
  protected readonly auth = inject(AuthService);
  protected readonly week = environment.currentWeek;

  ngOnInit(): void {
    this.recipeStore.loadWeek(this.week);
  }

  protected isInBox(recipeId: number): boolean {
    return this.orderStore.box().includes(recipeId);
  }

  protected toggle(recipeId: number): void {
    this.orderStore.toggleRecipe(recipeId);
  }
}
