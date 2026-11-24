import { computed, inject } from '@angular/core';
import { tapResponse } from '@ngrx/operators';
import { patchState, signalStore, withComputed, withMethods, withState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { concatMap, pipe, switchMap, tap } from 'rxjs';
import { Slot } from '../models/delivery';
import { Order, OrderRequest } from '../models/order';
import { DeliveryService } from '../services/delivery.service';
import { errorMessage } from '../services/error-message';
import { OrderService } from '../services/order.service';
import { replaceById } from './replace-by-id';

type OrderState = {
  orders: Order[];
  slots: Slot[];
  box: number[];
  loading: boolean;
  error: string | null;
};

const initialState: OrderState = {
  orders: [],
  slots: [],
  box: [],
  loading: false,
  error: null
};

export const OrderStore = signalStore(
  { providedIn: 'root' },
  withState(initialState),
  withComputed((store) => ({
    boxSize: computed(() => store.box().length)
  })),
  withMethods((store, orderService = inject(OrderService), deliveryService = inject(DeliveryService)) => ({
    toggleRecipe(recipeId: number): void {
      if (store.box().includes(recipeId)) {
        patchState(store, { box: store.box().filter((id) => id !== recipeId) });
      } else {
        patchState(store, { box: [...store.box(), recipeId] });
      }
    },

    clearError(): void {
      patchState(store, { error: null });
    },

    loadOrders: rxMethod<void>(
      pipe(
        tap(() => patchState(store, { loading: true, error: null })),
        switchMap(() =>
          orderService.findMine().pipe(
            tapResponse({
              next: (orders) => patchState(store, { orders }),
              error: (error) => patchState(store, { error: errorMessage(error, 'Je bestellingen konden niet geladen worden.') }),
              finalize: () => patchState(store, { loading: false })
            })
          )
        )
      )
    ),

    loadSlots: rxMethod<void>(
      pipe(
        switchMap(() =>
          deliveryService.findSlots().pipe(
            tapResponse({
              next: (slots) => patchState(store, { slots }),
              error: (error) => patchState(store, { error: errorMessage(error, 'De leverslots konden niet geladen worden.') })
            })
          )
        )
      )
    ),

    place: rxMethod<{ order: OrderRequest; onSuccess: () => void }>(
      pipe(
        tap(() => patchState(store, { loading: true, error: null })),
        concatMap((input) =>
          orderService.place(input.order).pipe(
            tapResponse({
              next: (order) => {
                patchState(store, { orders: [order, ...store.orders()], box: [] });
                input.onSuccess();
              },
              error: (error) => patchState(store, { error: errorMessage(error, 'Bestellen is mislukt.') }),
              finalize: () => patchState(store, { loading: false })
            })
          )
        )
      )
    ),

    cancel: rxMethod<number>(
      pipe(
        tap(() => patchState(store, { error: null })),
        concatMap((orderId) =>
          orderService.cancel(orderId).pipe(
            tapResponse({
              next: (order) => patchState(store, { orders: replaceById(store.orders(), order) }),
              error: (error) => patchState(store, { error: errorMessage(error, 'Annuleren is mislukt.') })
            })
          )
        )
      )
    ),

    reset(): void {
      patchState(store, initialState);
    }
  }))
);
