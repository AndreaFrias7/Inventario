package mx.uam.controller;

import mx.uam.model.dto.DashboardDTO;
import mx.uam.model.dto.ProductoDTO;
import mx.uam.repository.CategoriaRepository;
import mx.uam.repository.ProductoRepository;
import mx.uam.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private CategoriaRepository categoriaRepo;

    @Autowired
    private ProductoService productoService;


    @GetMapping("/metricas")
    public ResponseEntity<DashboardDTO> obtenerMetricas(
            @RequestParam(defaultValue = "10") int stockMinimo) {
        
        DashboardDTO dashboard = new DashboardDTO();
        
        dashboard.setTotalProductos(productoRepo.count());
        
        dashboard.setTotalCategorias(categoriaRepo.count());
        
        dashboard.setProductosActivos(productoRepo.countByActivoTrue());
        
        dashboard.setProductosInactivos(productoRepo.countByActivoFalse());
        
        List<ProductoDTO> bajoStock = productoRepo.findByStockActualLessThanEqualAndActivoTrue(stockMinimo)
                .stream()
                .map(p -> productoService.findById(p.getId()))
                .collect(Collectors.toList());
        dashboard.setProductosBajoStock(bajoStock);
        
        return ResponseEntity.ok(dashboard);
    }
}