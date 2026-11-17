export type Role = 'KLANT' | 'MEDEWERKER' | 'CHAUFFEUR';

export interface User {
  id: string;
  role: Role;
}
