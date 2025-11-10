package net.codejava;

import javax.persistence.*;
import java.util.List;

public class BooksManager1 {

    static EntityManagerFactory factory;
    static EntityManager entityManager;

    public static void main(String[] args) {

        // PARTIE 1 : Insertion et manipulation
//        System.out.println(" PARTIE 1");
       // partie1InsertionEtManipulation();
        System.out.println("\n\n");
//
//        // PARTIE 2 : Requêtes et manipulations
        System.out.println("PARTIE 2");
         // Question 1
//        findBooksByCategory("Science Fiction");
//        System.out.println();
//        // Question 2
//       findBooksByPublisher("Eyrolles");
//        System.out.println();
//         //Question 3
//        deleteBook(4L);
//        // Question 4
//        updateBookPrice(1L, 49.99);
//        System.out.println();
//       // Question 5
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BooksManager");
//        testCategories(emf);

//        emf.close();
    }



    public static void partie1InsertionEtManipulation() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BooksManager");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();


            System.out.println("═══ : Créer un éditeur, des catégories et un livre ═══");

            // Créer un éditeur
            Publisher publisher = new Publisher("Eyrolles", "Maroc");
            System.out.println("✓ Éditeur créé: " + publisher.getName());

            // Créer des catégories
            Category sciFi = new Category("Science Fiction");
            Category programming = new Category("Programmation");
            Category database = new Category("Base de données");
            System.out.println("✓ Catégories créées: Science Fiction, Programmation, Base de données");

            // Créer un livre
            Book book1 = new Book("JPA et Hibernate", "Emmanuel Bernard", 45.00);
            System.out.println("✓ Livre créé: " + book1.getTitle());
            System.out.println();

            // q2
            System.out.println("═══  Lier le livre à son éditeur et à plusieurs catégories ═══");

            // Lier le livre à son éditeur
            book1.setPublisher(publisher);
            System.out.println("✓ Livre lié à l'éditeur: " + publisher.getName());

            // Lier le livre à plusieurs catégories
            book1.addCategory(programming);
            book1.addCategory(database);
            System.out.println("✓ Livre lié aux catégories: Programmation, Base de données");
            System.out.println();

            // q3
            System.out.println("═══  Ajouter plusieurs avis (Review) pour ce livre ═══");

            // Review sans rating (constructeur modifié)
            Review review1 = new Review("Excellent livre pour apprendre JPA!", "Marie Dupont");
            Review review2 = new Review("Très complet et bien expliqué",  "Jean Martin");
            Review review3 = new Review("Parfait pour les débutants",  "Sophie Laurent");

            book1.addReview(review1);
            book1.addReview(review2);
            book1.addReview(review3);

            System.out.println("✓ Avis 1 ajouté: " + review1.getReviewerName());
            System.out.println("✓ Avis 2 ajouté: " + review2.getReviewerName());
            System.out.println("✓ Avis 3 ajouté: " + review3.getReviewerName());
            System.out.println();

            // q4
            System.out.println("═══  : Persister ces objets dans la base via EntityManager ═══");

            // Persister l'éditeur
            em.persist(publisher);
            System.out.println("✓ Éditeur persisté");

            // Persister les catégories
            em.persist(sciFi);
            em.persist(programming);
            em.persist(database);
            System.out.println("✓ Catégories persistées");

            // Persister le livre (qui contient déjà les relations)
            em.persist(book1);
            System.out.println("✓ Livre persisté avec ses relations");

            // Persister les avis
            em.persist(review1);
            em.persist(review2);
            em.persist(review3);
            System.out.println("✓ Avis persistés");

            // Créer d'autres livres pour tester la Partie 2
            Book book2 = new Book("Design Patterns", "Gang of Four", 55.00);
            book2.setPublisher(publisher);
            book2.addCategory(programming);
            em.persist(book2);

            Book book3 = new Book("Dune", "Frank Herbert", 25.00);
            book3.setPublisher(publisher);
            book3.addCategory(sciFi);
            em.persist(book3);

            System.out.println("✓ Autres livres persistés pour les tests");

            // Commit de la transaction
            transaction.commit();
            System.out.println("✓ Transaction committée - Toutes les données sont en base!");
            System.out.println();

            // q5
            System.out.println("═══  : Lire un livre et afficher ses informations complètes ═══");
            lireEtAfficherLivre(em, book1.getId());

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
                System.out.println(" Transaction annulée suite à une erreur");
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
    //q5
    public static void lireEtAfficherLivre(EntityManager em, Long bookId) {
        Book book = em.find(Book.class, bookId);
        if (book != null) {
            System.out.println("\nINFORMATIONS DU LIVRE");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("ID      : " + book.getId());
            System.out.println("Titre   : " + book.getTitle());
            System.out.println("Auteur  : " + book.getAuthor());
            System.out.println("Prix    : " + book.getPrice() + "$");
            System.out.println("\nÉDITEUR");// Afficher l'éditeur (country au lieu de address)
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            if (book.getPublisher() != null) {
                System.out.println("Nom     : " + book.getPublisher().getName());
                System.out.println("Pays    : " + book.getPublisher().getAddress()); // getAddress() retourne country
            } else {
                System.out.println("Aucun éditeur");
            }
            System.out.println("\nCATÉGORIES");// Afficher les catégories (sans description)
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            if (book.getCategories().isEmpty()) {
                System.out.println("Aucune catégorie");
            } else {
                for (Category category : book.getCategories()) {
                    System.out.println("• " + category.getName());
                }
            }
            System.out.println("\n AVIS (" + book.getReviews().size() + " avis au total)");// Afficher les avis
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            if (book.getReviews().isEmpty()) {
                System.out.println("Aucun avis");
            } else {
                for (Review review : book.getReviews()) {
                    System.out.println("• " + review.getReviewerName());
                    System.out.println("  \"" + review.getComment() + "\"");
                    System.out.println();
                }
            }

            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        } else {
            System.out.println(" Livre non trouvé avec l'ID: " + bookId);
        }
    }

    // part 2 q1
    public static void findBooksByCategory(String categoryName) {
        System.out.println("═══  Récupérer tous les livres appartenant à une catégorie donnée ═══");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BooksManager");
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                    "SELECT b FROM Book b JOIN b.categories c WHERE c.name = :categoryName",
                    Book.class
            );
            query.setParameter("categoryName", categoryName);
            List<Book> books = query.getResultList();
            System.out.println("Livres dans la catégorie '" + categoryName + "' (" + books.size() + " résultat(s)):");
            if (books.isEmpty()) {
                System.out.println("   Aucun livre trouvé");
            } else {
                for (Book book : books) {
                    System.out.println("   • " + book.getTitle() +
                            " par " + book.getAuthor() +
                            " (Prix: " + book.getPrice() + "$)");
                }
            }
        } finally {
            em.close();
            emf.close();
        }
    }


    // q2
    public static void findBooksByPublisher(String publisherName) {
        System.out.println("═══ Trouver tous les livres publiés par un éditeur spécifique ═══");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BooksManager");
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                    "SELECT b FROM Book b WHERE b.publisher.name = :publisherName",
                    Book.class
            );
            query.setParameter("publisherName", publisherName);

            List<Book> books = query.getResultList();

            System.out.println("Livres publiés par '" + publisherName + "' (" + books.size() + " résultat(s)):");
            if (books.isEmpty()) {
                System.out.println("   Aucun livre trouvé");
            } else {
                for (Book book : books) {
                    System.out.println("   • " + book.getTitle() +
                            " par " + book.getAuthor() +
                            " (Prix: " + book.getPrice() + "$)");
                }
            }
        } finally {
            em.close();
            emf.close();
        }
    }

    // q3
    public static void deleteBook(Long bookId) {
        System.out.println(" Supprimer un livre et observer le comportement (CascadeType) ═══");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BooksManager");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Book book = em.find(Book.class, bookId);

            if (book != null) {
                System.out.println("   Suppression du livre: " + book.getTitle());
                em.remove(book);
                System.out.println("  Livre supprimé avec succès!");
            } else {
                System.out.println(" Livre non trouvé avec l'ID: " + bookId);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erreur lors de la suppression: " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }
    }

    // q4
    public static void updateBookPrice(Long bookId, double newPrice) {
        System.out.println("═══  Mettre à jour le prix d'un livre ═══");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BooksManager");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Book book = em.find(Book.class, bookId);

            if (book != null) {
                double oldPrice = book.getPrice();
                book.setPrice(newPrice);
                em.merge(book);

                System.out.println(" Prix mis à jour pour '" + book.getTitle() + "'");
                System.out.println("   Ancien prix: " + oldPrice + "$");
                System.out.println("   Nouveau prix: " + newPrice + "$");
            } else {
                System.out.println("Livre non trouvé avec l'ID: " + bookId);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Erreur lors de la mise à jour: " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }
    }

    // q5
    public static void testCategories(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println(" Récupération du livre (id=2)");
            Book book = em.find(Book.class, 2L);
            System.out.println("   Livre trouvé : " + book.getTitle());
            System.out.println("Fermeture de l'EntityManager");
            em.close();
            System.out.println("EntityManager fermé\n");

            System.out.println("Tentative d'accès aux catégories");
            try {
                List<Category> categories = book.getCategories();
                System.out.println("   Nombre de catégories : " + categories.size());

                if (!categories.isEmpty()) {
                    System.out.println("Catégories :");
                    for (Category cat : categories) {
                        System.out.println("- " + cat.getName());
                    }
                }
            } catch (Exception e) {
                System.out.println("   Erreur : " + e.getClass().getSimpleName());
                System.out.println("   Message : " + e.getMessage());
            }
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
    }