package net.codejava;

import javax.persistence.*;
import java.util.List;

public class BooksManager {

    static EntityManagerFactory factory;
    static EntityManager entityManager;

    public static void main(String[] args) {
        begin();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BooksPU");
        EntityManager em = emf.createEntityManager();

        Author a = new Author("Victor Hugo");
        Publisher p = new Publisher("Gallimard", "France");

        em.getTransaction().begin();
        em.persist(a);
        em.persist(p);
        em.getTransaction().commit();

        em.close();
        emf.close();
        //create();
        //update();
        //read();
        //remove();
       // query();


        end();
    }

    private static void begin() {
        factory = Persistence.createEntityManagerFactory("BooksPU");
        entityManager = factory.createEntityManager();
    }

    private static void create() {
        entityManager.getTransaction().begin();

        Book newBook = new Book();
        newBook.setTitle("Thinking in Java");
        newBook.setAuthor("Bruce Eckel");
        newBook.setPrice(15.0);

        entityManager.persist(newBook);

        entityManager.getTransaction().commit();
        System.out.println("Livre cree!");
    }

    private static void update() {
        entityManager.getTransaction().begin();

        Book existBook = entityManager.find(Book.class, 1L);
        existBook.setTitle("Thinking in Java 2");
        existBook.setAuthor("Bruce Eckel");
        existBook.setPrice(20.0);
        entityManager.merge(existBook);

        entityManager.getTransaction().commit();
        System.out.println("Livre mis a jour!");
    }

    private static void read() {
        Long primaryKey = 1L;
        Book foundBook = entityManager.find(Book.class, primaryKey);

        System.out.println("ID: " + foundBook.getId());
        System.out.println("Title: " + foundBook.getTitle());
        System.out.println("Author: " + foundBook.getAuthor());
        System.out.println("Price: " + foundBook.getPrice());
    }

    private static void remove() {
        entityManager.getTransaction().begin();

        Long primaryKey = 1L;
        Book reference = entityManager.getReference(Book.class, primaryKey);
        entityManager.remove(reference);

        entityManager.getTransaction().commit();
        System.out.println("Livre supprime!");
    }

    private static void query() {
        // Requête 1a: Afficher tous les livres
        String jpql = "SELECT b FROM Book b WHERE b.price > :minPrice";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("minPrice", 0.0);

        List<Book> listBooks = query.getResultList();

        System.out.println("\n=== Tous les livres ===");
        for (Book book : listBooks) {
            System.out.println("ID: " + book.getId() + " - " +
                    book.getTitle() + " - " +
                    book.getAuthor() + " - " +
                    book.getPrice());
        }

        // Requête 1b: Livres avec prix > 50
        String jpql2 = "SELECT b FROM Book b WHERE b.price > :priceLimit";
        Query query2 = entityManager.createQuery(jpql2);
        query2.setParameter("priceLimit", 50.0);

        List<Book> expensiveBooks = query2.getResultList();

        System.out.println("\n=== Livres qui depassent 50 euros ===");
        for (Book book : expensiveBooks) {
            System.out.println("ID: " + book.getId() + " - " +
                    book.getTitle() + " - " +
                    book.getAuthor() + " - " +
                    book.getPrice());
        }

        // Requête 1b avec augmentation de 5%
        System.out.println("\n=== Livres avec augmentation de 5% ===");
        for (Book book : expensiveBooks) {
            double newPrice = book.getPrice() * 1.05;
            System.out.println("ID: " + book.getId() + " - " +
                    book.getTitle() + " - " +
                    "Ancien prix: " + book.getPrice() +
                    " - Nouveau prix: " + newPrice);
        }
    }

    private static void end() {
        entityManager.close();
        factory.close();
    }
}