<div align="center">

# 🧩 UserBridge

### Gotowy fundament full-stack pod każdą nową aplikację

**Spring Boot 3 · Spring Security + JWT · PostgreSQL · React 18 · Vite**

![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-6DB33F?logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?logo=springsecurity&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?logo=postgresql&logoColor=white)
![React](https://img.shields.io/badge/React-18-61DAFB?logo=react&logoColor=black)
![Vite](https://img.shields.io/badge/Vite-5-646CFF?logo=vite&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker&logoColor=white)
![Tests](https://img.shields.io/badge/tests-81%20passing-brightgreen)

</div>

---

## 💡 Po co ten projekt?

Każda aplikacja zaczyna się tak samo: rejestracja, aktywacja konta mailem, logowanie, „nie pamiętam hasła”, profil użytkownika, role. To zwykle **pierwsze 2–3 tygodnie pracy**, które nic nie wnoszą do samego pomysłu na produkt.

**UserBridge to klocek startowy, który robi to raz i porządnie.** Kiedy zaczynam nową aplikację, klonuję repozytorium, uzupełniam `.env` i po kilku minutach mam działający backend i frontend z pełną obsługą użytkowników. Od pierwszego dnia piszę już tylko to, co jest specyficzne dla nowego projektu.

> 🎯 **Jedno `git clone` i od razu piszesz funkcje produktu, a nie kolejne logowanie.**

---

## ✨ Co dostajesz od razu

| | Funkcja | Szczegóły |
|---|---|---|
| 📝 | **Rejestracja** | walidacja danych (Bean Validation), hasła hashowane BCryptem |
| 📧 | **Aktywacja konta mailem** | jednorazowy token, szablon HTML (Thymeleaf), wysyłka przez SMTP |
| 🔐 | **Logowanie JWT** | bezstanowe sesje, filtr `JwtAuthFilter`, nieaktywne konto nie zaloguje się |
| 🔁 | **Reset hasła** | link ważny 30 min, **jednorazowy**, nowy link unieważnia poprzedni, brak *user enumeration* |
| 👤 | **Profil** | `GET /me`, edycja danych, usunięcie konta |
| 🛡️ | **Role i własność zasobów** | `ROLE_USER` / `ROLE_ADMIN`, `@PreAuthorize`: użytkownik edytuje i usuwa **tylko siebie** |
| 🌐 | **CORS** | dozwolone originy konfigurowane z `.env` |
| ⚛️ | **Frontend React** | wszystkie ekrany gotowe: logowanie, rejestracja, aktywacja, reset hasła, profil, panel admina |
| 📦 | **Jeden port, jeden jar** | `npm run build` wrzuca frontend do Springa, a backend serwuje stronę i API razem |
| 🔑 | **Sekrety w `.env`** | `application.properties` zawiera tylko placeholdery, więc żadne hasło nie trafia do repo |
| 🐳 | **Baza jednym poleceniem** | Spring Boot sam uruchamia PostgreSQL z `compose.yaml` |
| 📄 | **Swagger UI** | dokumentacja API pod `/swagger-ui.html` |
| 🧪 | **81 testów** | JUnit 5, Mockito, MockMvc, Spring Security Test, H2 |

---

## 🏗️ Architektura

```mermaid
flowchart LR
    subgraph Frontend["⚛️ React · serwowany przez Spring z /static"]
        UI[Pages] --> Ctx[AuthContext<br/>useAuth]
        Ctx --> Api[api/client.js<br/>fetch + JWT]
    end

    subgraph Backend["🌱 Spring Boot :8080 (strona + API)"]
        F[JwtAuthFilter] --> C[UserController]
        C --> Fa[UserFacade]
        Fa --> S[Services<br/>Registration · Login · Confirmation<br/>PasswordReset · Edit · Delete]
        S --> R[(JPA Repositories)]
        S --> M[MailService<br/>Thymeleaf]
    end

    Api -- "/api/** (ten sam origin)" --> F
    R --> DB[(PostgreSQL)]
    M --> SMTP[[SMTP / Gmail]]
```

Backend ma czytelny podział **domain / infrastructure**. Kontroler zna tylko fasadę `UserFacade`, a każdy przypadek użycia ma osobny serwis. Nowy moduł (np. `domain/order`) dodajesz obok, nie ruszając części odpowiedzialnej za użytkowników.

### Przepływ resetu hasła

```mermaid
sequenceDiagram
    actor U as Użytkownik
    participant FE as React
    participant BE as Spring Boot
    participant Mail as SMTP

    U->>FE: „Nie pamiętam hasła” + email
    FE->>BE: POST /api/users/password/forgot
    BE-->>FE: 200 (zawsze, nie zdradza czy konto istnieje)
    BE->>Mail: link /reset-password?token=…
    U->>FE: klik w link, nowe hasło
    FE->>BE: POST /api/users/password/reset
    BE->>BE: walidacja tokenu (ważność, jednorazowość) + BCrypt
    BE-->>FE: 200 → przekierowanie do logowania
```

---

## 🚀 Start w 3 krokach

**Wymagania:** Java 17+ (działa też na 21 i 25), Node 18+, Docker

```bash
# 1. Konfiguracja: skopiuj szablon i uzupełnij sekrety (JWT, SMTP)
cp .env.example .env

# 2. Zbuduj frontend: trafia od razu do src/main/resources/static
cd frontend && npm install && npm run build && cd ..

# 3. Uruchom backend: PostgreSQL wstaje automatycznie z compose.yaml
./mvnw spring-boot:run
```

Otwórz **http://localhost:8080** (albo port z `SERVER_PORT`) i zarejestruj konto. Link aktywacyjny przyjdzie mailem.

| Adres | Co tam jest |
|---|---|
| http://localhost:8080 | aplikacja React |
| http://localhost:8080/api/... | REST API |
| http://localhost:8080/swagger-ui.html | dokumentacja API |

---

## 📦 Jeden port: frontend wbudowany w Spring Boot

Domyślnie **całość działa pod portem backendu**. Nie potrzebujesz osobnego serwera dla frontendu, nie konfigurujesz CORS, a wdrażasz **jeden plik `.jar`**.

```mermaid
flowchart LR
    B["npm run build<br/>(frontend/)"] -->|"outDir"| S["src/main/resources/static/"]
    S --> J["./mvnw package<br/>→ UserBridge.jar"]
    J --> P["http://localhost:8080<br/>strona + /api + swagger"]
```

**Jak to działa:**

- `frontend/vite.config.js`: `build.outDir` wskazuje na `src/main/resources/static`. Folder jest czyszczony przy każdym buildzie, więc nie zbierają się stare pliki.
- `SpaWebConfig`: Spring serwuje pliki z `/static`. Adresy obsługiwane przez React Router (`/login`, `/profile`, `/reset-password?token=…`) po wejściu z linku w mailu albo po odświeżeniu strony dostają `index.html`, a resztą zajmuje się React. Ścieżki `/api/**` i brakujące pliki (`*.js`, `*.css`) zwracają normalne 404.
- `SecurityConfig`: JWT jest wymagany tylko dla `/api/**`. Pliki strony są publiczne.
- Frontend woła API pod względnym adresem (`/api/...`), więc działa na każdym porcie i domenie bez zmian.
- Linki w mailach domyślnie prowadzą na `http://localhost:${SERVER_PORT}`.
- Wynik builda jest w `.gitignore`, bo to artefakt i nie powinien trafiać do repo.

**Wdrożenie produkcyjne:**

```bash
cd frontend && npm run build && cd ..
./mvnw clean package
java -jar target/UserBridge-0.0.1-SNAPSHOT.jar
```

### Tryb deweloperski z hot reload (opcjonalnie)

Gdy intensywnie pracujesz nad UI, uruchom Vite obok backendu:

```bash
cd frontend && npm run dev      # http://localhost:5173
```

Vite przekazuje `/api` do backendu. Port bierze sam z `SERVER_PORT` w głównym `.env`, więc niczego nie musisz ustawiać dwa razy. Jeśli chcesz, żeby linki z maili też otwierały się na 5173, ustaw w `.env` `APP_FRONTEND_URL=http://localhost:5173`. Kiedy skończysz, `npm run build` i znowu wszystko jest pod jednym portem.

---

## 🧱 Jak zacząć z tego nową aplikację

1. `git clone` → zmień nazwę repo i `VITE_APP_NAME` w `frontend/.env`
2. Uzupełnij `.env` (własny `JWT_SECRET`, dane SMTP, nazwa bazy)
3. **Backend:** dodaj swój moduł obok `domain/user`, np. `domain/project`. Endpointy są domyślnie chronione (`anyRequest().authenticated()`), a do sprawdzania własności zasobu użyj `@PreAuthorize`
4. **Frontend:** dopisz endpointy w `src/api/`, a strony umieść w chronionej grupie tras w `src/App.jsx`:

```jsx
<Route element={<ProtectedRoute />}>
  <Route path="/" element={<DashboardPage />} />
  <Route path="/projects" element={<ProjectsPage />} />   {/* ← Twoja nowa funkcja */}
</Route>
```

Zalogowany użytkownik jest dostępny w każdym komponencie:

```jsx
const { user, isAdmin, logout } = useAuth();
```

---

## 🔌 REST API

| Metoda | Endpoint | Dostęp | Opis |
|---|---|---|---|
| `POST` | `/api/users/register` | publiczny | rejestracja + mail aktywacyjny |
| `GET` | `/api/users/confirm?token=` | publiczny | aktywacja konta |
| `POST` | `/api/users/login` | publiczny | zwraca JWT |
| `POST` | `/api/users/password/forgot` | publiczny | wysyła link resetu hasła |
| `POST` | `/api/users/password/reset` | publiczny | ustawia nowe hasło na podstawie tokenu |
| `GET` | `/api/users/me` | zalogowany | profil + role bieżącego użytkownika |
| `PUT` | `/api/users/{id}` | właściciel / admin | edycja profilu |
| `DELETE` | `/api/users/{id}` | właściciel / admin | usunięcie konta |
| `GET` | `/api/users` | admin | lista użytkowników |

---

## ⚙️ Konfiguracja (`.env`)

Wszystkie wartości zależne od środowiska są w jednym pliku `.env`, z którego korzysta i Spring Boot (`spring.config.import`), i Docker Compose. W `application.properties` zostają tylko placeholdery `${...}`. Na produkcji te same nazwy ustawiasz jako zwykłe zmienne środowiskowe.

| Zmienna | Opis |
|---|---|
| `SERVER_PORT` | port backendu (domyślnie 8080) |
| `APP_FRONTEND_URL` | adres w linkach z maili, domyślnie `http://localhost:${SERVER_PORT}` |
| `APP_CORS_ALLOWED_ORIGINS` | originy dopuszczone przez CORS (po przecinku), potrzebne tylko gdy frontend działa na innym adresie |
| `JWT_SECRET`, `JWT_EXPIRATION_DAYS` | klucz podpisu i ważność tokenu |
| `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD` | PostgreSQL |
| `MAIL_USERNAME`, `MAIL_PASSWORD` | SMTP (dla Gmaila: [hasło aplikacji](https://myaccount.google.com/apppasswords)) |
| `PASSWORD_RESET_EXPIRATION_MINUTES` | ważność linku resetu hasła |

Frontend ma własny `frontend/.env`: `VITE_APP_NAME` to nazwa aplikacji w nawigacji, a `VITE_API_URL` zostaw puste, jeśli frontend i API są pod tym samym adresem. Ustawiasz je tylko wtedy, gdy hostujesz frontend osobno.

---

## 🔒 Decyzje dotyczące bezpieczeństwa

- **Hasła:** BCrypt, nigdy nie są zwracane przez API
- **Brak user enumeration:** `/password/forgot` zawsze odpowiada tak samo
- **Tokeny resetu:** UUID, ważne przez krótki czas, jednorazowe, usuwane razem z kontem (`ON DELETE CASCADE`)
- **Autoryzacja na poziomie zasobu:** `@PreAuthorize("@userAccess.isSelfOrAdmin(#id)")`
- **Domyślnie zamknięte:** wszystko poza listą publicznych endpointów wymaga JWT
- **Nieprawidłowy lub wygasły JWT:** żądanie traktowane jako anonimowe (401), nigdy jako błąd 500
- **Sekrety:** tylko w `.env`, który jest w `.gitignore`

---

## 🧪 Testy

```bash
./mvnw test
```

**81 testów** jednostkowych i integracyjnych: serwisy domenowe (Mockito), kontrolery i obsługa błędów (MockMvc + Spring Security Test), repozytoria (H2), wysyłka maili, JWT oraz serwowanie SPA (fallback na `index.html`). Testy nie potrzebują `.env` ani Dockera, więc działają od razu w CI.

---

## 📁 Struktura

```
UserBridge/
├── src/main/java/com/example/userbridge/
│   ├── domain/user/            # logika biznesowa: encje, DTO, serwisy, fasada, wyjątki
│   └── infrastructure/
│       ├── api/                # REST controllers + GlobalExceptionHandler
│       ├── security/           # SecurityConfig, JwtAuthFilter, JwtService, UserAccess
│       ├── web/                # SpaWebConfig: serwowanie Reacta + fallback routingu
│       ├── repository/         # Spring Data JPA
│       └── service/            # MailService
├── src/main/resources/
│   ├── templates/              # maile HTML (aktywacja, reset hasła)
│   └── static/                 # ← tu ląduje `npm run build` (gitignored)
├── frontend/
│   └── src/
│       ├── api/                # client.js (fetch + JWT + błędy), userApi.js
│       ├── auth/               # AuthContext (useAuth), ProtectedRoute
│       ├── components/         # Layout, FormField, Alert, AuthCard, Spinner
│       └── pages/              # Login, Register, Confirm, Forgot/ResetPassword, Dashboard, Profile, Admin
├── docs/                       # nagranie z testami API w Postmanie
├── compose.yaml                # PostgreSQL dla dev
└── .env.example                # szablon konfiguracji
```

---

## 🎬 Demo

Nagranie z testami API w Postmanie: [`docs/UserBridge-Postman-tests.mp4`](docs/UserBridge-Postman-tests.mp4)

---

## 🗺️ Roadmap

- [ ] Refresh tokeny i wylogowanie po stronie serwera
- [ ] Logowanie przez Google / GitHub (OAuth2)
- [ ] Migracje bazy (Flyway) zamiast `ddl-auto`
- [ ] Build frontendu w Mavenie (`frontend-maven-plugin`): jeden `./mvnw package` zamiast dwóch kroków
- [ ] Dockerfile + pipeline CI
- [ ] Zarządzanie rolami z panelu admina

---

<div align="center">

**Autor: Przemysław Włodarczyk** · [GitHub](https://github.com/przemyslawwlodarczyk90)

*Napisane raz, używane przy każdym kolejnym projekcie.* 🧩

</div>
