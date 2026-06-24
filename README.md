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
```

> ⚠️ `application.properties` está en `.gitignore`. Nunca subas credenciales al repo.

## Estructura de paquetes
```
com.tutienda.backend/
  config/
    CloudinaryConfig.java       # Bean de Cloudinary con credenciales
  model/
    Product.java                # Entidad JPA: tabla products
    Order.java                  # Entidad JPA: tabla orders
    OrderItem.java              # Entidad JPA: tabla order_items (guarda snapshot: productId, productName, price)
    User.java                   # Entidad JPA: tabla users (roles: ADMIN, EDITOR)
    HeroSlide.java              # Entidad JPA: tabla hero_slides
  repository/
    ProductRepository.java
    OrderRepository.java        # Incluye queries para stats (revenue, top productos, categorías, daily orders)
    UserRepository.java         # findByEmail()
    HeroSlideRepository.java    # findAllByOrderByOrdenAsc()
  controller/
    ProductController.java      # GET /api/products, GET /api/products/{id}, POST, PUT, DELETE
    OrderController.java        # POST /api/orders, GET /api/orders, PUT /api/orders/{id}/status
    StatsController.java        # GET /api/stats
    HeroSlideController.java    # GET /api/slides, PUT /api/slides/{id}
    PaymentController.java      # POST /api/payments/create-preference
    UploadController.java       # POST /api/upload → Cloudinary
    AuthController.java         # POST /api/auth/login → devuelve JWT
  dto/
    TopProductDTO.java          # record: productName, totalQuantity
    DailyOrderDTO.java          # record: date, orderCount
    StatsResponse.java          # record: totalRevenue, ordersByStatus, topProducts, revenueByCategory, dailyOrders
  security/
    JwtService.java             # Genera y valida tokens JWT
    JwtFilter.java              # Intercepta requests y verifica el token
    SecurityConfig.java         # Reglas de acceso por rol y CORS global
    UserDetailsServiceImpl.java # Carga usuarios desde MySQL para Spring Security
  DataLoader.java               # Carga 27 productos y 2 usuarios al iniciar si están vacíos
  BackendApplication.java
```

## Endpoints REST

### Auth (público)
| Método | URL | Descripción |
|---|---|---|
| POST | `/api/auth/login` | Login con email y password → devuelve JWT + role |

### Productos
| Método | URL | Rol requerido |
|---|---|---|
| GET | `/api/products` | Público |
| GET | `/api/products/{id}` | Público |
| POST | `/api/products` | ADMIN |
| PUT | `/api/products/{id}` | ADMIN, EDITOR |
| DELETE | `/api/products/{id}` | ADMIN |

### Órdenes
| Método | URL | Rol requerido | Descripción |
|---|---|---|---|
| POST | `/api/orders` | Público | Crear orden |
| GET | `/api/orders` | ADMIN | Listar todas (opcional `?status=PENDING`) |
| PUT | `/api/orders/{id}/status` | ADMIN | Cambiar estado de una orden |

### Estadísticas
| Método | URL | Rol requerido | Descripción |
|---|---|---|---|
| GET | `/api/stats` | ADMIN | Métricas del dashboard (revenue, top productos, ventas por categoría, órdenes por día) |

### Hero Slides
| Método | URL | Rol requerido | Descripción |
|---|---|---|---|
| GET | `/api/slides` | Público | Lista los 4 slides del carousel ordenados |
| PUT | `/api/slides/{id}` | ADMIN, EDITOR | Editar título, subtítulo, CTA, href e imagen de un slide |


| Método | URL | Descripción |
|---|---|---|
| POST | `/api/payments/create-preference` | Crea preferencia MP, devuelve sandboxInitPoint |

### Imágenes
| Método | URL | Rol requerido |
|---|---|---|
| POST | `/api/upload` | ADMIN, EDITOR |

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

### Order
| Campo | Tipo | Descripción |
|---|---|---|
| id | Long | PK autoincremental |
| customerName | String | Nombre del cliente |
| customerEmail | String | Email del cliente |
| total | Double | Total de la orden |
| status | String | PENDING / PAID / CANCELLED |
| createdAt | LocalDateTime | Fecha de creación automática |
| items | List\<OrderItem\> | OneToMany cascade |

### OrderItem
| Campo | Tipo | Descripción |
|---|---|---|
| id | Long | PK autoincremental |
| productId | Long | ID del producto |
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


| Campo | Tipo | Descripción |
|---|---|---|
| id | Long | PK autoincremental |
| email | String | Único, usado como username |
| password | String | BCrypt hash |
| role | Enum | ADMIN / EDITOR |

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

## Base de datos
- **Desarrollo**: MySQL local, base `tienda`
- **Producción**: MySQL en Railway
- **Migraciones Flyway** en `src/main/resources/db/migration/`:
    - `V1__init.sql` — tablas products, orders, order_items
    - `V2__create_users.sql` — tabla users
    - `V3__create_hero_slides.sql` — tabla hero_slides

## Imágenes
Cloudinary bajo carpeta `moccana/products`, transformación `f_auto,q_auto,w_800`.

## CORS
Configurado globalmente en `SecurityConfig` leyendo la variable `allowed.origin` del `application.properties`. No usar `@CrossOrigin` por controller.

- **Desarrollo**: `allowed.origin=http://localhost:3000`
- **Producción (Railway)**: `ALLOWED_ORIGIN=https://tu-tienda.vercel.app`

## MercadoPago
Checkout Pro con `getSandboxInitPoint()`. Cambiar a `getInitPoint()` en producción e implementar webhook para actualizar estado de orden automáticamente.

## Deploy
Railway con MySQL como servicio separado. Variables de entorno en el panel de Railway.

## Pendiente
- Webhook MercadoPago para actualizar orden a PAID automáticamente
- Campo stock en productos (BACK-07)
- Cambiar `getSandboxInitPoint()` por `getInitPoint()` en producción