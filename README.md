# 📚 Prototipo1

Prototipo1 è un progetto Spring Boot che espone delle REST API per la gestione di libri e autori. Utilizza un database PostgreSQL e include funzionalità per la creazione, aggiornamento, lettura e cancellazione di autori e libri.

## 📋 Struttura del progetto

Il progetto è composto da due controller principali:
- `AuthorController`: gestisce le operazioni CRUD per gli autori.
- `BookController`: gestisce le operazioni CRUD per i libri.

## 🛠 Prerequisiti

Assicurati di avere installato i seguenti strumenti:
- Java 17
- Maven
- Docker

## ⚙️ Configurazione del database

Il progetto utilizza un database PostgreSQL. È possibile avviare un'istanza di PostgreSQL utilizzando Docker. Esegui il seguente comando per avviare il container:

```sh
docker-compose up
```

Questo comando utilizza il file `docker-compose.yml` che configura il servizio PostgreSQL con la password di default `password!`.

## 🚀 Compilazione ed esecuzione del progetto

Per compilare ed eseguire il progetto, segui questi passaggi:

1. Clona il repository:

   ```sh
   git clone https://github.com/bandomatteo/Prototipo1.git
   cd Prototipo1
   ```

2. Compila il progetto con Maven:

   ```sh
   mvn clean install
   ```

3. Avvia l'applicazione Spring Boot:

   ```sh
   mvn spring-boot:run
   ```

## 🔌Endpoint delle API

### AuthorController

- **POST /authors**
  - Crea un nuovo autore.
  - Request Body: `AuthorDto`
  - Response: `AuthorDto` con stato `201 Created`
- **GET /authors**
  - Recupera la lista di tutti gli autori.
  - Response: `List<AuthorDto>`
- **GET /authors/{id}**
  - Recupera un autore per ID.
  - Response: `AuthorDto` con stato `200 OK` se trovato, `404 Not Found` altrimenti
- **PUT /authors/{id}**
  - Aggiorna completamente un autore esistente.
  - Request Body: `AuthorDto`
  - Response: `AuthorDto` con stato `200 OK` se aggiornato, `404 Not Found` altrimenti
- **PATCH /authors/{id}**
  - Aggiorna parzialmente un autore esistente.
  - Request Body: `AuthorDto`
  - Response: `AuthorDto` con stato `200 OK` se aggiornato, `404 Not Found` altrimenti
- **DELETE /authors/{id}**
  - Cancella un autore per ID.
  - Response: stato `204 No Content`

### BookController

- **PUT /books/{isbn}**

  - Crea o aggiorna un libro.
  - Request Body: `BookDto`
  - Response: `BookDto` con stato `200 OK` se aggiornato, `201 Created` se creato

- **GET /books**

  - Recupera la lista di tutti i libri.
  - Response: `List<BookDto>`

- **GET /books/{isbn}**

  - Recupera un libro per ISBN.
  - Response: `BookDto` con stato `200 OK` se trovato, `404 Not Found` altrimenti

- **PATCH /books/{isbn}**

  - Aggiorna parzialmente un libro esistente.
  - Request Body: `BookDto`
  - Response: `BookDto` con stato `200 OK` se aggiornato, `404 Not Found` altrimenti

- **DELETE /books/{isbn}**

  - Cancella un libro per ISBN.
  - Response: stato `204 No Content`

  ## ⚙️ Configurazione Maven

  Il file `pom.xml` include le seguenti dipendenze principali:

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- PostgreSQL Driver
- ModelMapper
- Lombok
- Spring Boot Starter Test

  ## 👤 Autore

- Matteo Bando
