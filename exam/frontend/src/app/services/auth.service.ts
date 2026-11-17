import { Injectable, computed, signal } from '@angular/core';
import { environment } from '../../environments/environment';
import { Role, User } from '../models/user';

@Injectable({ providedIn: 'root' }) /* singleton service */
export class AuthService {
  
  private readonly currentUser = signal<User | null>(this.readFromStorage());

  readonly user = this.currentUser.asReadonly();
  readonly isLoggedIn = computed(() => this.currentUser() !== null);

  login(id: string, role: Role): void {
    const user: User = { id, role };
    this.currentUser.set(user);
    sessionStorage.setItem(environment.storageKey, JSON.stringify(user));
  }

  logout(): void {
    this.currentUser.set(null);
    sessionStorage.removeItem(environment.storageKey);
  }

  hasRole(role: Role): boolean {
    const user = this.currentUser();
    return user !== null && user.role === role;
  }

  private readFromStorage(): User | null {
    const text = sessionStorage.getItem(environment.storageKey);
    if (text === null) {
      return null;
    }
    try {
      return JSON.parse(text) as User;
    } catch {
      sessionStorage.removeItem(environment.storageKey);
      return null;
    }
  }
}
