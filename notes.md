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
    private Author authorEntity;  
  
}
```
 - **`@Builder`** :L'annotazione `@Builder` in Spring Boot semplifica la creazione degli oggetti e migliora la leggibilità del codice
 - **`@Entity`** : Le entità in JPA non sono altro che POJO che rappresentano dati che possono essere persistiti nel database. Un'entità rappresenta una tabella memorizzata in un database. Ogni istanza di un'entità rappresenta una riga nella tabella.
 - **`@Table(name = "tableInDB")`** : Ci dice a quale tabella fa riferimento nel DB
 - **`@Id`**  : Per dire che sarà l'ID nella nostra tabella
 - **`@ManyToOne(cascade = CascadeType.ALL)`**:Specifica la relazione con l' altra tabella. `cascade = CascadeType.ALL` indica che quando ritorniamo un libro, ritorniamo anche l' autore e se dovessimo modificare anche l'autore del libro, i dati verrebbero salvati anche nel DB. Qualora salvassimo un nuovo libro e passassimo un autore, verrebbe anche creato l'autore nel DB
 - **`@JoinColumn(name = "author_id")`** Per dire a quale campo della tabella del DB ci si riferisce


# Hibernate Auto DDL
[Guida](https://docs.spring.io/spring-boot/docs/2.0.0.M6/reference/html/howto-database-initialization.html)
Setta il DB schema in modo automatico, basta aggiungere `spring.jpa.hibernate.ddl-auto=update` in **resources/application.properties** e non dobbiamo scrivere una singola riga di SQL

# Repository

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

Estendendo `CrudRepository< , >` abbiamo accesso a tutto questo:
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

 - Chiamando il metodo con un nome significativo --> [youtube](https://youtu.be/Nv2DERaMx-4?si=XNciK9ECc2Aok4ix&t=12652) e [documentazione ufficiale](https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html)
- Chiamando il metodo con un nome non significativo  (**HQL**). Qui usiamo l'annotazione `@Query("codice HQL qui)` nel metodo dentro alla repository --> [youtube](https://youtu.be/Nv2DERaMx-4?si=xL75wJ_l2KMOanQv&t=13022)


