package com.library.service;

import com.library.entity.Book;
import com.library.entity.OutOfStockBook;
import com.library.repository.BookRepository;
import com.library.repository.OutOfStockBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing out-of-stock books
 */
@Service
@Transactional
public class OutOfStockBookService {

    @Autowired
    private OutOfStockBookRepository outOfStockBookRepository;

    @Autowired
    private BookRepository bookRepository;

    /**
     * Add a book to out-of-stock list
     */
    public OutOfStockBook addToOutOfStock(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        // Check if book is already in out-of-stock list
        if (outOfStockBookRepository.existsByIsbnAndIsRestockedFalse(book.getIsbn())) {
            throw new RuntimeException("Book is already in out-of-stock list");
        }

        OutOfStockBook outOfStockBook = new OutOfStockBook();
        outOfStockBook.setIsbn(book.getIsbn());
        outOfStockBook.setTitle(book.getTitle());
        outOfStockBook.setAuthor(book.getAuthor());
        outOfStockBook.setPublisher(book.getPublisher());
        outOfStockBook.setPublicationDate(book.getPublicationDate());
        outOfStockBook.setCategory(book.getCategory());
        outOfStockBook.setLastAvailableQuantity(book.getAvailableCopies());
        outOfStockBook.setOutOfStockDate(LocalDate.now());

        return outOfStockBookRepository.save(outOfStockBook);
    }

    /**
     * Add a book to out-of-stock list by ISBN
     */
    public OutOfStockBook addToOutOfStockByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found with ISBN: " + isbn));

        return addToOutOfStock(book.getId());
    }

    /**
     * Get all out-of-stock books
     */
    @Transactional(readOnly = true)
    public List<OutOfStockBook> getAllOutOfStockBooks() {
        return outOfStockBookRepository.findByIsRestockedFalseOrderByOutOfStockDateDesc();
    }

    /**
     * Get all restocked books
     */
    @Transactional(readOnly = true)
    public List<OutOfStockBook> getAllRestockedBooks() {
        return outOfStockBookRepository.findByIsRestockedTrueOrderByRestockDateDesc();
    }

    /**
     * Get out-of-stock book by ID
     */
    @Transactional(readOnly = true)
    public Optional<OutOfStockBook> getOutOfStockBookById(Long id) {
        return outOfStockBookRepository.findById(id);
    }

    /**
     * Get out-of-stock book by ISBN
     */
    @Transactional(readOnly = true)
    public Optional<OutOfStockBook> getOutOfStockBookByIsbn(String isbn) {
        return Optional.ofNullable(outOfStockBookRepository.findByIsbn(isbn));
    }

    /**
     * Update out-of-stock book
     */
    public OutOfStockBook updateOutOfStockBook(Long id, OutOfStockBook updatedBook) {
        OutOfStockBook existingBook = outOfStockBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Out-of-stock book not found with id: " + id));

        existingBook.setRestockPriority(updatedBook.getRestockPriority());
        existingBook.setExpectedRestockDate(updatedBook.getExpectedRestockDate());
        existingBook.setNotes(updatedBook.getNotes());

        return outOfStockBookRepository.save(existingBook);
    }

    /**
     * Mark book as restocked
     */
    public OutOfStockBook markAsRestocked(Long id, Integer restockQuantity) {
        OutOfStockBook outOfStockBook = outOfStockBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Out-of-stock book not found with id: " + id));

        if (outOfStockBook.getIsRestocked()) {
            throw new RuntimeException("Book is already marked as restocked");
        }

        outOfStockBook.setIsRestocked(true);
        outOfStockBook.setRestockDate(LocalDate.now());

        // Update the original book's available copies
        Book book = bookRepository.findByIsbn(outOfStockBook.getIsbn())
                .orElseThrow(() -> new RuntimeException("Original book not found"));
        
        book.setAvailableCopies(book.getAvailableCopies() + restockQuantity);
        bookRepository.save(book);

        return outOfStockBookRepository.save(outOfStockBook);
    }

    /**
     * Get out-of-stock books by priority
     */
    @Transactional(readOnly = true)
    public List<OutOfStockBook> getOutOfStockBooksByPriority(OutOfStockBook.RestockPriority priority) {
        return outOfStockBookRepository.findByRestockPriorityAndIsRestockedFalseOrderByOutOfStockDateDesc(priority);
    }

    /**
     * Get out-of-stock books by category
     */
    @Transactional(readOnly = true)
    public List<OutOfStockBook> getOutOfStockBooksByCategory(String category) {
        return outOfStockBookRepository.findByCategoryAndIsRestockedFalseOrderByOutOfStockDateDesc(category);
    }

    /**
     * Get overdue books for restock
     */
    @Transactional(readOnly = true)
    public List<OutOfStockBook> getOverdueForRestock() {
        return outOfStockBookRepository.findOverdueForRestock(LocalDate.now());
    }

    /**
     * Get out-of-stock books by date range
     */
    @Transactional(readOnly = true)
    public List<OutOfStockBook> getOutOfStockBooksByDateRange(LocalDate startDate, LocalDate endDate) {
        return outOfStockBookRepository.findByOutOfStockDateBetween(startDate, endDate);
    }

    /**
     * Search out-of-stock books by title
     */
    @Transactional(readOnly = true)
    public List<OutOfStockBook> searchByTitle(String title) {
        return outOfStockBookRepository.findByTitleContainingIgnoreCaseAndIsRestockedFalseOrderByOutOfStockDateDesc(title);
    }

    /**
     * Search out-of-stock books by author
     */
    @Transactional(readOnly = true)
    public List<OutOfStockBook> searchByAuthor(String author) {
        return outOfStockBookRepository.findByAuthorContainingIgnoreCaseAndIsRestockedFalseOrderByOutOfStockDateDesc(author);
    }

    /**
     * Get count by restock priority
     */
    @Transactional(readOnly = true)
    public List<Object[]> getCountByRestockPriority() {
        return outOfStockBookRepository.countByRestockPriority();
    }

    /**
     * Delete out-of-stock book record
     */
    public void deleteOutOfStockBook(Long id) {
        if (!outOfStockBookRepository.existsById(id)) {
            throw new RuntimeException("Out-of-stock book not found with id: " + id);
        }
        outOfStockBookRepository.deleteById(id);
    }

    /**
     * Get total count of out-of-stock books
     */
    @Transactional(readOnly = true)
    public long getTotalOutOfStockCount() {
        return outOfStockBookRepository.count();
    }

    /**
     * Get count of pending restock books
     */
    @Transactional(readOnly = true)
    public long getPendingRestockCount() {
        return outOfStockBookRepository.findByIsRestockedFalseOrderByOutOfStockDateDesc().size();
    }
}
