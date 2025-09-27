package com.library.e2e;

import com.library.entity.Book;
import com.library.entity.Student;
import com.library.service.BookService;
import com.library.service.StudentService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

/**
 * Simple Functional Test
 * 
 * Tests core application functionality with dummy data
 * No DOM testing - focuses on business logic
 */
@SpringBootTest
@ActiveProfiles("test")
public class SimpleDashboardTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private StudentService studentService;

    @Test
    @DisplayName("Should create and retrieve books")
    void shouldCreateAndRetrieveBooks() {
        // Create a test book
        Book testBook = new Book();
        testBook.setIsbn("TEST-ISBN-001");
        testBook.setTitle("Test Book Title");
        testBook.setAuthor("Test Author");
        testBook.setPublisher("Test Publisher");
        testBook.setPublicationDate(LocalDate.of(2024, 1, 1));
        testBook.setCategory("Test Category");
        testBook.setTotalCopies(5);
        testBook.setAvailableCopies(5);
        testBook.setCreatedDate(LocalDate.now());
        
        // Save the book
        Book savedBook = bookService.saveBook(testBook);
        
        // Verify book was saved
        Assertions.assertNotNull(savedBook.getId());
        Assertions.assertEquals("TEST-ISBN-001", savedBook.getIsbn());
        Assertions.assertEquals("Test Book Title", savedBook.getTitle());
        
        // Retrieve all books
        var allBooks = bookService.getAllBooks();
        Assertions.assertTrue(allBooks.size() > 0);
        
        System.out.println("✅ Book creation and retrieval test passed!");
    }

    @Test
    @DisplayName("Should create and retrieve students")
    void shouldCreateAndRetrieveStudents() {
        // Create a test student
        Student testStudent = new Student();
        testStudent.setStudentId("TEST-STU-001");
        testStudent.setFirstName("John");
        testStudent.setLastName("Doe");
        testStudent.setEmail("john.doe@test.com");
        testStudent.setPhone("9876543210");
        testStudent.setGrade("10");
        testStudent.setSection("A");
        testStudent.setEnrollmentDate(LocalDate.now());
        testStudent.setIsActive(true);
        
        // Save the student
        Student savedStudent = studentService.saveStudent(testStudent);
        
        // Verify student was saved
        Assertions.assertNotNull(savedStudent.getId());
        Assertions.assertEquals("TEST-STU-001", savedStudent.getStudentId());
        Assertions.assertEquals("John", savedStudent.getFirstName());
        Assertions.assertEquals("Doe", savedStudent.getLastName());
        
        // Retrieve all students
        var allStudents = studentService.getAllStudents();
        Assertions.assertTrue(allStudents.size() > 0);
        
        System.out.println("✅ Student creation and retrieval test passed!");
    }

    @Test
    @DisplayName("Should calculate book statistics")
    void shouldCalculateBookStatistics() {
        // Create test books with different availability
        Book availableBook = new Book();
        availableBook.setIsbn("AVAILABLE-001");
        availableBook.setTitle("Available Book");
        availableBook.setAuthor("Test Author");
        availableBook.setPublisher("Test Publisher");
        availableBook.setPublicationDate(LocalDate.of(2024, 1, 1));
        availableBook.setCategory("Test Category");
        availableBook.setTotalCopies(3);
        availableBook.setAvailableCopies(3);
        availableBook.setCreatedDate(LocalDate.now());
        bookService.saveBook(availableBook);
        
        Book unavailableBook = new Book();
        unavailableBook.setIsbn("UNAVAILABLE-001");
        unavailableBook.setTitle("Unavailable Book");
        unavailableBook.setAuthor("Test Author");
        unavailableBook.setPublisher("Test Publisher");
        unavailableBook.setPublicationDate(LocalDate.of(2024, 1, 1));
        unavailableBook.setCategory("Test Category");
        unavailableBook.setTotalCopies(2);
        unavailableBook.setAvailableCopies(0);
        unavailableBook.setCreatedDate(LocalDate.now());
        bookService.saveBook(unavailableBook);
        
        // Test statistics calculation
        Long totalBooks = bookService.getTotalBooksCount();
        Long availableBooks = bookService.getAvailableBooksCount();
        Long unavailableBooks = bookService.getUnavailableBooksCount();
        
        Assertions.assertTrue(totalBooks >= 2);
        Assertions.assertTrue(availableBooks >= 1);
        Assertions.assertTrue(unavailableBooks >= 1);
        Assertions.assertEquals(totalBooks, availableBooks + unavailableBooks);
        
        System.out.println("✅ Book statistics calculation test passed!");
    }

    @Test
    @DisplayName("Should validate book data")
    void shouldValidateBookData() {
        // Test book with valid data
        Book validBook = new Book();
        validBook.setIsbn("VALID-ISBN-001");
        validBook.setTitle("Valid Book");
        validBook.setAuthor("Valid Author");
        validBook.setPublisher("Valid Publisher");
        validBook.setPublicationDate(LocalDate.of(2024, 1, 1));
        validBook.setCategory("Valid Category");
        validBook.setTotalCopies(1);
        validBook.setAvailableCopies(1);
        validBook.setCreatedDate(LocalDate.now());
        
        Book savedBook = bookService.saveBook(validBook);
        Assertions.assertNotNull(savedBook);
        Assertions.assertEquals("VALID-ISBN-001", savedBook.getIsbn());
        
        // Test duplicate ISBN validation
        Book duplicateBook = new Book();
        duplicateBook.setIsbn("VALID-ISBN-001"); // Same ISBN
        duplicateBook.setTitle("Duplicate Book");
        duplicateBook.setAuthor("Duplicate Author");
        duplicateBook.setPublisher("Duplicate Publisher");
        duplicateBook.setPublicationDate(LocalDate.of(2024, 1, 1));
        duplicateBook.setCategory("Duplicate Category");
        duplicateBook.setTotalCopies(1);
        duplicateBook.setAvailableCopies(1);
        duplicateBook.setCreatedDate(LocalDate.now());
        
        // This should either throw an exception or return null
        try {
            Book duplicateResult = bookService.saveBook(duplicateBook);
            // If no exception, the duplicate should not be saved
            Assertions.assertNull(duplicateResult);
        } catch (Exception e) {
            // Expected behavior - duplicate ISBN should cause an error
            System.out.println("✅ Duplicate ISBN validation working correctly");
        }
        
        System.out.println("✅ Book data validation test passed!");
    }
}
