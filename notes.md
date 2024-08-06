# Entity
## Cos'è
Una classe **entity** rappresenta una tabella in un database relazionale.
## Esempio
![Relazione tra le due tabelle](https://i.ibb.co/R0mn2qW/Screenshot-2024-08-06-213024.png)
### Esempio (Author.java) - ID generativo
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


### Esempio (Book) - Id nostro
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
 - **`@ManyToOne(cascade = CascadeType.ALL)`**:Specifica la relazione con l' altra tabella. `cascade = CascadeType.ALL` indica che quando ritorniamo un libro, ritorniamo anche l' autore e se dovessimo modificare anche l'autore del libro, i dati verrebbero salvati anche nel DB
 - **`@JoinColumn(name = "author_id")`** Per dire a quale campo della tabella del DB ci si riferisce


# Hibernate Auto DDL
[Guida](https://docs.spring.io/spring-boot/docs/2.0.0.M6/reference/html/howto-database-initialization.html)
Setta il DB schema in modo automatico, basta aggiungere `spring.jpa.hibernate.ddl-auto=update` in **resources/application.properties**