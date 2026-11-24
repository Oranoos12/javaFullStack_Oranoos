import { Component, OnInit, computed, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { environment } from '../../../environments/environment';
import { Recipe } from '../../models/recipe';
import { OrderStore } from '../../stores/order.store';
import { RecipeStore } from '../../stores/recipe.store';

@Component({
  selector: 'app-order-new',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './order-new.html'
})
export class OrderNew implements OnInit {
  protected readonly orderStore = inject(OrderStore);
  protected readonly recipeStore = inject(RecipeStore);
  private readonly router = inject(Router);
  private readonly fb = inject(FormBuilder);

  protected readonly chosenRecipes = computed(() => {
    const result: Recipe[] = [];
    for (const recipe of this.recipeStore.recipes()) {
      if (this.orderStore.box().includes(recipe.id)) {
        result.push(recipe);
      }
    }
    return result;
  });

  protected readonly form = this.fb.nonNullable.group({
    persons: [2, [Validators.required, Validators.min(1), Validators.max(8)]],
    slotId: [0, [Validators.required, Validators.min(1)]],
    street: ['', Validators.required],
    number: ['', Validators.required],
    postalCode: ['', Validators.required],
    city: ['', Validators.required]
  });

  ngOnInit(): void {
    this.orderStore.clearError();
    this.orderStore.loadSlots();
    if (this.recipeStore.recipes().length === 0) {
      this.recipeStore.loadWeek(environment.currentWeek);
    }
  }

  protected removeFromBox(recipeId: number): void {
    this.orderStore.toggleRecipe(recipeId);
  }

  protected onSubmit(): void {
    if (this.form.invalid || this.orderStore.boxSize() === 0) {
      this.form.markAllAsTouched();
      return;
    }
    const value = this.form.getRawValue();
    this.orderStore.place({
      order: {
        persons: value.persons,
        slotId: value.slotId,
        address: { street: value.street, number: value.number, postalCode: value.postalCode, city: value.city },
        recipeIds: this.orderStore.box()
      },
      onSuccess: () => this.router.navigateByUrl('/bestellingen')
    });
  }
}
