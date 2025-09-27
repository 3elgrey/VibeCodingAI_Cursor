package com.library.service;

import com.library.entity.BookIssue;
import com.library.entity.BookReturn;
import com.library.repository.BookReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookReturnService {
    
    @Autowired
    private BookReturnRepository bookReturnRepository;
    
    @Autowired
    private BookIssueService bookIssueService;
    
    @Autowired
    private BookService bookService;
    
    private static final double FINE_PER_DAY = 5.0; // Fine amount per day
    private static final double MAX_FINE = 500.0; // Maximum fine amount
    
    public List<BookReturn> getAllBookReturns() {
        return bookReturnRepository.findAll();
    }
    
    public Optional<BookReturn> getBookReturnById(Long id) {
        return bookReturnRepository.findById(id);
    }
    
    public List<BookReturn> getBookReturnsByStudent(Long studentId) {
        return bookReturnRepository.findByStudentId(studentId);
    }
    
    public List<BookReturn> getBookReturnsByBook(Long bookId) {
        return bookReturnRepository.findByBookId(bookId);
    }
    
    public List<BookReturn> getBookReturnsByDateRange(LocalDate startDate, LocalDate endDate) {
        return bookReturnRepository.findByReturnDateRange(startDate, endDate);
    }
    
    public List<BookReturn> getReturnsWithFines() {
        return bookReturnRepository.findReturnsWithFines();
    }
    
    public List<BookReturn> getUnpaidFines() {
        return bookReturnRepository.findUnpaidFines();
    }
    
    public List<BookReturn> getPaidFines() {
        return bookReturnRepository.findPaidFines();
    }
    
    public BookReturn returnBook(Long issueId, BookReturn.BookCondition condition, String remarks) {
        // Get the book issue
        BookIssue bookIssue = bookIssueService.getBookIssueById(issueId)
                .orElseThrow(() -> new RuntimeException("Book issue not found"));
        
        if (bookIssue.getStatus() != BookIssue.IssueStatus.ISSUED && 
            bookIssue.getStatus() != BookIssue.IssueStatus.OVERDUE) {
            throw new RuntimeException("Book is not currently issued");
        }
        
        LocalDate returnDate = LocalDate.now();
        
        // Calculate fine if overdue
        double fineAmount = calculateFine(bookIssue, returnDate);
        
        // Create book return record
        BookReturn bookReturn = new BookReturn(bookIssue, returnDate, condition);
        bookReturn.setFineAmount(fineAmount);
        bookReturn.setRemarks(remarks);
        bookReturn = bookReturnRepository.save(bookReturn);
        
        // Update book issue status
        bookIssue.setStatus(BookIssue.IssueStatus.RETURNED);
        bookIssue.setReturnDate(returnDate);
        bookIssue.setFineAmount(fineAmount);
        bookIssueService.updateBookIssue(bookIssue);
        
        // Increase available copies
        bookService.increaseAvailableCopies(bookIssue.getBook().getId());
        
        return bookReturn;
    }
    
    public BookReturn returnBookByIssueId(Long issueId, BookReturn.BookCondition condition) {
        return returnBook(issueId, condition, null);
    }
    
    public void payFine(Long returnId) {
        Optional<BookReturn> bookReturnOpt = bookReturnRepository.findById(returnId);
        if (bookReturnOpt.isPresent()) {
            BookReturn bookReturn = bookReturnOpt.get();
            bookReturn.setFinePaid(true);
            bookReturn.setFinePaidDate(LocalDate.now());
            bookReturnRepository.save(bookReturn);
        }
    }
    
    public double calculateFine(BookIssue bookIssue, LocalDate returnDate) {
        if (returnDate.isAfter(bookIssue.getDueDate())) {
            long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(bookIssue.getDueDate(), returnDate);
            double fine = daysOverdue * FINE_PER_DAY;
            return Math.min(fine, MAX_FINE);
        }
        return 0.0;
    }
    
    public double calculateFineForIssue(Long issueId) {
        BookIssue bookIssue = bookIssueService.getBookIssueById(issueId)
                .orElseThrow(() -> new RuntimeException("Book issue not found"));
        
        return calculateFine(bookIssue, LocalDate.now());
    }
    
    public Double getTotalFineCollected() {
        return bookReturnRepository.getTotalFineCollected();
    }
    
    public Double getTotalUnpaidFines() {
        return bookReturnRepository.getTotalUnpaidFines();
    }
    
    public Long getReturnsWithFinesCount() {
        return bookReturnRepository.countReturnsWithFines();
    }
    
    public Long getUnpaidFinesCount() {
        return bookReturnRepository.countUnpaidFines();
    }
    
    public List<BookReturn> getBookReturnsByCondition(BookReturn.BookCondition condition) {
        return bookReturnRepository.findByReturnCondition(condition);
    }
    
    public List<BookReturn> getBookReturnsByFinePaid(Boolean finePaid) {
        return bookReturnRepository.findByFinePaid(finePaid);
    }
}
