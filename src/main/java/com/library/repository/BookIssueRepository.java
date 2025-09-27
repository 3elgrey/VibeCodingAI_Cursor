package com.library.repository;

import com.library.entity.BookIssue;
import com.library.entity.Student;
import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {
    
    List<BookIssue> findByStudent(Student student);
    
    List<BookIssue> findByBook(Book book);
    
    List<BookIssue> findByStatus(BookIssue.IssueStatus status);
    
    List<BookIssue> findByStudentAndStatus(Student student, BookIssue.IssueStatus status);
    
    @Query("SELECT bi FROM BookIssue bi WHERE bi.student.id = :studentId AND bi.status = :status")
    List<BookIssue> findByStudentIdAndStatus(@Param("studentId") Long studentId, @Param("status") BookIssue.IssueStatus status);
    
    @Query("SELECT bi FROM BookIssue bi WHERE bi.book.id = :bookId AND bi.status = :status")
    List<BookIssue> findByBookIdAndStatus(@Param("bookId") Long bookId, @Param("status") BookIssue.IssueStatus status);
    
    @Query("SELECT bi FROM BookIssue bi WHERE bi.dueDate < :currentDate AND bi.status = 'ISSUED'")
    List<BookIssue> findOverdueBooks(@Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT bi FROM BookIssue bi WHERE bi.issueDate BETWEEN :startDate AND :endDate")
    List<BookIssue> findByIssueDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT bi FROM BookIssue bi WHERE bi.dueDate BETWEEN :startDate AND :endDate")
    List<BookIssue> findByDueDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT COUNT(bi) FROM BookIssue bi WHERE bi.status = 'ISSUED'")
    Long countIssuedBooks();
    
    @Query("SELECT COUNT(bi) FROM BookIssue bi WHERE bi.status = 'RETURNED'")
    Long countReturnedBooks();
    
    @Query("SELECT COUNT(bi) FROM BookIssue bi WHERE bi.dueDate < :currentDate AND bi.status = 'ISSUED'")
    Long countOverdueBooks(@Param("currentDate") LocalDate currentDate);
    
    @Query("SELECT bi FROM BookIssue bi WHERE bi.student.id = :studentId AND bi.book.id = :bookId AND bi.status = 'ISSUED'")
    Optional<BookIssue> findActiveIssueByStudentAndBook(@Param("studentId") Long studentId, @Param("bookId") Long bookId);
    
    @Query("SELECT bi FROM BookIssue bi WHERE bi.student.studentId = :studentId AND bi.status = 'ISSUED'")
    List<BookIssue> findActiveIssuesByStudentId(@Param("studentId") String studentId);
}
