import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { MovimientoStockService } from '../../../services/movimiento-stock.service';
import { ProductoService } from '../../../services/producto.service';
import { MovimientoStock } from '../../../models/movimiento-stock';
import { Producto } from '../../../models/producto';

@Component({
  selector: 'app-movimiento-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    MatSelectModule,
    MatRadioModule,
    MatSnackBarModule,
    MatIconModule  
  ],
  templateUrl: './movimiento-form.component.html',
  styleUrl: './movimiento-form.component.css'
})
export class MovimientoFormComponent implements OnInit {
  movimientoForm: FormGroup;
  productos: Producto[] = [];
  productoSeleccionado: Producto | null = null;

  constructor(
    private fb: FormBuilder,
    private movimientoService: MovimientoStockService,
    private productoService: ProductoService,
    private snackBar: MatSnackBar,
    public dialogRef: MatDialogRef<MovimientoFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.movimientoForm = this.fb.group({
      productoId: [null, [Validators.required]],
      tipo: ['ENTRADA', [Validators.required]],
      cantidad: [1, [Validators.required, Validators.min(1)]],
      motivo: ['', [Validators.maxLength(200)]]
    });
  }

  ngOnInit(): void {
    this.cargarProductos();
    
    if (this.data?.productoId) {
      this.movimientoForm.patchValue({ productoId: this.data.productoId });
    }

    this.movimientoForm.get('productoId')?.valueChanges.subscribe(productoId => {
      this.productoSeleccionado = this.productos.find(p => p.id === productoId) || null;
    });
  }

  cargarProductos(): void {
    this.productoService.listar().subscribe({
      next: (productos) => {
        this.productos = productos.filter(p => p.activo);
        
        if (this.data?.productoId) {
          this.productoSeleccionado = this.productos.find(p => p.id === this.data.productoId) || null;
        }
      },
      error: (error) => {
        console.error('Error al cargar productos:', error);
        this.snackBar.open('Error al cargar productos', 'Cerrar', { duration: 3000 });
      }
    });
  }

  onSubmit(): void {
    if (this.movimientoForm.valid) {
      const movimiento: MovimientoStock = this.movimientoForm.value;

      if (movimiento.tipo === 'SALIDA' && this.productoSeleccionado) {
        if (movimiento.cantidad > this.productoSeleccionado.stockActual) {
          this.snackBar.open(
            `Stock insuficiente. Disponible: ${this.productoSeleccionado.stockActual}`,
            'Cerrar',
            { duration: 5000 }
          );
          return;
        }
      }

      this.movimientoService.registrarMovimiento(movimiento).subscribe({
        next: () => {
          this.snackBar.open('Movimiento registrado correctamente', 'Cerrar', { duration: 3000 });
          this.dialogRef.close(true);
        },
        error: (error) => {
          console.error('Error al registrar movimiento:', error);
          const mensaje = error.error?.message || error.error || 'Error al registrar movimiento';
          this.snackBar.open(mensaje, 'Cerrar', { duration: 5000 });
        }
      });
    } else {
      this.marcarCamposComoTocados();
    }
  }

  marcarCamposComoTocados(): void {
    Object.keys(this.movimientoForm.controls).forEach(key => {
      this.movimientoForm.get(key)?.markAsTouched();
    });
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}