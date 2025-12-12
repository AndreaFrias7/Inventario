import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, MatPaginator } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatChipsModule } from '@angular/material/chips';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MovimientoStockService } from '../../../services/movimiento-stock.service';
import { MovimientoStock } from '../../../models/movimiento-stock';
import { MovimientoFormComponent } from '../movimiento-form/movimiento-form.component';

@Component({
  selector: 'app-movimiento-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule,
    MatSnackBarModule,
    MatChipsModule,
    MatTooltipModule
  ],
  templateUrl: './movimiento-list.component.html',
  styleUrl: './movimiento-list.component.css'
})
export class MovimientoListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'fechaMovimiento', 'productoNombre', 'tipo', 'cantidad', 'motivo'];
  movimientos: MovimientoStock[] = [];
  totalElements = 0;
  pageSize = 10;
  pageIndex = 0;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private movimientoService: MovimientoStockService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.cargarMovimientos();
  }

  cargarMovimientos(): void {
    this.movimientoService.listarPaginado(this.pageIndex, this.pageSize).subscribe({
      next: (response) => {
        this.movimientos = response.content;
        this.totalElements = response.totalElements;
      },
      error: (error) => {
        console.error('Error al cargar movimientos:', error);
        this.snackBar.open('Error al cargar movimientos', 'Cerrar', { duration: 3000 });
      }
    });
  }

  onPageChange(event: any): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.cargarMovimientos();
  }

  abrirFormulario(): void {
    const dialogRef = this.dialog.open(MovimientoFormComponent, {
      width: '600px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.cargarMovimientos();
      }
    });
  }
}
