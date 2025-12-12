import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MovimientoStock } from '../models/movimiento-stock';

@Injectable({
  providedIn: 'root'
})
export class MovimientoStockService {
  private apiUrl = 'http://localhost:8080/api/movimientos';

  constructor(private http: HttpClient) { }

  listarPaginado(page: number = 0, size: number = 10): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<any>(`${this.apiUrl}/page`, { params });
  }

  listarPorProducto(productoId: number): Observable<MovimientoStock[]> {
    return this.http.get<MovimientoStock[]>(`${this.apiUrl}/producto/${productoId}`);
  }

  buscarPorId(id: number): Observable<MovimientoStock> {
    return this.http.get<MovimientoStock>(`${this.apiUrl}/${id}`);
  }

  registrarMovimiento(movimiento: MovimientoStock): Observable<MovimientoStock> {
    return this.http.post<MovimientoStock>(this.apiUrl, movimiento);
  }
}