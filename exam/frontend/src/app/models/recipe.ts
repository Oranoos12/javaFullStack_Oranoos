export type Allergen = 'GLUTEN' | 'LACTOSE' | 'NOTEN' | 'VIS' | 'EI' | 'SOJA';

export interface RecipeIngredient {
  ingredientId: number;
  name: string;
  unit: string;
  quantityPerPerson: number;
}

export interface Recipe {
  id: number;
  name: string;
  description: string;
  prepTimeMinutes: number;
  persons: number;
  week: string;
  allergens: Allergen[];
  ingredients: RecipeIngredient[];
}
