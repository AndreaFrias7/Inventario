import { Producto } from './producto';

export interface Dashboard {
  totalProductos: number;
  totalCategorias: number;
  productosActivos: number;
  productosInactivos: number;
  productosBajoStock: Producto[];
}