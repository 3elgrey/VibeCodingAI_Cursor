package com.library.e2e;

import com.library.entity.Book;
import com.library.entity.Student;
import com.library.service.BookService;
import com.library.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Utility class for setting up test data for E2E tests
 */
@Component
public class TestDataSetup {

    @Autowired
    private BookService bookService;

    @Autowired
    private StudentService studentService;

    /**
     * Creates sample books for testing
     */
    public void createSampleBooks() {
        // Create sample books if they don't exist
        if (bookService.getAllBooks().isEmpty()) {
            Book book1 = new Book();
            book1.setIsbn("9780123456789");
            book1.setTitle("Test Book 1");
            book1.setAuthor("Test Author 1");
            book1.setPublisher("Test Publisher");
            book1.setPublicationDate(LocalDate.of(2024, 1, 1));
            book1.setCategory("Programming");
            book1.setTotalCopies(5);
            book1.setAvailableCopies(5);
            book1.setCreatedDate(LocalDate.now());
            bookService.saveBook(book1);

            Book book2 = new Book();
            book2.setIsbn("9780123456790");
            book2.setTitle("Test Book 2");
            book2.setAuthor("Test Author 2");
            book2.setPublisher("Test Publisher");
            book2.setPublicationDate(LocalDate.of(2024, 1, 1));
            book2.setCategory("Science");
            book2.setTotalCopies(3);
            book2.setAvailableCopies(3);
            book2.setCreatedDate(LocalDate.now());
            bookService.saveBook(book2);
        }
    }

    /**
     * Creates sample students for testing
     */
    public void createSampleStudents() {
        // Create sample students if they don't exist
        if (studentService.getAllStudents().isEmpty()) {
            Student student1 = new Student();
            student1.setStudentId("STU001");
            student1.setFirstName("John");
            student1.setLastName("Doe");
            student1.setEmail("john.doe@test.com");
            student1.setPhone("9876543210");
            student1.setGrade("10");
            student1.setSection("A");
            student1.setEnrollmentDate(LocalDate.now());
            student1.setIsActive(true);
            studentService.saveStudent(student1);

            Student student2 = new Student();
            student2.setStudentId("STU002");
            student2.setFirstName("Jane");
            student2.setLastName("Smith");
            student2.setEmail("jane.smith@test.com");
            student2.setPhone("9876543211");
            student2.setGrade("11");
            student2.setSection("B");
            student2.setEnrollmentDate(LocalDate.now());
            student2.setIsActive(true);
            studentService.saveStudent(student2);
        }
    }

    /**
     * Cleans up test data
     */
    public void cleanupTestData() {
        // This method can be used to clean up test data if needed
        // For now, we'll let the database be recreated for each test run
    }
}
