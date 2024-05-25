# UserBridge

UserBridge to aplikacja napisana w Spring Boot, która służy do zarządzania rejestracją użytkowników, logowaniem i aktualizacją profili. Aplikacja oferuje funkcje takie jak potwierdzenie rejestracji przez e-mail, uwierzytelnianie JWT oraz zarządzanie szczegółowymi informacjami o użytkownikach.

## Funkcje

- **Rejestracja użytkowników:** Nowi użytkownicy mogą się rejestrować, a na ich adres e-mail wysyłane jest potwierdzenie rejestracji.
- **Logowanie:** Użytkownicy mogą logować się za pomocą adresu e-mail i hasła.
- **Potwierdzenie e-mail:** Na adres e-mail użytkownika wysyłany jest token potwierdzający, który musi zostać zweryfikowany, aby aktywować konto.
- **Zarządzanie użytkownikami:** Użytkownicy mogą aktualizować swoje dane profilowe po zalogowaniu.
- **Bezpieczeństwo:** Do zabezpieczania punktów końcowych używane są JWT, jednak zarządzanie kluczami JWT odbywa się wewnętrznie i nie jest eksponowane ani wymagane od użytkownika.
- **Walidacja:** Obszerna walidacja danych wejściowych użytkownika, w tym format adresu e-mail, obowiązkowe pola i poprawne numery telefonów.

## Technologie

- **Język programowania:** Java 17
- **Framework:** Spring Boot 3.2.5
- **Bezpieczeństwo:** Spring Security, JWT (JSON Web Token)
- **Baza danych:** PostgreSQL, H2 (do testów)
- **ORM:** Hibernate
- **Testy:** JUnit 5, Mockito
- **Inne:** Lombok, Thymeleaf, Docker, Maven, Swagger (Springdoc OpenAPI)

## Wymagania

- Java 17
- Maven 3.8.1 lub nowszy
- PostgreSQL 14
- Docker (opcjonalnie, do uruchamiania PostgreSQL w kontenerze)



## Instalacja

1. **Sklonuj repozytorium:**
   ```sh
   git clone https://github.com/yourusername/UserBridge.git
   cd UserBridge



## Skonfiguruj bazę danych
**Zaktualizuj plik** 

`application.properties` **swoimi danymi dostępowymi do bazy danych PostgreSQL.**


## **Zbuduj aplikację:**


mvn clean install


# **Uruchom aplikację:**


mvn spring-boot:run

## **Uzyskaj dostęp do aplikacji:**

Otwórz http://localhost:8080 w swojej przeglądarce internetowej.

# Konfiguracja

## Konfiguracja bazy danych

**Upewnij się, że zaktualizowałeś następujące właściwości w pliku application.properties:**




spring.datasource.url=jdbc:postgresql://localhost:5432/pgDB
spring.datasource.username=user
spring.datasource.password=password

## Konfiguracja JWT


**Ustawienia JWT są skonfigurowane w pliku application.properties:**



auth.jwt.secret=your_secret_key
auth.jwt.expirationDays=7
auth.jwt.issuer=UserBridge

## Konfiguracja poczty


**Skonfiguruj ustawienia e-mail, aby wysyłać e-maile potwierdzające:**



spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_email_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# Użytkowanie

## **Rejestracja użytkownika**

Aby zarejestrować nowego użytkownika, wyślij żądanie POST na /api/users/register z ciałem JSON:



## Potwierdzenie rejestracji użytkownika

Po zarejestrowaniu użytkownika, na jego adres e-mail zostanie wysłany link potwierdzający. Użytkownik musi kliknąć na ten link, aby aktywować swoje konto.

## Logowanie

Aby się zalogować, wyślij żądanie POST na /api/users/login z  ciałem JSON:


## Edycja użytkownika

Aby edytować dane użytkownika, wyślij żądanie PUT na /api/users/{id} z  ciałem JSON:


## Usunięcie użytkownika

Aby usunąć użytkownika, wyślij żądanie DELETE na /api/users/{id}.

## Pobranie listy wszystkich użytkowników

Aby pobrać listę wszystkich użytkowników, wyślij żądanie GET na /api/users.

## Uruchamianie testów

Aplikacja zawiera testy jednostkowe dla różnych komponentów. Aby uruchomić testy, użyj następującego polecenia:


**mvn test**

## Docker

Aby uruchomić PostgreSQL za pomocą Dockera, użyj dostarczonego pliku docker-compose.yml:


**docker-compose up**


## Wkład

Jeśli chcesz przyczynić się do rozwoju aplikacji, otwieraj zgłoszenia pull request lub issues. Wkład jest mile widziany!

## Licencja

Ten projekt nie posiada obecnie licencji.

## Podziękowania

Szczególne podziękowania dla społeczności open-source za dostarczenie narzędzi i bibliotek, które umożliwiły powstanie tego projektu.
