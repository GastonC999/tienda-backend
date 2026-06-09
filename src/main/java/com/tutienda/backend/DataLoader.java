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

                // ☕ CAFÉ
                product("Granos Etiopía Yirgacheffe", "Café de origen único, notas de frutos rojos y jazmín. Tostado medio.", 3200.0, "Café",
                        "https://placehold.co/400x400?text=Etiopia"),
                product("Granos Colombia Huila", "Perfil achocolatado con acidez suave. Ideal para espresso y filtrado.", 2900.0, "Café",
                        "https://placehold.co/400x400?text=Colombia"),
                product("Granos Brasil Cerrado", "Nueces, caramelo y cuerpo redondo. Blend clásico para todos los días.", 2400.0, "Café",
                        "https://placehold.co/400x400?text=Brasil"),
                product("Cápsulas Moccana x10", "Compatibles con Nespresso. Blend exclusivo de la casa.", 1800.0, "Café",
                        "https://placehold.co/400x400?text=Capsulas"),
                product("Taza Moccana 300ml", "Taza de cerámica con logo Moccana. Edición limitada.", 2200.0, "Café",
                        "https://placehold.co/400x400?text=Taza"),
                product("Prensa Francesa 600ml", "Vidrio borosilicato con marco de acero inoxidable.", 4500.0, "Café",
                        "https://placehold.co/400x400?text=Prensa"),
                product("Molinillo Manual", "Cerámica de precisión, ajuste de grosor en 18 niveles.", 6800.0, "Café",
                        "https://placehold.co/400x400?text=Molinillo"),
                product("Dripper V60", "Cerámica japonesa para filtrado por goteo. Incluye 10 filtros.", 3600.0, "Café",
                        "https://placehold.co/400x400?text=V60"),

                // 🌿 CANNABIS MEDICINAL
                product("Aceite CBD 10% 30ml", "Full spectrum, extracción CO2. Ideal para ansiedad y dolor crónico.", 8500.0, "Cannabis Medicinal",
                        "https://placehold.co/400x400?text=Aceite+CBD"),
                product("Aceite CBD 5% 30ml", "Espectro amplio, sin THC. Primera opción para principiantes.", 5900.0, "Cannabis Medicinal",
                        "https://placehold.co/400x400?text=Aceite+5"),
                product("Tintura CBD 500mg", "Base alcohólica, absorción sublingual rápida.", 6200.0, "Cannabis Medicinal",
                        "https://placehold.co/400x400?text=Tintura"),
                product("Crema CBD 50mg", "Crema tópica para dolor muscular y articular. 50ml.", 4800.0, "Cannabis Medicinal",
                        "https://placehold.co/400x400?text=Crema"),
                product("Plantin Auto White Widow", "Semilla feminizada autofloreciente. Alta producción.", 3500.0, "Cannabis Medicinal",
                        "https://placehold.co/400x400?text=Plantin"),

                // 🌱 CULTIVO
                product("TopCrop Big One 1kg", "Fertilizante de floración de alta concentración.", 4200.0, "Cultivo",
                        "https://placehold.co/400x400?text=TopCrop"),
                product("Namaste Grow 500ml", "Estimulador de crecimiento orgánico.", 3100.0, "Cultivo",
                        "https://placehold.co/400x400?text=Namaste"),
                product("Carpa de Cultivo 80x80x160", "Lona reflectiva 600D, ventanas de inspección, bolsillos internos.", 18500.0, "Cultivo",
                        "https://placehold.co/400x400?text=Carpa"),
                product("Panel LED Full Spectrum 100W", "Samsung LM301B, cobertura 60x60cm, bajo consumo.", 32000.0, "Cultivo",
                        "https://placehold.co/400x400?text=LED"),
                product("Tierra Premium 20L", "Mix de turba, perlita y compost. pH balanceado.", 2800.0, "Cultivo",
                        "https://placehold.co/400x400?text=Tierra"),
                product("Maceta Air Pot 11L", "Raíces aireadas, crecimiento más vigoroso.", 1600.0, "Cultivo",
                        "https://placehold.co/400x400?text=Maceta"),
                product("Rociador 1.5L", "Boquilla ajustable, gatillo ergonómico.", 1200.0, "Cultivo",
                        "https://placehold.co/400x400?text=Rociador"),

                // 🛠️ ACCESORIOS
                product("Papeles RAW Classic King Size x50", "Fibra natural sin blanquear, combustión lenta.", 850.0, "Accesorios",
                        "https://placehold.co/400x400?text=RAW"),
                product("Picador Metálico 4 Partes", "Aluminio anodizado con colector de polen.", 2400.0, "Accesorios",
                        "https://placehold.co/400x400?text=Picador"),
                product("Vaper Portátil Dry Herb", "Temperatura ajustable, batería 2200mAh.", 15000.0, "Accesorios",
                        "https://placehold.co/400x400?text=Vaper"),
                product("Bong Vidrio 30cm", "Vidrio borosilicato 5mm, percolador de árbol.", 8900.0, "Accesorios",
                        "https://placehold.co/400x400?text=Bong"),
                product("Pipa Madera Artesanal", "Madera de olivo, pulida a mano. Pieza única.", 3200.0, "Accesorios",
                        "https://placehold.co/400x400?text=Pipa"),
                product("Portatucas x3", "Aluminio anodizado, cierre hermético. Pack de 3.", 1800.0, "Accesorios",
                        "https://placehold.co/400x400?text=Portutucas"),
                product("Caja de Almacenamiento", "Madera de bambú con tapa magnética y bandeja interior.", 4500.0, "Accesorios",
                        "https://placehold.co/400x400?text=Caja")
        ));

        System.out.println(">>> Productos Moccana cargados");
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