import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { 
    path: 'dashboard', 
    loadComponent: () => import('./components/dashboard/dashboard.component').then(m => m.DashboardComponent) 
  },
  { 
    path: 'categorias', 
    loadComponent: () => import('./components/categorias/categoria-list/categoria-list.component').then(m => m.CategoriaListComponent) 
  },
  { 
    path: 'productos', 
    loadComponent: () => import('./components/productos/producto-list/producto-list.component').then(m => m.ProductoListComponent) 
  },
  { 
    path: 'movimientos', 
    loadComponent: () => import('./components/movimientos/movimiento-list/movimiento-list.component').then(m => m.MovimientoListComponent) 
  }
];