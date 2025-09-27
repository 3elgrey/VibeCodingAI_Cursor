package com.library.service;

import com.library.entity.Book;
import com.library.entity.BookIssue;
import com.library.entity.Student;
import com.library.repository.BookIssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookIssueService {
    
    @Autowired
    private BookIssueRepository bookIssueRepository;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private StudentService studentService;
    
    public List<BookIssue> getAllBookIssues() {
        return bookIssueRepository.findAll();
    }
    
    public Optional<BookIssue> getBookIssueById(Long id) {
        return bookIssueRepository.findById(id);
    }
    
    public List<BookIssue> getBookIssuesByStudent(Long studentId) {
        return bookIssueRepository.findByStudentIdAndStatus(studentId, BookIssue.IssueStatus.ISSUED);
    }
    
    public List<BookIssue> getBookIssuesByBook(Long bookId) {
        return bookIssueRepository.findByBookIdAndStatus(bookId, BookIssue.IssueStatus.ISSUED);
    }
    
    public List<BookIssue> getBookIssuesByStatus(BookIssue.IssueStatus status) {
        return bookIssueRepository.findByStatus(status);
    }
    
    public List<BookIssue> getActiveBookIssues() {
        return bookIssueRepository.findByStatus(BookIssue.IssueStatus.ISSUED);
    }
    
    public List<BookIssue> getOverdueBooks() {
        return bookIssueRepository.findOverdueBooks(LocalDate.now());
    }
    
    public BookIssue issueBook(Long bookId, Long studentId, int daysToReturn) {
        // Check if book is available
        if (!bookService.isBookAvailable(bookId)) {
            throw new RuntimeException("Book is not available for issue");
        }
        
        // Check if student is active
        if (!studentService.isStudentActive(studentId)) {
            throw new RuntimeException("Student is not active");
        }
        
        // Check if student already has this book issued
        Optional<BookIssue> existingIssue = bookIssueRepository.findActiveIssueByStudentAndBook(studentId, bookId);
        if (existingIssue.isPresent()) {
            throw new RuntimeException("Student already has this book issued");
        }
        
        // Get book and student
        Book book = bookService.getBookById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        
        Student student = studentService.getStudentById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        // Create book issue
        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = issueDate.plusDays(daysToReturn);
        
        BookIssue bookIssue = new BookIssue(book, student, issueDate, dueDate);
        bookIssue = bookIssueRepository.save(bookIssue);
        
        // Decrease available copies
        bookService.decreaseAvailableCopies(bookId);
        
        return bookIssue;
    }
    
    public BookIssue issueBookByStudentId(Long bookId, String studentId, int daysToReturn) {
        Student student = studentService.getStudentByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
        
        return issueBook(bookId, student.getId(), daysToReturn);
    }
    
    public void markAsOverdue(Long issueId) {
        Optional<BookIssue> bookIssueOpt = bookIssueRepository.findById(issueId);
        if (bookIssueOpt.isPresent()) {
            BookIssue bookIssue = bookIssueOpt.get();
            if (bookIssue.getStatus() == BookIssue.IssueStatus.ISSUED && 
                LocalDate.now().isAfter(bookIssue.getDueDate())) {
                bookIssue.setStatus(BookIssue.IssueStatus.OVERDUE);
                bookIssueRepository.save(bookIssue);
            }
        }
    }
    
    public void markAsLost(Long issueId) {
        Optional<BookIssue> bookIssueOpt = bookIssueRepository.findById(issueId);
        if (bookIssueOpt.isPresent()) {
            BookIssue bookIssue = bookIssueOpt.get();
            bookIssue.setStatus(BookIssue.IssueStatus.LOST);
            bookIssueRepository.save(bookIssue);
        }
    }
    
    public Long getIssuedBooksCount() {
        return bookIssueRepository.countIssuedBooks();
    }
    
    public Long getReturnedBooksCount() {
        return bookIssueRepository.countReturnedBooks();
    }
    
    public Long getOverdueBooksCount() {
        return bookIssueRepository.countOverdueBooks(LocalDate.now());
    }
    
    public List<BookIssue> getBookIssuesByDateRange(LocalDate startDate, LocalDate endDate) {
        return bookIssueRepository.findByIssueDateBetween(startDate, endDate);
    }
    
    public List<BookIssue> getBookIssuesDueInRange(LocalDate startDate, LocalDate endDate) {
        return bookIssueRepository.findByDueDateBetween(startDate, endDate);
    }
    
    public List<BookIssue> getActiveIssuesByStudentId(String studentId) {
        return bookIssueRepository.findActiveIssuesByStudentId(studentId);
    }
    
    public BookIssue updateBookIssue(BookIssue bookIssue) {
        return bookIssueRepository.save(bookIssue);
    }
    
    public double calculateFineForIssue(Long issueId) {
        Optional<BookIssue> bookIssueOpt = bookIssueRepository.findById(issueId);
        if (bookIssueOpt.isPresent()) {
            BookIssue bookIssue = bookIssueOpt.get();
            if (bookIssue.getStatus() == BookIssue.IssueStatus.ISSUED || 
                bookIssue.getStatus() == BookIssue.IssueStatus.OVERDUE) {
                LocalDate today = LocalDate.now();
                if (today.isAfter(bookIssue.getDueDate())) {
                    long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(bookIssue.getDueDate(), today);
                    double fine = daysOverdue * 5.0; // ₹5 per day
                    return Math.min(fine, 500.0); // Maximum fine ₹500
                }
            }
        }
        return 0.0;
    }
}
