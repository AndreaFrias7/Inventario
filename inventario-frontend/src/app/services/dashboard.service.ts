import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Dashboard } from '../models/dashboard';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private apiUrl = 'http://localhost:8080/api/dashboard';

  constructor(private http: HttpClient) { }

  obtenerMetricas(stockMinimo: number = 10): Observable<Dashboard> {
    const params = new HttpParams().set('stockMinimo', stockMinimo.toString());
    return this.http.get<Dashboard>(`${this.apiUrl}/metricas`, { params });
  }
}