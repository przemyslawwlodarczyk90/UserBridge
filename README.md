# 🧩 UserBridge - Most do zarządzania użytkownikami 🛡️

**UserBridge** to kompletna aplikacja stworzona w Spring Boot 3.2.5, która umożliwia pełną obsługę użytkowników — od rejestracji po aktualizację profilu i autoryzację. Co więcej, aplikacja ta została zaprojektowana jako gotowy "klocek" — szablon do budowy nowych aplikacji. Można ją łatwo rozszerzać o dodatkowe funkcjonalności i wykorzystywać jako fundament większych projektów.

---

## 🚀 Główne funkcje

- 📝 **Rejestracja** z potwierdzeniem e-mail
- 🔐 **Logowanie** z użyciem JWT
- ✅ **Potwierdzenie konta** przez kliknięcie linku z e-maila
- 👤 **Edycja profilu użytkownika**
- 🧾 **Walidacja danych wejściowych** (e-mail, telefon, wymagane pola)
- 🔒 **Zabezpieczenia JWT** (wewnętrzne zarządzanie tokenami)

---

## ⚙️ Technologie

- ☕ Java 17
- 🌱 Spring Boot 3.2.5
- 🛡 Spring Security + JWT
- 🐘 PostgreSQL, H2 (dla testów)
- 🔄 Hibernate (JPA)
- 🧪 JUnit 5, Mockito
- 📄 Swagger (Springdoc OpenAPI)
- 🛠️ Docker
- ❤️ Thymeleaf, Lombok, Maven

---

## 📚 Dokumentacja API

Swagger UI dostępny pod adresem:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🧪 Testowanie

- ✅ Testy jednostkowe komponentów
- 🔄 Testy integracyjne
- 🧱 Mockowanie zależności z Mockito

Aby uruchomić testy:

```bash
mvn test
```

---

## 🛠️ Uruchomienie projektu

### Wymagania

- Java 17
- Maven 3.8.1+
- PostgreSQL 14
- Docker (opcjonalnie)

### Krok po kroku

```bash
git clone https://github.com/yourusername/UserBridge.git
cd UserBridge
```

Skonfiguruj `application.properties`, podając dane do bazy:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/pgDB
spring.datasource.username=user
spring.datasource.password=password
```

Zbuduj i uruchom aplikację:

```bash
mvn clean install
mvn spring-boot:run
```

Otwórz w przeglądarce:  
[http://localhost:8080](http://localhost:8080)

---

## 🔐 Konfiguracja JWT

```properties
auth.jwt.secret=your_secret_key
auth.jwt.expirationDays=7
auth.jwt.issuer=UserBridge
```

## ✉️ Konfiguracja e-mail

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_email_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

## 👥 Użytkowanie (REST API)

- Rejestracja: `POST /api/users/register`
- Potwierdzenie konta: kliknięcie w link z e-maila
- Logowanie: `POST /api/users/login`
- Edycja profilu: `PUT /api/users/{id}`
- Usuwanie konta: `DELETE /api/users/{id}`
- Lista użytkowników: `GET /api/users`

---

## 🐳 Docker (PostgreSQL)

```bash
docker-compose up
```

---

## 🤝 Wkład

Chcesz pomóc? Śmiało — forkuj repozytorium, otwórz pull request lub zgłoś issue!

---

## 📄 Licencja

Projekt nie posiada obecnie przypisanej licencji.

---

## 🙏 Podziękowania

Dziękujemy społeczności open-source za inspirację i wszystkie narzędzia!

---

Twój most do zarządzania użytkownikami 🧩  
**UserBridge Dev**
