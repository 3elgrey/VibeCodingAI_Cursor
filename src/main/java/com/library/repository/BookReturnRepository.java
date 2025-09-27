package com.library.repository;

import com.library.entity.BookReturn;
import com.library.entity.BookIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookReturnRepository extends JpaRepository<BookReturn, Long> {
    
    Optional<BookReturn> findByBookIssue(BookIssue bookIssue);
    
    List<BookReturn> findByReturnDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<BookReturn> findByFinePaid(Boolean finePaid);
    
    List<BookReturn> findByReturnCondition(BookReturn.BookCondition condition);
    
    @Query("SELECT br FROM BookReturn br WHERE br.bookIssue.student.id = :studentId")
    List<BookReturn> findByStudentId(@Param("studentId") Long studentId);
    
    @Query("SELECT br FROM BookReturn br WHERE br.bookIssue.book.id = :bookId")
    List<BookReturn> findByBookId(@Param("bookId") Long bookId);
    
    @Query("SELECT br FROM BookReturn br WHERE br.returnDate BETWEEN :startDate AND :endDate")
    List<BookReturn> findByReturnDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT br FROM BookReturn br WHERE br.fineAmount > 0")
    List<BookReturn> findReturnsWithFines();
    
    @Query("SELECT br FROM BookReturn br WHERE br.fineAmount > 0 AND br.finePaid = false")
    List<BookReturn> findUnpaidFines();
    
    @Query("SELECT br FROM BookReturn br WHERE br.fineAmount > 0 AND br.finePaid = true")
    List<BookReturn> findPaidFines();
    
    @Query("SELECT SUM(br.fineAmount) FROM BookReturn br WHERE br.finePaid = true")
    Double getTotalFineCollected();
    
    @Query("SELECT SUM(br.fineAmount) FROM BookReturn br WHERE br.finePaid = false")
    Double getTotalUnpaidFines();
    
    @Query("SELECT COUNT(br) FROM BookReturn br WHERE br.fineAmount > 0")
    Long countReturnsWithFines();
    
    @Query("SELECT COUNT(br) FROM BookReturn br WHERE br.fineAmount > 0 AND br.finePaid = false")
    Long countUnpaidFines();
}
