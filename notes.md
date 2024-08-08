# Persistence Layer
Entity + Repository 
![enter image description here](https://i.ibb.co/y0LJnsM/Screenshot-2024-08-07-004402.png)

# Entity
## Cos'è
Una classe **entity** rappresenta una tabella in un database relazionale.

![Relazione tra le due tabelle](https://i.ibb.co/R0mn2qW/Screenshot-2024-08-06-213024.png)
## Esempio (Author.java) - ID generativo
```JAVA
@Data  
@AllArgsConstructor  
@NoArgsConstructor  
@Builder  
@Entity  
@Table(name = "authors")  
public class Author { 

    @Id  
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")
    private Long id;  

    private String name;  

    private Integer age;  
}
```
-   **`@Data`**: Una scorciatoia per @ToString, @EqualsAndHashCode, @Getter su tutti i campi, @Setter su tutti i campi non finali e @RequiredArgsConstructor
-   **`@AllArgsConstructor`**: Genera un costruttore che richiede un argomento per ogni campo nella classe annotata
-   **`@NoArgsConstructor`**: Genera un costruttore senza parametri

> [Utile](https://stackoverflow.com/questions/68314072/why-to-use-allargsconstructor-and-noargsconstructor-together-over-an-entity) per `@AllArgsConstructor`  e per `@NoArgsConstructor`

 - **`@Builder`** :L'annotazione `@Builder` in Spring Boot semplifica la creazione degli oggetti e migliora la leggibilità del codice
 - **`@Entity`** : Le entità in JPA non sono altro che POJO che rappresentano dati che possono essere persistiti nel database. Un'entità rappresenta una tabella memorizzata in un database. Ogni istanza di un'entità rappresenta una riga nella tabella.
 - **`@Table(name = "tableInDB")`** : Ci dice a quale tabella fa riferimento nel DB
 - **`@Id`**  : Per dire che sarà l'ID nella nostra tabella
 - **`@GeneratedValue`**: `(strategy = GenerationType.SEQUENCE, generator = "author_id_seq")`: Per dire che sarà un ID incrementale


# Esempio (Book) - Id nostro
```JAVA
@Data  
@AllArgsConstructor  
@NoArgsConstructor  
@Builder  
@Entity  
@Table(name = "books")  
public class Book {  

    @Id  
    private String isbn;  

    private String title;  

    @ManyToOne(cascade = CascadeType.ALL)  
    @JoinColumn(name = "author_id")
    private Author author;  

}
```
 - **`@Builder`** :L'annotazione `@Builder` in Spring Boot semplifica la creazione degli oggetti e migliora la leggibilità del codice
 - **`@Entity`** : Le entità in JPA non sono altro che POJO che rappresentano dati che possono essere persistiti nel database. Un'entità rappresenta una tabella memorizzata in un database. Ogni istanza di un'entità rappresenta una riga nella tabella.
 - **`@Table(name = "tableInDB")`** : Ci dice a quale tabella fa riferimento nel DB
 - **`@Id`**  : Per dire che sarà l'ID nella nostra tabella
 - **`@ManyToOne(cascade = CascadeType.ALL)`**:Specifica la relazione con l' altra tabella. `cascade = CascadeType.ALL` indica che quando ritorniamo un libro, ritorniamo anche l' autore e se dovessimo modificare anche l'autore del libro, i dati verrebbero salvati anche nel DB. Qualora salvassimo un nuovo libro e passassimo un autore, verrebbe anche creato l'autore nel DB
 - **`@JoinColumn(name = "author_id")`** Per dire a quale campo della tabella del DB ci si riferisce


# Hibernate Auto DDL
Hibernate è uno strumento di mapping object-relational (ORM) open source che fornisce un framework per mappare i modelli di dominio orientati agli oggetti ai database relazionali per le applicazioni web.

[Guida](https://docs.spring.io/spring-boot/docs/2.0.0.M6/reference/html/howto-database-initialization.html)

Setta il DB schema in modo automatico, basta aggiungere `spring.jpa.hibernate.ddl-auto=update` in **resources/application.properties** e non dobbiamo scrivere una singola riga di SQL

# Repository (una per ogni entità)

La "respository" (AuthorRepository/Book Repository)  è l’astrazione che usiamo per salvare e ottenere cose dal DB (evitando di scrivere SQL)
Una cosa che fa Spring è creare l’implementazione delle repository per noi, quindi noi andremo **solo** a creare l’interfaccia; tutto questo è possibile andando ad estendere con la corretta Repository, per esempio: 
```JAVA 
@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> { }
```
dove

 - `Author` è il l'entity con cui andiamo ad interagire
 - `Long` è il tipo dell' ID dell' entity

> **`@Repository`** : funziona come @Bean, ma è per le repository

Estendendo `CrudRepository< , >` abbiamo accesso a tutto questo: (**CRUD** + altro) 
```JAVA
public interface CrudRepository <T, ID> extends org.springframework.data.repository.Repository<T,ID> { 

    <S extends T> S save(S entity);  

    <S extends T> java.lang.Iterable<S> saveAll(java.lang.Iterable<S> entities);  

    java.util.Optional<T> findById(ID id);  

    boolean existsById(ID id);  

    java.lang.Iterable<T> findAll();  

    java.lang.Iterable<T> findAllById(java.lang.Iterable<ID> ids);  

    long count();  

    void deleteById(ID id);  

    void delete(T entity);  

    void deleteAllById(java.lang.Iterable<? extends ID> ids);  

    void deleteAll(java.lang.Iterable<? extends T> entities);  

    void deleteAll();  
}
```
` <S extends T> S save(S entity);`è utilizzato sia per creare e fare l'update dei dati del DB

## Fare query piu' complicate (no CRUD)

 1. Chiamando il metodo con un nome significativo --> [youtube](https://youtu.be/Nv2DERaMx-4?si=XNciK9ECc2Aok4ix&t=12652) e [documentazione ufficiale](https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html)
 2. Chiamando il metodo con un nome non significativo  (**HQL**). Qui usiamo l'annotazione `@Query("codice HQL qui)` nel metodo dentro alla repository --> [youtube](https://youtu.be/Nv2DERaMx-4?si=xL75wJ_l2KMOanQv&t=13022)

# Jackson
Jackson è una popolare libreria per la serializzazione/deserializzazione di oggetti Java in vari formati di testo. La classe  ObjectMapper è il modo principale della libreria per lavorare con il formato JSON.
Marshalling = Java Object -> JSON
Unmarshalling = JSON -> Java Object

# REST API
`/books` si chiama **risorsa**. La convezione è quella di usare sempre il termine al plurale
## API books (ID nostro)
```c++
 PUT /books {isbn}		// Siccome siamo noi che diamo l' ID nel DB, non usiamo POST, ma PUT (convenzione). 
						// Ritorna il libro creato + HTTP STATUS 201 se creato
				   
 GET /books/{isbn}		// Ritorna il libro trovato (se trovato) + HTTP STATUS 200
						// Ritorna un body vuoto (se non trovato) + HTTP STATUS 404
				   
 GET /books				// Ritorna SEMPRE HTTP STATUS 200 (anche se non c'è nulla)
 
 PUT /books/{isbn}		// Quando facciamo l'update ci aspettiamo il libro + HTTP STATUS  200 (non 201)
						// Qui devo fornire TUTTI gli attributi e l'intero oggetto viene aggiornato nel DB
 
 PATCH /books/{isbn}	// SOLO gli attributi che vengono passati vengono aggiornati nel DB
						// Se tutto va bene, ottengo HTTP STATUS 200
 
 DELETE /books/{isbn} //Non ritorna un body + HTTPS STATUS 204
```
## Authors API (ID del DB)
```c++
 POST/authors	// Ritorna l'author object nel response body + HTTP STATUS 201
						
 GET /authors/{id}		
				   
 GET /authors
 
 PUT /authors/{id}		
 
 PATCH /authors/{id}	
 
 DELETE /authors/{id}
```

# Presentation Layer
Usaremo i controller per costuire il presentation layer.
Usaremo **`@RestController`** per indicare che è un **RestController**
``` JAVA
@RestController  
public class AuthorController {  
}
```

Nel controller andremo a creare le funzionalità CRUD.
## Create
``` JAVA
@RestController  
public class AuthorController {  

    @PostMapping(path = "/authors")  
    public Author createAuthor(@RequestBody Author author) {  

    }  
}
```
`@PostMapping(path = "/authors")` ci indica che è un HTTP POST endpoint 

Noi ci aspettiamo un Author JSON nel request body, quindi usiamo `@RequestBody` che ci assicura che 

 1. verrà letto il body (che contiene il JSON)
 2. verrà convertito in un Java Object

>  **@RequestBody** dice a Spring di cercare nell' HTTP Request Body un Author Object rappresentato come JSON e lo converte in Java Object

# Service Layer
Serve a "linkare" il presentation con il persistance layer.

Creiamo una interfaccia (così da avere piu' implementazioni future)
```JAVA
public interface AuthorService {  
    Author createAuthor(Author author);  //prende in input un Author, che è l' author che vogliamo creare 
}
```


### Injection del Servizio (back to Presentation Layer)
Ovviamente per usare il servizio all'interno del controller, useremo il pattern *dependency injection* e otteniamo:
```JAVA
@RestController  
public class AuthorController {  

    private AuthorService authorService;  

    public AuthorController(AuthorService authorServices) {  
        this.authorService = authorService;  
    }  

    @PostMapping(path = "/authors")  
    public Author createAuthor(@RequestBody Author author) {  
        return authorService.createAuthor(author);  

    }  
}
```
Facendo così però è ancora lontano dalla perfezione, perchè stiamo usando nel **Presentation Layer** l' **Author Object**, il quale è una **Entity** che dovrebbe esistere nel **Persistance Layer** e al massimo nel **Service Layer** e non vogliamo in nessun modo che il Presentation Layer sappia come funziona il **Persistance Layer**.

Per ovviare a questo problema useremo i **DTO**, così che il **Service Layer** continui a ritornare una **Entity**, ma il **Presentation Layer** lo mapperà in un **DTO** e sarà proprio un **DTO** ciò che manderà e si aspetterà in cose come il **request body**

## DTO
Il DTO sarà un POJO
```JAVA
@Data  
@AllArgsConstructor  
@NoArgsConstructor // per Jackson
@Builder  
public class AuthorDto {  

    private Long id;  

    private String name;  

    private Integer age;  
}
```

Dopo aver fatto il DTO quindi, andiamo a modificare il controller del Presentation Layer
```JAVA
@RestController  
public class AuthorController {  

    private AuthorService authorService;  

    public AuthorController(AuthorService authorServices) {  
        this.authorService = authorServices;  
    }  

    @PostMapping(path = "/authors")  
    public AuthorDto createAuthor(@RequestBody AuthorDto author) {  
        return authorService.createAuthor(author);  
    }  
}
```

Adesso però abbiamo ancora un problema.
``` java
    return authorService.createAuthor(author); 
```
vuole ancora un **author** un tipo author. 

Per convertire una Entity in un DTO e viceversa useremo la libreria [ModelMapper](https://modelmapper.org/) --> [youtube](https://www.youtube.com/watch?v=Nv2DERaMx-4&t=14799s)

# Model Mapper
Siccome **ModelMapper** è un oggetto, un buon metodo per utilizzarla è quello di creare un nuovo **package** chiamato "**config**" e dentro andremo a creare una classe chiamata **MapperConfig**:
![enter image description here](https://i.ibb.co/RjxmG9V/image.png)
che sarà tipo:
```JAVA
@Configuration  
public class MapperConfig {  
    @Bean  
    public ModelMapper modelMapper() {  
        return new ModelMapper();  
    }  
}

```
---
## @Configuration vs @Component ( approfondimento)

**@Configuration**
- **Scopo:** Utilizzata per definire classi che configurano e forniscono bean al contesto Spring.
- **Annotazione di Classe:** *@Configuration*.
- **Gestione del Ciclo di Vita:** Le classi annotate con *@Configuration* sono proxyizzate da Spring per garantire che i bean definiti con @Bean siano singleton.
- **Uso Tipico:**
  - Configurazione di componenti complessi come DataSource, Security, ecc.
  - Centralizzazione della configurazione dei bean.
  - Personalizzazione di bean con configurazioni specifiche.

**Esempio:**
```JAVA
@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Configurazioni personalizzate
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }
}
```

**@Component**
- **Scopo:** Utilizzata per registrare una classe come bean nel contesto Spring.
- **Annotazione di Classe:** @Component.
- **Gestione del Ciclo di Vita:** Le classi annotate con *@Component* non sono proxyizzate come quelle con *@Configuration*, quindi ogni chiamata a un metodo *@Bean* potrebbe creare una nuova istanza del bean, a meno che non sia gestito diversamente.
- Uso Tipico:
  - Registrazione di servizi generici, helper o utility.
  - Registrazione di componenti come schedulatori, listener di eventi, ecc.
  - Annotazioni specializzate come @Service, @Repository, @Controller che estendono @Component.

Esempio:
```JAVA
@Component
public class EmailService {
    public void sendEmail(String to, String subject, String body) {
        // Logica per inviare un'email
        System.out.println("Sending email to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        // Codice reale per inviare l'email
    }
}
```

Conclusione
- @Configuration è ideale per classi che definiscono bean e configurazioni complesse, garantendo gestione e personalizzazione centralizzate.
- @Component è adatto per registrare classi come bean generici nel contesto Spring, spesso utilizzato per servizi, utility e componenti operativi.
-----

## Interfaccia Mapper
Ora andiamo a creare una nuova folder chiamata "mappers" e per tenere le cose pulite e per permetterci di swappare tra future librerie di mapping piu' avanti, andiamo a creare una interfaccia chiamata Mapper che andrà a contenere tutta la logica per mappare:
``` JAVA
public interface Mapper <A,B> {  

    B mapto(A a);  

    A mapfrom(B b);  
}
```
Possiamo implementare questa interfaccia con i **beans** e possiamo fare l'inject di questi beans quando ci servono.
### Implementazione AuthorMapper
Usiamo  `@Component` così da renderlo injectable 

``` JAVA
@Component  
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {  

    private ModelMapper modelMapper;  

    public AuthorMapperImpl(ModelMapper modelMapper) {  
        this.modelMapper = modelMapper;  
    }  

    @Override  
    public AuthorDto mapto(AuthorEntity authorEntity) {  
        return modelMapper.map (authorEntity, AuthorDto.class);  
    }  

    @Override  
    public AuthorEntity mapfrom(AuthorDto authorDto) {  
        return modelMapper.map(authorDto, AuthorEntity.class);  
    }  
}
```


----



andiamo ad implementare il Servizio (AuthorServiceImpl)
``` JAVA
@Service  
public class AuthorServiceImpl implements AuthorService {  

    private AuthorRepository authorRepository;  

    public AuthorServiceImpl(AuthorRepository authorRepository) {  
        this.authorRepository = authorRepository;  
    }  

    @Override  
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {  
        return authorRepository.save(authorEntity);  
    }  
}
```
Di default `authorRepository.save(authorEntity);` JPA ritorna una Entity, quindi ritorniamo una Entity

# Integration test endpoint
[Youtube](https://youtu.be/Nv2DERaMx-4?si=A9SX5spshuEhc7G9&t=15335)

## Sistemiamo il controller
Siccome il controller ritorna un HTTP STATUS 200 e noi vogliamo HTTP STATUS 201, andremo ad apportare le sia sul **tipo di ritorno** che sul **return**
``` JAVA
// DA

@PostMapping(path = "/authors")
public AuthorDto createAuthor(@RequestBody AuthorDto author) {

    AuthorEntity authorEntity = authorMapper.mapfrom(author);
    AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);

    return authorMapper.mapto(savedAuthorEntity);
}
}


//  A
@PostMapping(path = "/authors")  
public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {  

    AuthorEntity authorEntity = authorMapper.mapfrom(author);  
    AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);  

    return new ResponseEntity<>(authorMapper.mapto(savedAuthorEntity), HttpStatus.CREATED);  
}
```
 **tipo di ritorno:** `AuthorDto `--->` ResponseEntity<AuthorDto>`: ci permette di cambiare lo status code della response
 **return**: `return authorMapper.mapto(savedAuthorEntity);` --->`return new ResponseEntity<>(authorMapper.mapto(savedAuthorEntity), HttpStatus.CREATED);`

## Book DTO
Anche questo sarà un POJO e risulterà così:
``` JAVA
@Data  
@AllArgsConstructor  
@NoArgsConstructor  
@Builder  
public class BookDto {  

    private String isbn;  

    private String title;  

    private AuthorDto authorEntity;  //adesso lavoriamo con i DTO e non con le entity
}
```
## Book Controller
Siccome l' ISBN sarà nel path usiamo **@PathVariable** per indicare che sarà nel path
``` JAVA
@RestController  
public class BookController {

    @PutMapping("/books/{isbn}")  
    public ResponseEntity<BookDto> createBook(@PathVariable ("isbn") String isbn, @RequestBody BookDto bookDto {} 
}
```
## BookService
``` JAVA
@Service  
public class BookServiceImpl implements BookService {  

    private BookRepository bookRepository;  

    public BookServiceImpl(BookRepository bookRepository) {  
        this.bookRepository = bookRepository;  
    }  

    @Override  
    public BookEntity createBook(String isbn, BookEntity bookToCreate) {  
        //per assicurarci che l'ISBN passato nell'oggetto sia uguale a quello passato nell'URL  
        bookToCreate.setIsbn(isbn);  
        return bookRepository.save(bookToCreate);  
    }  
}
```

# Read (CRUD) Author  Controller
``` JAVA
@GetMapping(path="/authors")  
public List<AuthorDto> listAuthors(){  
    List<AuthorEntity> authors = authorService.findAll();  

    authors.stream()  
        .map(authorMapper::mapTo)  
        .collect(Collectors.toList());    
}
```

Spiegazione del Metodo: 
``` JAVA
List<AuthorEntity> authors = authorService.findAll(); 
```

Questa riga recupera una lista di entità AuthorEntity dal servizio authorService. Presumibilmente, authorService è un componente di servizio che interagisce con il database o un'altra fonte di dati per ottenere tutte le entità degli autori.

---
``` JAVA
authors.stream()
```
Questa riga crea uno stream dalla lista di AuthorEntity. Gli stream in Java forniscono un modo per elaborare sequenze di elementi in modo dichiarativo.
``` JAVA
.map(authorMapper::mapTo)
```
Il metodo map applica una funzione a ciascun elemento dello stream. In questo caso, authorMapper::mapTo è un riferimento al metodo mapTo di un oggetto authorMapper. Questo metodo converte ogni AuthorEntity in un oggetto AuthorDto. AuthorDto è probabilmente una classe che rappresenta i dati dell'autore in un formato più adatto per il client.
``` JAVA
.collect(Collectors.toList());
```
Questa riga raccoglie gli elementi trasformati dallo stream in una lista. Dopo aver mappato ogni AuthorEntity in AuthorDto, la lista finale di AuthorDto viene raccolta e restituita.

---
# Read (CRUD) Author Service
``` JAVA
@Override  
public List<AuthorEntity> findAll() {  
    return StreamSupport.stream(authorRepository  
                                .findAll()  
                                .spliterator(),  
                                false)  
        .collect(Collectors.toList());   
}
```

---

``` JAVA
authorRepository.findAll()
```

authorRepository è probabilmente un'istanza di un'interfaccia che estende CrudRepository, JpaRepository, o una simile interfaccia di Spring Data JPA. Il metodo findAll() restituisce tutti gli oggetti AuthorEntity dal database. Questa chiamata restituisce un oggetto di tipo Iterable<AuthorEntity>, che rappresenta una sequenza di entità.

----
``` JAVA
spliterator()
```

spliterator() è un metodo che fornisce un Spliterator per iterare sugli elementi in Iterable. Spliterator è un'interfaccia introdotta in Java 8 per la suddivisione e l'elaborazione parallela di sequenze di elementi.

---
``` JAVA
StreamSupport.stream(..., false)
```

Questo metodo crea uno Stream a partire dallo Spliterator. Il secondo argomento false indica che lo stream non deve essere parallelo. Se fosse true, lo stream sarebbe parallelo e potrebbe essere elaborato in parallelo.

---
``` JAVA
.collect(Collectors.toList())
```
Questa riga raccoglie gli elementi dello stream in una lista. Il Collector Collectors.toList() accumula gli elementi dello stream in una List.

---

Usiamo **StreamSupport** per prendere lo **Spliterator** dal **findAll** (che è un **Iterable**)  e lo mettiamo in una **lista**

 `Iterable <Entity>` <---Spliterator --- `List <Entity>` 

# getAuthorByID- Author Service
## Spiegazione Lamda
### Codice di Esempio

``` JAVA
Optional<AuthorEntity> foundAuthor = authorService.findOne(id);

return foundAuthor.map(authorEntity -> {
    AuthorDto authorDto = authorMapper.mapTo(authorEntity);
    return new ResponseEntity<>(authorDto, HttpStatus.OK);
}).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));` 
```

### Spiegazione Dettagliata

1.  **Recupero dell'Autore**
   
    
    `Optional<AuthorEntity> foundAuthor = authorService.findOne(id);` 
    
    Qui, `authorService.findOne(id)` cerca un autore con l'ID specificato e restituisce un `Optional<AuthorEntity>`. Questo `Optional` può contenere un autore (`AuthorEntity`) o essere vuoto se l'autore non viene trovato.
    
2.  **Uso di `map`**
  
    ``` JAVA
    return foundAuthor.map(authorEntity -> {
        AuthorDto authorDto = authorMapper.mapTo(authorEntity);
        return new ResponseEntity<>(authorDto, HttpStatus.OK);
    }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    ```
    **Cosa fa `map`?**
    
    -   **`map`** è un metodo di `Optional` che applica una funzione al valore contenuto nell'`Optional`, se presente, e restituisce un nuovo `Optional` con il risultato della funzione.
    -   Se l'`Optional` è vuoto, la funzione non viene applicata e viene restituito l'`Optional` vuoto senza modifiche.
    
    **Cosa fa la Lambda all'interno di `map`?**
    
    -   **`authorEntity -> { ... }`** è una lambda che riceve `authorEntity` come input.
    -   All'interno della lambda:
        -   **`AuthorDto authorDto = authorMapper.mapTo(authorEntity);`**: Converte l'oggetto `AuthorEntity` in un oggetto `AuthorDto` usando un mapper.
        -   **`return new ResponseEntity<>(authorDto, HttpStatus.OK);`**: Crea una risposta HTTP con il DTO e uno stato HTTP OK (200), indicando che tutto è andato bene.
    
    In altre parole, se `foundAuthor` contiene un autore, la lambda converte quell'autore in un DTO e restituisce una risposta HTTP con stato OK.
    
3.  **Gestione dell'Optional Vuoto**
    
    
    
    `.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));` 
    
    -   Se l'`Optional` è vuoto (cioè l'autore non è stato trovato), `orElse` restituisce una risposta HTTP con stato NOT FOUND (404), indicando che la risorsa richiesta non esiste.

### Perché Usare `map` e Lambda?

-   **`map`**: Serve per trasformare il valore all'interno dell'`Optional`. Se trovi l'autore, vuoi trasformarlo in una risposta HTTP. Se non trovi l'autore, non fai nulla con la lambda ma gestisci il caso con `orElse`.
    
-   **Lambda**: Rende il codice più compatto e leggibile. Senza lambda, dovresti scrivere una classe o un metodo separato per gestire la trasformazione, il che sarebbe più lungo e meno chiaro.
    


### Riassumendo

1.  **`map`** è usato per applicare una funzione se l'`Optional` contiene un valore.
2.  **Lambda** è la funzione che definisce cosa fare con quel valore.
3.  **`orElse`** gestisce il caso in cui l'`Optional` è vuoto.

## fullUpdateAuthor (Refactor CreateAuthor)
Qui vedo che posso riutilizzare il createAuthor del servizio, quindi faccio un refactor in AuthorService e modificio il metodo da createAuthor in saveAuthor (così da poterlo riutilizzare anche facendo l'update) e ottengo

``` JAVA
public interface AuthorService {  
    AuthorEntity saveAuthor(AuthorEntity authorEntity);  

    List<AuthorEntity> findAll();  

    Optional<AuthorEntity> findOne(Long id);  

    boolean existsById(Long id);  
}
```

## UpdateBook
Stessa cosa qui, perchè anche qui andremo ad utilizzare 
``` JAVA
@PutMapping("/books/{isbn}")  
```
L' unica cosa che cambia dal **create** all' update è l'HTTP STATUS code che sarà
- Creazione -201
- Update - 200

Quindi facciamo un refactor e passiamo da 
``` JAVA
// BookController.java

@PutMapping("/books/{isbn}")  
public ResponseEntity<BookDto> createBook(  
    @PathVariable ("isbn") String isbn,  
    @RequestBody BookDto bookDto){  

    BookEntity bookEntity =  bookMapper.mapfrom(bookDto);  

    BookEntity savedBookEntity = bookService.createBook(isbn,bookEntity);  

    BookDto savedBookDto = bookMapper.mapto(savedBookEntity);  

    return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);  
}
```

a 
``` JAVA
// BookController.java

@PutMapping("/books/{isbn}")  
public ResponseEntity<BookDto> createUpdateBook(@PathVariable ("isbn") String isbn, @RequestBody BookDto bookDto){  

    BookEntity bookEntity =  bookMapper.mapfrom(bookDto);  

    boolean bookExists =  bookService.existsByIsbn(isbn);  

    BookEntity savedBookEntity = bookService.createUpdateBook(isbn,bookEntity);  
    BookDto savedBookDto = bookMapper.mapto(savedBookEntity);  

    if (bookExists)  
        return new ResponseEntity<>(savedBookDto, HttpStatus.OK); // update  
    else  
        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);  
}
```
e facciamo anche un refactor del metodo createBook del servizio, perchè se passiamo un ISBN esistente a **save**, allora andrà a fare l'update, se invece l'ISBN non esiste, allora andrà a crearlo
``` JAVA
//BookService.java

public interface BookService {  
    BookEntity createBook(String isbn, BookEntity bookToCreate);  

    List<BookEntity> findAll();  

    Optional<BookEntity> findOne(String isbn);  
}
```
a
``` JAVA
//BookService.java

public interface BookService {  
    BookEntity createUpdateBook(String isbn, BookEntity bookToCreate);  

    List<BookEntity> findAll();  

    Optional<BookEntity> findOne(String isbn);  
}
```


## PartialUpdate (AuthorService)
``` JAVA
@Override  
public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {  

    authorEntity.setId(id); //this time we set the ID in the Service  

    return authorRepository.findById(id).map(existingAuthor -> {  
        Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);  
        Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);  
        return authorRepository.save(existingAuthor);  
    }).orElseThrow(()-> new RuntimeException("Author does not exits with id: " + id));  
}  
}
```
Questa funzione aggiorna parzialmente un'entità `AuthorEntity` nel database, identificata da un ID specifico. 

 `return authorRepository.findById(id).map(existingAuthor -> {` 

Qui, la funzione chiama `findById(id)` sul repository (`authorRepository`) per cercare l'autore esistente con l'ID fornito. Se l'autore esiste, il risultato è avvolto in un `Optional`, e la funzione `map` viene applicata.

La funzione lambda all'interno di `map` è definita come segue:

 `Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);` 

Questo codice prende il nome dall'entità `authorEntity`. Se il nome non è `null`, allora `ifPresent` esegue il metodo `setName` sull'autore esistente (`existingAuthor`).

 `Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);` 

Analogamente, questo codice prende l'età dall'entità `authorEntity`. Se l'età non è `null`, allora `ifPresent` esegue il metodo `setAge` sull'autore esistente (`existingAuthor`).

Il metodo `::` è una forma abbreviata per chiamare un metodo. In questo caso, `existingAuthor::setName` è equivalente a `name -> existingAuthor.setName(name)`.

 `return authorRepository.save(existingAuthor);` 

Qui, l'autore aggiornato viene salvato nel repository.

 `}).orElseThrow(()-> new RuntimeException("Author does not exist with id: " + id));` 

Se `findById(id)` non trova l'autore, `orElseThrow` lancia una `RuntimeException` con un messaggio che specifica che l'autore con l'ID dato non esiste.

### Dettaglio su Lambda e `::`

-   **Lambda**: Una funzione lambda è una funzione anonima che può essere usata come parametro. Esempio: `x -> x * x` è una lambda che prende un parametro `x` e restituisce `x` al quadrato.
-   **Method Reference (`::`)**: È una scorciatoia per riferirsi a metodi esistenti. `existingAuthor::setName` è una forma abbreviata per `name -> existingAuthor.setName(name)`.

# Nested Object Conversion
Per "nestare" le cose modifichiamo **MapperConfig** :
``` JAVA
@Configuration  
public class MapperConfig {  
    @Bean  
    public ModelMapper modelMapper() {  
        ModelMapper modelMapper = new ModelMapper();  
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);  

        return modelMapper;  
    }  
}
```

### Configurazione della Strategia `LOOSE`

Quando imposti la strategia di corrispondenza su `LOOSE`, stai dicendo al `ModelMapper` di essere più permissivo nella ricerca di corrispondenze tra i campi degli oggetti di origine e di destinazione.

`modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);` 

Questo permette al `ModelMapper` di gestire conversioni tra Entity e DTO anche se i campi non corrispondono esattamente per nome o struttura. Per esempio:

### Come Funziona

Con la strategia `LOOSE`, il `ModelMapper` è in grado di capire che:

-   `emailAddress` in `UserEntity` corrisponde a `email` in `UserDTO`.

Anche se i nomi dei campi non corrispondono esattamente, il `ModelMapper` utilizza convenzioni e somiglianze nei nomi per trovare le corrispondenze.

### Perché Funziona la Conversione tra Entity e DTO

1.  **Riconoscimento delle Convenzioni di Denominazione**: La strategia `LOOSE` permette al `ModelMapper` di riconoscere che `emailAddress` in `UserEntity` e `email` in `UserDTO` rappresentano lo stesso concetto, anche se i nomi non sono identici.
2.  **Flessibilità nei Nomi dei Campi**: `ModelMapper` può mappare campi con nomi che hanno suffissi o prefissi diversi, basandosi su convenzioni comuni.
3.  **Riduzione della Necessità di Configurazione Manuale**: Con la strategia `LOOSE`, si riduce il bisogno di configurare manualmente ogni singola mappatura tra campi, rendendo il codice più pulito e meno soggetto a errori.

