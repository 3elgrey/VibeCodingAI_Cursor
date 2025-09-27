package com.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing books that are out of stock
 */
@Entity
@Table(name = "out_of_stock_books")
public class OutOfStockBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "ISBN is required")
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @NotBlank(message = "Title is required")
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank(message = "Author is required")
    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "category")
    private String category;

    @NotNull(message = "Last available quantity is required")
    @Column(name = "last_available_quantity", nullable = false)
    private Integer lastAvailableQuantity;

    @Column(name = "out_of_stock_date", nullable = false)
    private LocalDate outOfStockDate;

    @Column(name = "restock_priority")
    @Enumerated(EnumType.STRING)
    private RestockPriority restockPriority = RestockPriority.MEDIUM;

    @Column(name = "expected_restock_date")
    private LocalDate expectedRestockDate;

    @Column(name = "notes", length = 500)
    private String notes;

    @Column(name = "is_restocked", nullable = false)
    private Boolean isRestocked = false;

    @Column(name = "restock_date")
    private LocalDate restockDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors
    public OutOfStockBook() {
        this.createdAt = LocalDateTime.now();
        this.outOfStockDate = LocalDate.now();
    }

    public OutOfStockBook(String isbn, String title, String author, Integer lastAvailableQuantity) {
        this();
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.lastAvailableQuantity = lastAvailableQuantity;
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

    public Integer getLastAvailableQuantity() {
        return lastAvailableQuantity;
    }

    public void setLastAvailableQuantity(Integer lastAvailableQuantity) {
        this.lastAvailableQuantity = lastAvailableQuantity;
    }

    public LocalDate getOutOfStockDate() {
        return outOfStockDate;
    }

    public void setOutOfStockDate(LocalDate outOfStockDate) {
        this.outOfStockDate = outOfStockDate;
    }

    public RestockPriority getRestockPriority() {
        return restockPriority;
    }

    public void setRestockPriority(RestockPriority restockPriority) {
        this.restockPriority = restockPriority;
    }

    public LocalDate getExpectedRestockDate() {
        return expectedRestockDate;
    }

    public void setExpectedRestockDate(LocalDate expectedRestockDate) {
        this.expectedRestockDate = expectedRestockDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Boolean getIsRestocked() {
        return isRestocked;
    }

    public void setIsRestocked(Boolean isRestocked) {
        this.isRestocked = isRestocked;
    }

    public LocalDate getRestockDate() {
        return restockDate;
    }

    public void setRestockDate(LocalDate restockDate) {
        this.restockDate = restockDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Enum for restock priority
    public enum RestockPriority {
        LOW, MEDIUM, HIGH, URGENT
    }
}
