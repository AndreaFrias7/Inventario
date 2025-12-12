export interface Producto {
  id?: number;
  nombre: string;
  descripcion?: string;
  precio: number;
  stockActual: number;
  activo?: boolean;
  categoriaId: number;
  categoriaNombre?: string;
}