package mx.uam.controller;

import jakarta.validation.Valid;
import mx.uam.model.dto.MovimientoStockDTO;
import mx.uam.service.MovimientoStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/movimientos")
@CrossOrigin(origins = "*")
public class MovimientoStockController {

    @Autowired
    private MovimientoStockService service;

    @GetMapping("/page")
    public ResponseEntity<Page<MovimientoStockDTO>> listarPaginado(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(service.listar(page, size));
    }


    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<MovimientoStockDTO>> listarPorProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(service.listarPorProducto(productoId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<MovimientoStockDTO> buscarPorId(@PathVariable Long id) {
        MovimientoStockDTO dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }


    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody MovimientoStockDTO dto) {
        try {
            MovimientoStockDTO created = service.registrarMovimiento(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}