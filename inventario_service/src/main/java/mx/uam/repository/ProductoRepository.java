package mx.uam.repository;

import mx.uam.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    Long countByActivoTrue();
    
    Long countByActivoFalse();
    
    List<Producto> findByStockActualLessThanEqualAndActivoTrue(Integer stockMinimo);
}