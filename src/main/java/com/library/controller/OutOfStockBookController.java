package com.library.controller;

import com.library.entity.OutOfStockBook;
import com.library.service.OutOfStockBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller for managing out-of-stock books
 */
@RestController
@RequestMapping("/api/out-of-stock")
@CrossOrigin(origins = "*")
public class OutOfStockBookController {

    @Autowired
    private OutOfStockBookService outOfStockBookService;

    /**
     * Add a book to out-of-stock list by book ID
     */
    @PostMapping("/add/{bookId}")
    public ResponseEntity<?> addToOutOfStock(@PathVariable Long bookId) {
        try {
            OutOfStockBook outOfStockBook = outOfStockBookService.addToOutOfStock(bookId);
            return ResponseEntity.ok(Map.of(
                "message", "Book added to out-of-stock list successfully",
                "data", outOfStockBook
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Add a book to out-of-stock list by ISBN
     */
    @PostMapping("/add-by-isbn/{isbn}")
    public ResponseEntity<?> addToOutOfStockByIsbn(@PathVariable String isbn) {
        try {
            OutOfStockBook outOfStockBook = outOfStockBookService.addToOutOfStockByIsbn(isbn);
            return ResponseEntity.ok(Map.of(
                "message", "Book added to out-of-stock list successfully",
                "data", outOfStockBook
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Get all out-of-stock books
     */
    @GetMapping
    public ResponseEntity<List<OutOfStockBook>> getAllOutOfStockBooks() {
        List<OutOfStockBook> books = outOfStockBookService.getAllOutOfStockBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * Get all restocked books
     */
    @GetMapping("/restocked")
    public ResponseEntity<List<OutOfStockBook>> getAllRestockedBooks() {
        List<OutOfStockBook> books = outOfStockBookService.getAllRestockedBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * Get out-of-stock book by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getOutOfStockBookById(@PathVariable Long id) {
        Optional<OutOfStockBook> book = outOfStockBookService.getOutOfStockBookById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get out-of-stock book by ISBN
     */
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<?> getOutOfStockBookByIsbn(@PathVariable String isbn) {
        Optional<OutOfStockBook> book = outOfStockBookService.getOutOfStockBookByIsbn(isbn);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update out-of-stock book
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOutOfStockBook(@PathVariable Long id, @RequestBody OutOfStockBook updatedBook) {
        try {
            OutOfStockBook book = outOfStockBookService.updateOutOfStockBook(id, updatedBook);
            return ResponseEntity.ok(Map.of(
                "message", "Out-of-stock book updated successfully",
                "data", book
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Mark book as restocked
     */
    @PostMapping("/{id}/restock")
    public ResponseEntity<?> markAsRestocked(@PathVariable Long id, @RequestParam Integer restockQuantity) {
        try {
            OutOfStockBook book = outOfStockBookService.markAsRestocked(id, restockQuantity);
            return ResponseEntity.ok(Map.of(
                "message", "Book marked as restocked successfully",
                "data", book
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Get out-of-stock books by priority
     */
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<OutOfStockBook>> getOutOfStockBooksByPriority(@PathVariable String priority) {
        try {
            OutOfStockBook.RestockPriority restockPriority = OutOfStockBook.RestockPriority.valueOf(priority.toUpperCase());
            List<OutOfStockBook> books = outOfStockBookService.getOutOfStockBooksByPriority(restockPriority);
            return ResponseEntity.ok(books);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get out-of-stock books by category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<OutOfStockBook>> getOutOfStockBooksByCategory(@PathVariable String category) {
        List<OutOfStockBook> books = outOfStockBookService.getOutOfStockBooksByCategory(category);
        return ResponseEntity.ok(books);
    }

    /**
     * Get overdue books for restock
     */
    @GetMapping("/overdue")
    public ResponseEntity<List<OutOfStockBook>> getOverdueForRestock() {
        List<OutOfStockBook> books = outOfStockBookService.getOverdueForRestock();
        return ResponseEntity.ok(books);
    }

    /**
     * Get out-of-stock books by date range
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<OutOfStockBook>> getOutOfStockBooksByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<OutOfStockBook> books = outOfStockBookService.getOutOfStockBooksByDateRange(startDate, endDate);
        return ResponseEntity.ok(books);
    }

    /**
     * Search out-of-stock books by title
     */
    @GetMapping("/search/title")
    public ResponseEntity<List<OutOfStockBook>> searchByTitle(@RequestParam String title) {
        List<OutOfStockBook> books = outOfStockBookService.searchByTitle(title);
        return ResponseEntity.ok(books);
    }

    /**
     * Search out-of-stock books by author
     */
    @GetMapping("/search/author")
    public ResponseEntity<List<OutOfStockBook>> searchByAuthor(@RequestParam String author) {
        List<OutOfStockBook> books = outOfStockBookService.searchByAuthor(author);
        return ResponseEntity.ok(books);
    }

    /**
     * Get statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        long totalCount = outOfStockBookService.getTotalOutOfStockCount();
        long pendingCount = outOfStockBookService.getPendingRestockCount();
        List<Object[]> priorityCounts = outOfStockBookService.getCountByRestockPriority();

        Map<String, Object> stats = Map.of(
            "totalOutOfStockBooks", totalCount,
            "pendingRestockBooks", pendingCount,
            "restockedBooks", totalCount - pendingCount,
            "priorityCounts", priorityCounts
        );

        return ResponseEntity.ok(stats);
    }

    /**
     * Delete out-of-stock book record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOutOfStockBook(@PathVariable Long id) {
        try {
            outOfStockBookService.deleteOutOfStockBook(id);
            return ResponseEntity.ok(Map.of("message", "Out-of-stock book record deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Get all restock priorities
     */
    @GetMapping("/priorities")
    public ResponseEntity<OutOfStockBook.RestockPriority[]> getRestockPriorities() {
        return ResponseEntity.ok(OutOfStockBook.RestockPriority.values());
    }
}
