import { inject } from '@angular/core';
import { tapResponse } from '@ngrx/operators';
import { patchState, signalStore, withMethods, withState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { pipe, switchMap, tap } from 'rxjs';
import { Recipe } from '../models/recipe';
import { errorMessage } from '../services/error-message';
import { RecipeService } from '../services/recipe.service';

type RecipeState = {
  recipes: Recipe[];
  loading: boolean;
  error: string | null;
};

const initialState: RecipeState = {
  recipes: [],
  loading: false,
  error: null
};

export const RecipeStore = signalStore(
  { providedIn: 'root' },
  withState(initialState),
  withMethods((store, recipeService = inject(RecipeService)) => ({
    loadWeek: rxMethod<string>(
      pipe(
        tap(() => patchState(store, { loading: true, error: null })),
        switchMap((week) =>
          recipeService.findByWeek(week).pipe(
            tapResponse({
              next: (recipes) => patchState(store, { recipes }),
              error: (error) => patchState(store, { error: errorMessage(error, 'Het weekmenu kon niet geladen worden.') }),
              finalize: () => patchState(store, { loading: false })
            })
          )
        )
      )
    )
  }))
);
