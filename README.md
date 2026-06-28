# Backend — Moccana

Stack: Java 21 · Spring Boot 3 · Spring Data JPA · MySQL · Flyway · Cloudinary · Spring Security + JWT

## Requisitos
- Java 21+
- Maven (incluido via `mvnw`)
- MySQL 8+

## Desarrollo
```bash
./mvnw spring-boot:run → http://localhost:8080
```

## Configuración
Creá `src/main/resources/application.properties` basándote en `.example`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tienda?useSSL=false&serverTimezone=America/Argentina/Buenos_Aires&allowPublicKeyRetrieval=true
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

jwt.secret=TU_JWT_SECRET_LARGO
jwt.expiration=86400000

mercadopago.access-token=TU_ACCESS_TOKEN
cloudinary.cloud-name=TU_CLOUD_NAME
cloudinary.api-key=TU_API_KEY
cloudinary.api-secret=TU_API_SECRET

allowed.origin=http://localhost:3000
```

> ⚠️ `application.properties` está en `.gitignore`. Nunca subas credenciales al repo.

## Estructura de paquetes
```
com.tutienda.backend/
  config/
    CloudinaryConfig.java         # Bean de Cloudinary con credenciales
  controller/
    AuthController.java           # POST /api/auth/login → devuelve JWT
    HeroSlideController.java      # GET /api/slides · PUT /api/slides/{id}
    OrderController.java          # POST · GET /api/orders · PUT /api/orders/{id}/status
    PaymentController.java        # POST /api/payments/create-preference
    ProductController.java        # GET · POST · PUT · DELETE /api/products
    StatsController.java          # GET /api/stats
    UploadController.java         # POST /api/upload → Cloudinary
  dto/
    DailyOrderDTO.java            # record: date, orderCount
    StatsResponse.java            # record: totalRevenue, ordersByStatus, topProducts, revenueByCategory, dailyOrders
    TopProductDTO.java            # record: productName, totalQuantity
    UpdateStatusRequest.java      # record: status (OrderStatus)
  enums/
    OrderStatus.java              # PENDING, PAID, CANCELLED
  exception/
    GlobalExceptionHandler.java   # @RestControllerAdvice: maneja todas las excepciones
    InsufficientStockException.java
    InvalidOrderStatusException.java
    OrderNotFoundException.java
    ProductNotFoundException.java
  model/
    HeroSlide.java                # Entidad JPA: tabla hero_slides
    Order.java                    # Entidad JPA: tabla orders — @PrePersist setea status y createdAt
    OrderItem.java                # Entidad JPA: tabla order_items (snapshot de producto)
    Product.java                  # Entidad JPA: tabla products
    User.java                     # Entidad JPA: tabla users (roles: ADMIN, EDITOR)
  repository/
    HeroSlideRepository.java      # findAllByOrderByOrdenAsc()
    OrderRepository.java          # queries para stats, filtro por status, orden por fecha
    ProductRepository.java
    UserRepository.java           # findByEmail()
  security/
    JwtFilter.java                # Intercepta requests y verifica el token
    JwtService.java               # Genera y valida tokens JWT
    SecurityConfig.java           # Reglas de acceso por rol y CORS global
    UserDetailsServiceImpl.java   # Carga usuarios desde MySQL para Spring Security
  service/
    OrderService.java             # Lógica de negocio de órdenes y descuento de stock
    StatsService.java             # Ensambla métricas para el dashboard
  DataLoader.java                 # Siembra 27 productos, 2 usuarios y 4 slides si las tablas están vacías
  BackendApplication.java
```

## Endpoints REST

### Auth
| Método | URL | Acceso | Descripción |
|---|---|---|---|
| POST | `/api/auth/login` | Público | Login con email y password → devuelve JWT + role |

### Productos
| Método | URL | Acceso | Descripción |
|---|---|---|---|
| GET | `/api/products` | Público | Lista todos los productos |
| GET | `/api/products/{id}` | Público | Detalle de un producto |
| POST | `/api/products` | ADMIN | Crear producto |
| PUT | `/api/products/{id}` | ADMIN, EDITOR | Editar producto |
| DELETE | `/api/products/{id}` | ADMIN | Eliminar producto |

### Órdenes
| Método | URL | Acceso | Descripción |
|---|---|---|---|
| POST | `/api/orders` | Público | Crear orden y descontar stock |
| GET | `/api/orders` | ADMIN | Listar todas (opcional `?status=PENDING`) |
| GET | `/api/orders/{id}` | ADMIN | Detalle de una orden |
| PUT | `/api/orders/{id}/status` | ADMIN | Cambiar estado de una orden |

### Estadísticas
| Método | URL | Acceso | Descripción |
|---|---|---|---|
| GET | `/api/stats` | ADMIN | Revenue total, órdenes por estado, top productos, ventas por categoría, órdenes por día |

### Hero Slides
| Método | URL | Acceso | Descripción |
|---|---|---|---|
| GET | `/api/slides` | Público | Lista los 4 slides del carousel ordenados |
| PUT | `/api/slides/{id}` | ADMIN, EDITOR | Editar título, subtítulo, CTA, href e imagen |

### Imágenes
| Método | URL | Acceso | Descripción |
|---|---|---|---|
| POST | `/api/upload` | ADMIN, EDITOR | Subir imagen a Cloudinary → devuelve URL |

### Pagos
| Método | URL | Acceso | Descripción |
|---|---|---|---|
| POST | `/api/payments/create-preference` | Público | Crea preferencia MP → devuelve sandboxInitPoint |

## Modelo de datos

### Product
| Campo | Tipo | Descripción |
|---|---|---|
| id | Long | PK autoincremental |
| name | String | Nombre del producto |
| description | String | Descripción |
| price | Double | Precio |
| image | String | URL de Cloudinary |
| category | String | Café / Cannabis Medicinal / Cultivo / Accesorios |
| stock | Integer | Unidades disponibles |

### Order
| Campo | Tipo | Descripción |
|---|---|---|
| id | Long | PK autoincremental |
| customerName | String | Nombre del cliente |
| customerEmail | String | Email del cliente |
| total | Double | Total de la orden |
| status | OrderStatus | PENDING / PAID / CANCELLED — seteado por @PrePersist |
| createdAt | LocalDateTime | Fecha de creación — seteada por @PrePersist |
| items | List\<OrderItem\> | OneToMany cascade |

### OrderItem
| Campo | Tipo | Descripción |
|---|---|---|
| id | Long | PK autoincremental |
| productId | Long | ID del producto al momento de compra |
| productName | String | Snapshot del nombre al momento de compra |
| price | Double | Snapshot del precio al momento de compra |
| quantity | Integer | Cantidad |

### HeroSlide
| Campo | Tipo | Descripción |
|---|---|---|
| id | Long | PK autoincremental |
| title | String | Título del slide |
| subtitle | String | Subtítulo |
| cta | String | Texto del botón |
| href | String | Link del botón |
| imageUrl | String | URL de la imagen de fondo |
| orden | Integer | Orden de aparición en el carousel |

### User
| Campo | Tipo | Descripción |
|---|---|---|
| id | Long | PK autoincremental |
| email | String | Único, usado como username |
| password | String | BCrypt hash |
| role | Enum | ADMIN / EDITOR |

## Manejo de excepciones

`GlobalExceptionHandler` centraliza todas las respuestas de error:

| Excepción | HTTP | Cuándo se lanza |
|---|---|---|
| `InsufficientStockException` | 400 | Stock insuficiente al crear orden |
| `InvalidOrderStatusException` | 400 | Status inválido en PUT /orders/{id}/status |
| `OrderNotFoundException` | 404 | Orden no encontrada |
| `ProductNotFoundException` | 404 | Producto no encontrado |

## Migraciones Flyway
En `src/main/resources/db/migration/`:
- `V1__init.sql` — tablas products, orders, order_items
- `V2__create_users.sql` — tabla users
- `V3__create_hero_slides.sql` — tabla hero_slides
- `V4__add_stock_to_products.sql` — columna stock en products

## Usuarios de prueba (creados por DataLoader)
| Email | Password | Rol |
|---|---|---|
| admin@moccana.com | moccana2025 | ADMIN |
| editor@moccana.com | editor2025 | EDITOR |

## Autenticación JWT
1. POST `/api/auth/login` con `{email, password}`
2. Respuesta: `{token, role, email}`
3. Incluir en requests protegidos: `Authorization: Bearer <token>`
4. Token válido por 24hs (configurable con `jwt.expiration`)

## CORS
Configurado globalmente en `SecurityConfig` leyendo `allowed.origin` del `application.properties`.
- **Desarrollo:** `allowed.origin=http://localhost:3000`
- **Producción (Railway):** `ALLOWED_ORIGIN=https://tu-tienda.vercel.app`

## Imágenes
Cloudinary bajo carpeta `moccana/products`, transformación `f_auto,q_auto,w_800`.

## MercadoPago
Checkout Pro con `getSandboxInitPoint()`. Pendiente: cambiar a `getInitPoint()` e implementar webhook para actualizar estado de orden automáticamente.

## Deploy
Railway con MySQL como servicio separado. Variables de entorno requeridas en Railway:
```
SPRING_DATASOURCE_URL=jdbc:mysql://${{MySQL.MYSQLHOST}}:${{MySQL.MYSQLPORT}}/${{MySQL.MYSQLDATABASE}}?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
SPRING_DATASOURCE_USERNAME=${{MySQL.MYSQLUSER}}
SPRING_DATASOURCE_PASSWORD=${{MySQL.MYSQLPASSWORD}}
JWT_SECRET=...
MERCADOPAGO_ACCESS_TOKEN=...
CLOUDINARY_CLOUD_NAME=...
CLOUDINARY_API_KEY=...
CLOUDINARY_API_SECRET=...
ALLOWED_ORIGIN=https://tu-tienda.vercel.app
```

## Pendiente
- Webhook MercadoPago para actualizar orden a PAID automáticamente (BACK-09)
- Cambiar `getSandboxInitPoint()` por `getInitPoint()` en producción