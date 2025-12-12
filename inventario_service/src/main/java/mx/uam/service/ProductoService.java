package mx.uam.service;

import mx.uam.model.dto.ProductoDTO;
import mx.uam.model.entity.Categoria;
import mx.uam.model.entity.Producto;
import mx.uam.repository.CategoriaRepository;
import mx.uam.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repo;

    @Autowired
    private CategoriaRepository catRepo;

    public List<ProductoDTO> listarProductos() {
        return repo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Page<ProductoDTO> listarProductos(String filtro, int page, int size, Long categoriaId) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable).map(this::convertToDTO);
    }

    public ProductoDTO create(ProductoDTO dto) {
        Categoria categoria = catRepo.findById(dto.getCategoriaId()).orElse(null);
        if (categoria == null) return null;

        Producto p = new Producto();
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setPrecio(dto.getPrecio() != null ? dto.getPrecio() : 0.0);
        p.setStockActual(dto.getStockActual() != null ? dto.getStockActual() : 0);
        p.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        p.setCategoria(categoria);

        Producto saved = repo.save(p);
        return convertToDTO(saved);
    }

    public ProductoDTO findById(Long id) {
        return repo.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public ProductoDTO update(Long id, ProductoDTO dto) {
        Producto p = repo.findById(id).orElse(null);
        if (p == null) return null;

        Categoria categoria = catRepo.findById(dto.getCategoriaId()).orElse(null);
        if (categoria == null) return null;

        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setPrecio(dto.getPrecio());
        p.setStockActual(dto.getStockActual());
        p.setActivo(dto.getActivo() != null ? dto.getActivo() : p.getActivo());
        p.setCategoria(categoria);

        Producto updated = repo.save(p);
        return convertToDTO(updated);
    }

    public void delete(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    private ProductoDTO convertToDTO(Producto p) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setDescripcion(p.getDescripcion());
        dto.setPrecio(p.getPrecio());
        dto.setStockActual(p.getStockActual());
        dto.setActivo(p.getActivo());
        dto.setCategoriaId(p.getCategoria() != null ? p.getCategoria().getId() : null);
        dto.setCategoriaNombre(p.getCategoria() != null ? p.getCategoria().getNombre() : null);
        return dto;
    }
}