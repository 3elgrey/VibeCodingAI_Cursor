package com.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "ISBN is required")
    @Column(unique = true, nullable = false)
    private String isbn;
    
    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Author is required")
    @Column(nullable = false)
    private String author;
    
    @NotBlank(message = "Publisher is required")
    private String publisher;
    
    @NotNull(message = "Publication date is required")
    @Column(name = "publication_date")
    private LocalDate publicationDate;
    
    @NotBlank(message = "Category is required")
    private String category;
    
    @NotNull(message = "Total copies is required")
    @Positive(message = "Total copies must be positive")
    @Column(name = "total_copies")
    private Integer totalCopies;
    
    @NotNull(message = "Available copies is required")
    @Column(name = "available_copies")
    private Integer availableCopies;
    
    @Column(name = "created_date")
    private LocalDate createdDate;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookIssue> bookIssues;
    
    // Constructors
    public Book() {
        this.createdDate = LocalDate.now();
    }
    
    public Book(String isbn, String title, String author, String publisher, 
                LocalDate publicationDate, String category, Integer totalCopies) {
        this();
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getPublisher() {
        return publisher;
    }
    
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    public LocalDate getPublicationDate() {
        return publicationDate;
    }
    
    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public Integer getTotalCopies() {
        return totalCopies;
    }
    
    public void setTotalCopies(Integer totalCopies) {
        this.totalCopies = totalCopies;
    }
    
    public Integer getAvailableCopies() {
        return availableCopies;
    }
    
    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }
    
    public LocalDate getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
    
    public List<BookIssue> getBookIssues() {
        return bookIssues;
    }
    
    public void setBookIssues(List<BookIssue> bookIssues) {
        this.bookIssues = bookIssues;
    }
    
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publicationDate=" + publicationDate +
                ", category='" + category + '\'' +
                ", totalCopies=" + totalCopies +
                ", availableCopies=" + availableCopies +
                ", createdDate=" + createdDate +
                '}';
    }
}
