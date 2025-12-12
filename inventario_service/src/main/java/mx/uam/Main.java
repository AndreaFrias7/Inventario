package mx.uam;

//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;

//import mx.uam.model.entity.Categoria;
//import mx.uam.model.entity.Producto;
//import mx.uam.repository.CategoriaRepository;
//import mx.uam.repository.ProductoRepository;

@SpringBootApplication(scanBasePackages = "mx.uam")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }



    /*
    @Bean
    CommandLineRunner init(CategoriaRepository categoriaRepository, ProductoRepository productoRepository) {
        return args -> {
            Categoria d = new Categoria();
            d.setNombre("Sistemas");
            d.setDireccion("Calle Falsa 123");
            categoriaRepository.save(d);

            Producto e = new Producto();
            e.setNombre("Juan");
            e.setApellidoPaterno("Pérez");
            e.setApellidoMaterno("López");
            e.setEdad(30);
            e.setCategoria(d);
            productoRepository.save(e);

            System.out.println("Sample data inserted: categoria id=" + d.getId() + ", producto id=" + e.getId());
        };
    }
         */
}