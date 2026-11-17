import { Routes } from '@angular/router';
import { authGuard, roleGuard } from './guards/auth.guard';
import { Login } from './pages/login/login';
import { OrderNew } from './pages/order-new/order-new';
import { Orders } from './pages/orders/orders';
import { Weekmenu } from './pages/weekmenu/weekmenu';
import { Welcome } from './pages/welcome/welcome';

export const routes: Routes = [
  { path: 'login', component: Login },
  { path: '', pathMatch: 'full', component: Welcome, canActivate: [authGuard] },
  { path: 'weekmenu', component: Weekmenu, canActivate: [authGuard] },
  { path: 'bestellen', component: OrderNew, canActivate: [authGuard, roleGuard], data: { role: 'KLANT' } },
  { path: 'bestellingen', component: Orders, canActivate: [authGuard, roleGuard], data: { role: 'KLANT' } },
  { path: '**', redirectTo: '' }
];
