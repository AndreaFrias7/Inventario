package mx.uam.service;

import mx.uam.model.dto.CategoriaDTO;
import mx.uam.model.entity.Categoria;
import mx.uam.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public List<CategoriaDTO> listar() {
        return repo.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Page<CategoriaDTO> listar(String filtro, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.findAll(pageable).map(this::convertToDTO);
    }

    public CategoriaDTO create(CategoriaDTO dto) {
        Categoria c = new Categoria();
        c.setNombre(dto.getNombre());
        c.setDescripcion(dto.getDescripcion());
        c.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        c.setFechaCreacion(LocalDateTime.now());
        Categoria saved = repo.save(c);
        return convertToDTO(saved);
    }

    public CategoriaDTO findById(Long id) {
        return repo.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public CategoriaDTO update(Long id, CategoriaDTO dto) {
        Categoria c = repo.findById(id).orElse(null);
        if (c != null) {
            c.setNombre(dto.getNombre());
            c.setDescripcion(dto.getDescripcion());
            c.setActivo(dto.getActivo() != null ? dto.getActivo() : c.getActivo());
            Categoria updated = repo.save(c);
            return convertToDTO(updated);
        }
        return null;
    }

    public void delete(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    private CategoriaDTO convertToDTO(Categoria c) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(c.getId());
        dto.setNombre(c.getNombre());
        dto.setDescripcion(c.getDescripcion());
        dto.setActivo(c.getActivo());
        dto.setFechaCreacion(c.getFechaCreacion());
        return dto;
    }
}