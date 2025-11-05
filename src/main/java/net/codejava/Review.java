package net.codejava;

import javax.persistence.*;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "comment", length = 500)
    private String comment;
    @Column(name = "reviewer_name")
    private String reviewerName;

    // Plusieurs avis peuvent appartenir à un seul livre
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public Review() {
    }

    public Review(String comment, String reviewerName) {
        this.comment = comment;
        this.reviewerName = reviewerName;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", reviewerName='" + reviewerName + '\'' +
                '}';
    }
}