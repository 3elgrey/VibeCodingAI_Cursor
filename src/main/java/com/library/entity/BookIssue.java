package com.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_issues")
public class BookIssue {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    @NotNull(message = "Book is required")
    private Book book;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @NotNull(message = "Student is required")
    private Student student;
    
    @NotNull(message = "Issue date is required")
    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;
    
    @NotNull(message = "Due date is required")
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
    
    @Column(name = "return_date")
    private LocalDate returnDate;
    
    @Column(name = "fine_amount")
    private Double fineAmount = 0.0;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private IssueStatus status = IssueStatus.ISSUED;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToOne(mappedBy = "bookIssue", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BookReturn bookReturn;
    
    public enum IssueStatus {
        ISSUED, RETURNED, OVERDUE, LOST
    }
    
    // Constructors
    public BookIssue() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public BookIssue(Book book, Student student, LocalDate issueDate, LocalDate dueDate) {
        this();
        this.book = book;
        this.student = student;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Book getBook() {
        return book;
    }
    
    public void setBook(Book book) {
        this.book = book;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public LocalDate getIssueDate() {
        return issueDate;
    }
    
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public LocalDate getReturnDate() {
        return returnDate;
    }
    
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    
    public Double getFineAmount() {
        return fineAmount;
    }
    
    public void setFineAmount(Double fineAmount) {
        this.fineAmount = fineAmount;
    }
    
    public IssueStatus getStatus() {
        return status;
    }
    
    public void setStatus(IssueStatus status) {
        this.status = status;
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
    
    public BookReturn getBookReturn() {
        return bookReturn;
    }
    
    public void setBookReturn(BookReturn bookReturn) {
        this.bookReturn = bookReturn;
    }
    
    public boolean isOverdue() {
        return LocalDate.now().isAfter(dueDate) && status == IssueStatus.ISSUED;
    }
    
    public long getDaysOverdue() {
        if (isOverdue()) {
            return java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return "BookIssue{" +
                "id=" + id +
                ", book=" + (book != null ? book.getTitle() : "null") +
                ", student=" + (student != null ? student.getFullName() : "null") +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", fineAmount=" + fineAmount +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
