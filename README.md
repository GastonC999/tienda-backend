# Backend — Mi Tienda

Stack: Java 21 · Spring Boot 3 · Spring Data JPA · H2 (dev)

## Requisitos
- Java 21+
- Maven (incluido via mvnw)

## Desarrollo
./mvnw spring-boot:run → http://localhost:8080

## Estructura
src/main/java/com/tutienda/backend/
model/          # Entidades JPA (Product, Order, OrderItem)
repository/     # Interfaces JPA Repository
controller/     # Endpoints REST
DataLoader.java # Carga productos de prueba al iniciar

## Endpoints
GET  /api/products       # Lista todos los productos
GET  /api/products/{id}  # Detalle de un producto
POST /api/products       # Crear producto
POST /api/orders         # Crear orden
GET  /api/orders/{id}    # Detalle de orden

## Base de datos
Usa H2 en memoria por defecto (se resetea al reiniciar).
Configuración en src/main/resources/application.properties

## CORS
Configurado para aceptar requests desde http://localhost:3000