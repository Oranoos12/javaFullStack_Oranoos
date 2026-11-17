export type OrderStatus = 'PLACED' | 'CONFIRMED' | 'IN_DELIVERY' | 'DELIVERED' | 'CANCELLED';

export interface Address {
  street: string;
  number: string;
  postalCode: string;
  city: string;
}

export interface OrderLine {
  recipeId: number;
  recipeName: string;
}

export interface Order {
  id: number;
  customerId: string;
  persons: number;
  slotId: number;
  address: Address;
  status: OrderStatus;
  createdAt: string;
  lines: OrderLine[];
}

export interface OrderRequest {
  persons: number;
  slotId: number;
  address: Address;
  recipeIds: number[];
}
