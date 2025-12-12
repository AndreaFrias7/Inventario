package mx.uam.model.dto;

import java.util.List;

public class DashboardDTO {
    private Long totalProductos;
    private Long totalCategorias;
    private Long productosActivos;
    private Long productosInactivos;
    private List<ProductoDTO> productosBajoStock;

    public DashboardDTO() {
    }

    public DashboardDTO(Long totalProductos, Long totalCategorias, Long productosActivos, 
                        Long productosInactivos, List<ProductoDTO> productosBajoStock) {
        this.totalProductos = totalProductos;
        this.totalCategorias = totalCategorias;
        this.productosActivos = productosActivos;
        this.productosInactivos = productosInactivos;
        this.productosBajoStock = productosBajoStock;
    }

    public Long getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(Long totalProductos) {
        this.totalProductos = totalProductos;
    }

    public Long getTotalCategorias() {
        return totalCategorias;
    }

    public void setTotalCategorias(Long totalCategorias) {
        this.totalCategorias = totalCategorias;
    }

    public Long getProductosActivos() {
        return productosActivos;
    }

    public void setProductosActivos(Long productosActivos) {
        this.productosActivos = productosActivos;
    }

    public Long getProductosInactivos() {
        return productosInactivos;
    }

    public void setProductosInactivos(Long productosInactivos) {
        this.productosInactivos = productosInactivos;
    }

    public List<ProductoDTO> getProductosBajoStock() {
        return productosBajoStock;
    }

    public void setProductosBajoStock(List<ProductoDTO> productosBajoStock) {
        this.productosBajoStock = productosBajoStock;
    }
}