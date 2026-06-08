package com.tutienda.backend;

import com.tutienda.backend.model.Product;
import com.tutienda.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (productRepository.count() > 0) return;

        productRepository.saveAll(List.of(
                product("Remera básica", "Algodón 100%, talles S al XL", 4500.0, "Ropa",
                        "https://placehold.co/400x400?text=Remera"),
                product("Jeans slim", "Jean elastizado, corte slim fit", 12900.0, "Ropa",
                        "https://placehold.co/400x400?text=Jeans"),
                product("Zapatillas urbanas", "Suela de goma, capellada de cuero", 28000.0, "Calzado",
                        "https://placehold.co/400x400?text=Zapatillas"),
                product("Gorra trucker", "Tela de algodón, ajuste trasero", 3200.0, "Accesorios",
                        "https://placehold.co/400x400?text=Gorra"),
                product("Campera de abrigo", "Relleno sintético, impermeable", 35000.0, "Ropa",
                        "https://placehold.co/400x400?text=Campera"),
                product("Mochila urbana", "20 litros, compartimiento para notebook", 18500.0, "Accesorios",
                        "https://placehold.co/400x400?text=Mochila"),
                product("Mochila urbana", "20 litros, compartimiento para notebook", 18500.0, "Accesorios",
                        "https://placehold.co/400x400?text=Mochila")
        ));

        System.out.println(">>> Productos de prueba cargados");
    }

    private Product product(String name, String description, Double price, String category, String image) {
        Product p = new Product();
        p.setName(name);
        p.setDescription(description);
        p.setPrice(price);
        p.setCategory(category);
        p.setImage(image);
        return p;
    }
}