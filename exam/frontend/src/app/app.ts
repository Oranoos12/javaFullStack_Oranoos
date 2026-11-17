import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from './services/auth.service';
import { OrderStore } from './stores/order.store';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.html'
})
export class App {
  protected readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  private readonly orderStore = inject(OrderStore);

  protected logout(): void {
    this.auth.logout();
    this.orderStore.reset();
    this.router.navigateByUrl('/login');
  }
}
