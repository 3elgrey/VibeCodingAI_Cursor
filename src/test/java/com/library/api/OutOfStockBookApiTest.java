package com.library.api;

import com.library.entity.Book;
import com.library.entity.OutOfStockBook;
import com.library.service.BookService;
import com.library.service.OutOfStockBookService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

/**
 * Test class for OutOfStockBook API functionality
 */
@SpringBootTest
@ActiveProfiles("test")
public class OutOfStockBookApiTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private OutOfStockBookService outOfStockBookService;

    private Book testBook;

    @BeforeEach
    void setUp() {
        // Create a test book with unique ISBN for each test
        testBook = new Book();
        testBook.setIsbn("TEST-OOS-" + System.currentTimeMillis());
        testBook.setTitle("Test Out of Stock Book");
        testBook.setAuthor("Test Author");
        testBook.setPublisher("Test Publisher");
        testBook.setPublicationDate(LocalDate.of(2024, 1, 1));
        testBook.setCategory("Test Category");
        testBook.setTotalCopies(5);
        testBook.setAvailableCopies(0); // Set to 0 to simulate out of stock
        testBook.setCreatedDate(LocalDate.now());
        
        testBook = bookService.saveBook(testBook);
    }

    @AfterEach
    void tearDown() {
        // Clean up test data
        if (testBook != null) {
            bookService.deleteBook(testBook.getId());
        }
    }

    @Test
    @DisplayName("Should add book to out-of-stock list")
    void shouldAddBookToOutOfStockList() {
        // Add book to out-of-stock list
        OutOfStockBook outOfStockBook = outOfStockBookService.addToOutOfStock(testBook.getId());
        
        // Verify the book was added
        Assertions.assertNotNull(outOfStockBook.getId());
        Assertions.assertEquals(testBook.getIsbn(), outOfStockBook.getIsbn());
        Assertions.assertEquals(testBook.getTitle(), outOfStockBook.getTitle());
        Assertions.assertEquals(testBook.getAuthor(), outOfStockBook.getAuthor());
        Assertions.assertEquals(testBook.getAvailableCopies(), outOfStockBook.getLastAvailableQuantity());
        Assertions.assertFalse(outOfStockBook.getIsRestocked());
        
        System.out.println("✅ Book successfully added to out-of-stock list");
    }

    @Test
    @DisplayName("Should retrieve all out-of-stock books")
    void shouldRetrieveAllOutOfStockBooks() {
        // Add book to out-of-stock list
        outOfStockBookService.addToOutOfStock(testBook.getId());
        
        // Get all out-of-stock books
        var outOfStockBooks = outOfStockBookService.getAllOutOfStockBooks();
        
        // Verify the book is in the list
        Assertions.assertTrue(outOfStockBooks.size() > 0);
        Assertions.assertTrue(outOfStockBooks.stream()
            .anyMatch(book -> book.getIsbn().equals(testBook.getIsbn())));
        
        System.out.println("✅ Successfully retrieved out-of-stock books");
    }

    @Test
    @DisplayName("Should update out-of-stock book priority")
    void shouldUpdateOutOfStockBookPriority() {
        // Add book to out-of-stock list
        OutOfStockBook outOfStockBook = outOfStockBookService.addToOutOfStock(testBook.getId());
        
        // Update priority
        outOfStockBook.setRestockPriority(OutOfStockBook.RestockPriority.HIGH);
        outOfStockBook.setExpectedRestockDate(LocalDate.now().plusDays(7));
        outOfStockBook.setNotes("Urgent restock needed");
        
        OutOfStockBook updatedBook = outOfStockBookService.updateOutOfStockBook(outOfStockBook.getId(), outOfStockBook);
        
        // Verify the update
        Assertions.assertEquals(OutOfStockBook.RestockPriority.HIGH, updatedBook.getRestockPriority());
        Assertions.assertEquals(LocalDate.now().plusDays(7), updatedBook.getExpectedRestockDate());
        Assertions.assertEquals("Urgent restock needed", updatedBook.getNotes());
        
        System.out.println("✅ Successfully updated out-of-stock book priority");
    }

    @Test
    @DisplayName("Should mark book as restocked")
    void shouldMarkBookAsRestocked() {
        // Add book to out-of-stock list
        OutOfStockBook outOfStockBook = outOfStockBookService.addToOutOfStock(testBook.getId());
        
        // Mark as restocked
        int restockQuantity = 10;
        OutOfStockBook restockedBook = outOfStockBookService.markAsRestocked(outOfStockBook.getId(), restockQuantity);
        
        // Verify the book is marked as restocked
        Assertions.assertTrue(restockedBook.getIsRestocked());
        Assertions.assertEquals(LocalDate.now(), restockedBook.getRestockDate());
        
        // Verify the original book's available copies were updated
        Book updatedBook = bookService.getBookById(testBook.getId()).orElse(null);
        Assertions.assertNotNull(updatedBook);
        Assertions.assertEquals(restockQuantity, updatedBook.getAvailableCopies());
        
        System.out.println("✅ Successfully marked book as restocked");
    }

    @Test
    @DisplayName("Should get out-of-stock books by priority")
    void shouldGetOutOfStockBooksByPriority() {
        // Add book to out-of-stock list
        OutOfStockBook outOfStockBook = outOfStockBookService.addToOutOfStock(testBook.getId());
        
        // Set priority to HIGH
        outOfStockBook.setRestockPriority(OutOfStockBook.RestockPriority.HIGH);
        outOfStockBookService.updateOutOfStockBook(outOfStockBook.getId(), outOfStockBook);
        
        // Get books by priority
        var highPriorityBooks = outOfStockBookService.getOutOfStockBooksByPriority(OutOfStockBook.RestockPriority.HIGH);
        
        // Verify the book is in the high priority list
        Assertions.assertTrue(highPriorityBooks.size() > 0);
        Assertions.assertTrue(highPriorityBooks.stream()
            .anyMatch(book -> book.getIsbn().equals(testBook.getIsbn())));
        
        System.out.println("✅ Successfully retrieved books by priority");
    }

    @Test
    @DisplayName("Should get statistics")
    void shouldGetStatistics() {
        // Add book to out-of-stock list
        outOfStockBookService.addToOutOfStock(testBook.getId());
        
        // Get statistics
        long totalCount = outOfStockBookService.getTotalOutOfStockCount();
        long pendingCount = outOfStockBookService.getPendingRestockCount();
        var priorityCounts = outOfStockBookService.getCountByRestockPriority();
        
        // Verify statistics
        Assertions.assertTrue(totalCount > 0);
        Assertions.assertTrue(pendingCount > 0);
        Assertions.assertNotNull(priorityCounts);
        
        System.out.println("✅ Statistics retrieved successfully");
        System.out.println("Total out-of-stock books: " + totalCount);
        System.out.println("Pending restock books: " + pendingCount);
    }
}
