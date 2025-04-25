# Image Gallery Web Application

## Projekt leírása
Ez egy webes képböngésző alkalmazás, amely Spring Boot API-val és React (Vite + TypeScript) frontenddel készült. A felhasználók bejelentkezhetnek, és jogosultságaik alapján tekinthetik meg az adott könyvtárban lévő képeket.

## Fő funkciók
- Felhasználói autentikáció JWT alapon
- Admin és User szerepkör kezelése
- Képfájlok jogosultság alapú megjelenítése (kiterjesztés szűréssel)
- Admin felhasználó jogosultság-menedzsmentje
- PostgreSQL adatbázis

## Használt technológiák

### Backend
- Java 17+
- Spring Boot 3 (Web, Security, Data JPA, Validation)
- JWT (io.jsonwebtoken)
- PostgreSQL
- Lombok

### Frontend
- React 18
- Vite
- TypeScript
- MUI (Material-UI)
- Zustand
- React Router DOM
- Axios

## Projekt struktúra
```plaintext
spring-react-image-gallery/
├── api/        # Spring Boot alkalmazás
├── client/         # React + Vite frontend alkalmazás
├── docker/         # PostgreSQL docker-compose
├── README.md
```

## Telepítési útmutató

### 1. PostgreSQL adatbázis Docker konténerrel
- Lépj be a `docker/` könyvtárba és indítsd el a konténert:

```bash
cd docker
docker-compose up -d
```

**docker-compose.yml** minta:
```yaml
version: "3.8"

services:
  db:
    image: postgres:13
    container_name: postgres_container
    environment:
      POSTGRES_USER: root
      POSTGRES_PASSWORD: root
      POSTGRES_DB: image_browser_db
    volumes:
      - postgres_data:/var/lib/postgresql/data
    ports:
      - "5432:5432"

volumes:
  postgres_data:
```

### 2. Backend indítása (Spring Boot)
- Lépj be a `api/` könyvtárba:

```bash
cd api
gradlew bootRun
```

- Konfiguráld az `application.yml` fájlban az adatbázis kapcsolatot:

```properties
spring.datasource:
  url: jdbc:postgresql://localhost:5432/image_browser_db
  username: root
  password: root
  driver-class-name: org.postgresql.Driver
```

### 3. Frontend indítása (React + Vite)
- Lépj be a `client/` könyvtárba:

```bash
cd client
npm install
npm run dev
```

### API várhato elérhetőség
- Backend API: http://localhost:8080
- Frontend alkalmazás: http://localhost:5173

## Jogosultsági rendszer
| Szerepkör  | Leírás |
|------------|--------|
| **Admin**  | Összes képkiterjesztés megtekintése, felhasználói jogosultságok módosítása |
| **User**   | Csak saját formátumú képek megtekintése |

## API vázlat
| Módszer | Útvonal | Funkció |
|:-------:|:--------:|:-------:|
| POST | `/api/auth/login` | Bejelentkezés |
| POST | `/api/auth/signup` | Regisztráció |
| GET | `/api/auth/me` | Profile |
| POST | `/api/images` | Kép hozzáadása |
| GET | `/api/images` | Képek listázása jogosultság alapján |
| GET | `/api/images/${id}` | Kép lekérése |
| GET | `/api/images/admin` | Képek listázása |
| GET | `/api/users` | Felhasználók listázása |
| GET | `/api/users/{id}` | Felhasználó lekérése |
| PATCH | `/api/users/{id}` | Szerkesztés |

## Rendszerkövetelmények
- Java 17+
- Node.js 18+
- Docker
- PostgreSQL 13+

---

## Megjegyzés
Ez a projekt oktatási és referencia célokra készült, valós webalkalmazási architektúra demonstrálására.