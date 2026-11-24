import { Component, OnInit, inject } from '@angular/core';
import { DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Order } from '../../models/order';
import { OrderStore } from '../../stores/order.store';

@Component({
  selector: 'app-orders',
  imports: [RouterLink, DatePipe],
  templateUrl: './orders.html'
})
export class Orders implements OnInit {
  protected readonly orderStore = inject(OrderStore);

  ngOnInit(): void {
    this.orderStore.loadOrders();
  }

  protected canCancel(order: Order): boolean {
    return order.status === 'PLACED' || order.status === 'CONFIRMED';
  }

  protected recipeNames(order: Order): string {
    const names: string[] = [];
    for (const line of order.lines) {
      names.push(line.recipeName);
    }
    return names.join(', ');
  }

  protected cancel(orderId: number): void {
    this.orderStore.cancel(orderId);
  }
}
