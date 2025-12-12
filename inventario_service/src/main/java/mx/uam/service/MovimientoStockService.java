package mx.uam.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.uam.model.dto.MovimientoStockDTO;
import mx.uam.model.entity.MovimientoStock;
import mx.uam.model.entity.Producto;
import mx.uam.repository.MovimientoStockRepository;
import mx.uam.repository.ProductoRepository;

@Service
public class MovimientoStockService {

    @Autowired
    private MovimientoStockRepository repo;

    @Autowired
    private ProductoRepository productoRepo;


    public Page<MovimientoStockDTO> listar(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaMovimiento").descending());
        return repo.findAll(pageable).map(this::convertToDTO);
    }


    public List<MovimientoStockDTO> listarPorProducto(Long productoId) {
        return repo.findByProductoIdOrderByFechaMovimientoDesc(productoId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Transactional
    public MovimientoStockDTO registrarMovimiento(MovimientoStockDTO dto) {
        Producto producto = productoRepo.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        int nuevoStock = producto.getStockActual();
        if ("ENTRADA".equalsIgnoreCase(dto.getTipo())) {
            nuevoStock += dto.getCantidad();
        } else if ("SALIDA".equalsIgnoreCase(dto.getTipo())) {
            nuevoStock -= dto.getCantidad();
            if (nuevoStock < 0) {
                throw new RuntimeException("Stock insuficiente. Stock actual: " + producto.getStockActual());
            }
        }

        producto.setStockActual(nuevoStock);
        productoRepo.save(producto);

        MovimientoStock movimiento = new MovimientoStock();
        movimiento.setProducto(producto);
        movimiento.setTipo(dto.getTipo().toUpperCase());
        movimiento.setCantidad(dto.getCantidad());
        movimiento.setMotivo(dto.getMotivo());
        movimiento.setFechaMovimiento(LocalDateTime.now());

        MovimientoStock saved = repo.save(movimiento);
        return convertToDTO(saved);
    }


    public MovimientoStockDTO findById(Long id) {
        return repo.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }


    private MovimientoStockDTO convertToDTO(MovimientoStock m) {
        MovimientoStockDTO dto = new MovimientoStockDTO();
        dto.setId(m.getId());
        dto.setProductoId(m.getProducto().getId());
        dto.setProductoNombre(m.getProducto().getNombre());
        dto.setTipo(m.getTipo());
        dto.setCantidad(m.getCantidad());
        dto.setMotivo(m.getMotivo());
        dto.setFechaMovimiento(m.getFechaMovimiento());
        return dto;
    }
}