import { HttpErrorResponse } from '@angular/common/http';

export function errorMessage(error: unknown, fallback: string): string {
  if (error instanceof HttpErrorResponse) {
    if (error.status === 0) {
      return 'De server is niet bereikbaar. Draait de backend?';
    }
    if (error.error && error.error.message) {
      return error.error.message;
    }
  }
  return fallback;
}
