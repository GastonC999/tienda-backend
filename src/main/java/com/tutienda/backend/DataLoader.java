package com.tutienda.backend;

import com.tutienda.backend.model.HeroSlide;
import com.tutienda.backend.model.Product;
import com.tutienda.backend.model.User;
import com.tutienda.backend.repository.HeroSlideRepository;
import com.tutienda.backend.repository.ProductRepository;
import com.tutienda.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HeroSlideRepository heroSlideRepository;

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
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
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setEmail("admin@moccana.com");
            admin.setPassword(passwordEncoder.encode("moccana2025"));
            admin.setRole(User.Role.ADMIN);
            userRepository.save(admin);

            User editor = new User();
            editor.setEmail("editor@moccana.com");
            editor.setPassword(passwordEncoder.encode("editor2025"));
            editor.setRole(User.Role.EDITOR);
            userRepository.save(editor);

            System.out.println(">>> Usuarios creados");
        }
        if (heroSlideRepository.count() == 0) {
            heroSlideRepository.saveAll(List.of(
                    slide("Café de Especialidad", "Granos seleccionados de origen único", "Ver cafés", "/products?category=Café", "https://placehold.co/1200x500", 1),
                    slide("Cannabis Medicinal", "Productos de calidad para tu bienestar", "Ver productos", "/products?category=Cannabis Medicinal", "https://placehold.co/1200x500", 2),
                    slide("Todo para tu Cultivo", "Equipamiento profesional para cultivadores", "Ver cultivo", "/products?category=Cultivo", "https://placehold.co/1200x500", 3),
                    slide("Accesorios", "Complementos esenciales para tu experiencia", "Ver accesorios", "/products?category=Accesorios", "https://placehold.co/1200x500", 4)
            ));
        }
    }


    private Product product(String name, String description, Double price, String category, String image) {
        Product p = new Product();
        p.setName(name);
        p.setDescription(description);
        p.setPrice(price);
        p.setCategory(category);
        p.setImage(image);
        p.setStock(100);// Temporal: agregar `stock` como parámetro del método y pasar el argumento en cada llamada a `product()`.
        return p;
    }

    private HeroSlide slide(String title, String subtitle, String cta, String href, String imagenUrl, Integer order){
        HeroSlide slide = new HeroSlide();
        slide.setTitle(title);
        slide.setSubtitle(subtitle);
        slide.setCta(cta);
        slide.setHref(href);
        slide.setImageUrl(imagenUrl);
        slide.setOrden(order);
        return slide;
    }
}