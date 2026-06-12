# Backend — Moccana

Stack: Java 21 · Spring Boot 3 · Spring Data JPA · MySQL · Flyway · Cloudinary

## Requisitos
- Java 21+
- Maven (incluido via `mvnw`)
- MySQL 8+

## Desarrollo
```bash
./mvnw spring-boot:run → http://localhost:8080
```

## Configuración
Creá `src/main/resources/application.properties` basándote en el archivo `.example`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tienda?useSSL=false&serverTimezone=America/Argentina/Buenos_Aires
spring.datasource.username=root
spring.datasource.password=TU_CONTRASEÑA
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true

spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

server.port=8080

mercadopago.access-token=TU_ACCESS_TOKEN
cloudinary.cloud-name=TU_CLOUD_NAME
cloudinary.api-key=TU_API_KEY
cloudinary.api-secret=TU_API_SECRET
```

> ⚠️ `application.properties` está en `.gitignore`. Nunca subas credenciales al repo.

## Estructura de paquetes
```
com.tutienda.backend/
  config/
    CloudinaryConfig.java     # Bean de Cloudinary con credenciales
  model/
    Product.java              # Entidad JPA: tabla products
    Order.java                # Entidad JPA: tabla orders
    OrderItem.java            # Entidad JPA: tabla order_items
  repository/
    ProductRepository.java    # CRUD de productos
    OrderRepository.java      # CRUD de órdenes
  controller/
    ProductController.java    # Endpoints REST /api/products
    OrderController.java      # Endpoints REST /api/orders
    PaymentController.java    # Endpoint /api/payments/create-preference
    UploadController.java     # Endpoint /api/upload (Cloudinary)
  DataLoader.java             # Carga 27 productos de prueba al iniciar
  BackendApplication.java     # Clase principal (main)
```

## Endpoints REST

### Productos
| Método | URL | Descripción |
|---|---|---|
| GET | `/api/products` | Lista todos los productos |
| GET | `/api/products/{id}` | Detalle de un producto |
| POST | `/api/products` | Crear producto |

### Órdenes
| Método | URL | Descripción |
|---|---|---|
| POST | `/api/orders` | Crear orden |
| GET | `/api/orders/{id}` | Detalle de orden |

### Pagos
| Método | URL | Descripción |
|---|---|---|
| POST | `/api/payments/create-preference` | Crea preferencia en MercadoPago y devuelve initPoint |

### Imágenes
| Método | URL | Descripción |
|---|---|---|
| POST | `/api/upload` | Sube imagen a Cloudinary y devuelve URL |

## Modelo de datos

### Product
| Campo | Tipo | Descripción |
|---|---|---|
| id | Long | PK autoincremental |
| name | String | Nombre del producto |
| description | String | Descripción |
| price | Double | Precio |
| image | String | URL de la imagen (Cloudinary) |
| category | String | Café / Cannabis Medicinal / Cultivo / Accesorios |

### Order
| Campo | Tipo | Descripción |
|---|---|---|
| id | Long | PK autoincremental |
| customerName | String | Nombre del cliente |
| customerEmail | String | Email del cliente |
| total | Double | Total de la orden |
| status | String | PENDING / PAID / CANCELLED |
| createdAt | LocalDateTime | Fecha de creación (auto) |
| items | List\<OrderItem\> | Items de la orden (OneToMany) |

### OrderItem
| Campo | Tipo | Descripción |
|---|---|---|
| id | Long | PK autoincremental |
| productId | Long | ID del producto |
| productName | String | Nombre (snapshot al momento de compra) |
| price | Double | Precio al momento de la compra |
| quantity | Integer | Cantidad |

## Base de datos
- **Desarrollo**: MySQL local, base `tienda`
- **Producción**: MySQL en Railway
- **Migraciones**: Flyway — scripts en `src/main/resources/db/migration/`
    - `V1__init.sql` — estructura inicial de tablas

## Imágenes
Las imágenes de productos se almacenan en **Cloudinary** bajo la carpeta `moccana/products`. El backend guarda solo la URL en MySQL. Se optimizan automáticamente con `f_auto,q_auto,w_800`.

## MercadoPago
Integración con Checkout Pro. El backend crea una preferencia de pago y devuelve el `sandboxInitPoint` para redirigir al usuario. Las backUrls redirigen a `/checkout/success` o `/checkout` según el resultado.

## CORS
Configurado por controlador con `@CrossOrigin(origins = "http://localhost:3000")`. En producción actualizar con el dominio de Vercel.

## DataLoader
Carga 27 productos de prueba al iniciar si la tabla está vacía. Categorías: Café (8), Cannabis Medicinal (5), Cultivo (7), Accesorios (7).

## Deploy
Deployado en **Railway** con MySQL como servicio separado en el mismo proyecto. Variables de entorno configuradas en el panel de Railway.