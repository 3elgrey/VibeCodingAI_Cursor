package com.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_returns")
public class BookReturn {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_issue_id", nullable = false)
    @NotNull(message = "Book issue is required")
    private BookIssue bookIssue;
    
    @NotNull(message = "Return date is required")
    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;
    
    @Column(name = "fine_amount")
    private Double fineAmount = 0.0;
    
    @Column(name = "fine_paid")
    private Boolean finePaid = false;
    
    @Column(name = "fine_paid_date")
    private LocalDate finePaidDate;
    
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "return_condition", nullable = false)
    private BookCondition returnCondition = BookCondition.GOOD;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    public enum BookCondition {
        GOOD, DAMAGED, LOST, VERY_DAMAGED
    }
    
    // Constructors
    public BookReturn() {
        this.createdAt = LocalDateTime.now();
    }
    
    public BookReturn(BookIssue bookIssue, LocalDate returnDate, BookCondition returnCondition) {
        this();
        this.bookIssue = bookIssue;
        this.returnDate = returnDate;
        this.returnCondition = returnCondition;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public BookIssue getBookIssue() {
        return bookIssue;
    }
    
    public void setBookIssue(BookIssue bookIssue) {
        this.bookIssue = bookIssue;
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
    
    public Boolean getFinePaid() {
        return finePaid;
    }
    
    public void setFinePaid(Boolean finePaid) {
        this.finePaid = finePaid;
    }
    
    public LocalDate getFinePaidDate() {
        return finePaidDate;
    }
    
    public void setFinePaidDate(LocalDate finePaidDate) {
        this.finePaidDate = finePaidDate;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public BookCondition getReturnCondition() {
        return returnCondition;
    }
    
    public void setReturnCondition(BookCondition returnCondition) {
        this.returnCondition = returnCondition;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public boolean isOverdue() {
        return returnDate.isAfter(bookIssue.getDueDate());
    }
    
    public long getDaysOverdue() {
        if (isOverdue()) {
            return java.time.temporal.ChronoUnit.DAYS.between(bookIssue.getDueDate(), returnDate);
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return "BookReturn{" +
                "id=" + id +
                ", bookIssue=" + (bookIssue != null ? bookIssue.getId() : "null") +
                ", returnDate=" + returnDate +
                ", fineAmount=" + fineAmount +
                ", finePaid=" + finePaid +
                ", finePaidDate=" + finePaidDate +
                ", remarks='" + remarks + '\'' +
                ", returnCondition=" + returnCondition +
                ", createdAt=" + createdAt +
                '}';
    }
}
