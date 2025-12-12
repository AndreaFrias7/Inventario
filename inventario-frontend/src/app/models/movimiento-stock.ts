export interface MovimientoStock {
  id?: number;
  productoId: number;
  productoNombre?: string;
  tipo: 'ENTRADA' | 'SALIDA';
  cantidad: number;
  motivo?: string;
  fechaMovimiento?: Date;
}