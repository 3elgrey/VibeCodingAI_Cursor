package com.library.repository;

import com.library.entity.OutOfStockBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for OutOfStockBook entity
 */
@Repository
public interface OutOfStockBookRepository extends JpaRepository<OutOfStockBook, Long> {

    /**
     * Find all out-of-stock books that are not yet restocked
     */
    List<OutOfStockBook> findByIsRestockedFalseOrderByOutOfStockDateDesc();

    /**
     * Find all out-of-stock books that have been restocked
     */
    List<OutOfStockBook> findByIsRestockedTrueOrderByRestockDateDesc();

    /**
     * Find out-of-stock books by restock priority
     */
    List<OutOfStockBook> findByRestockPriorityAndIsRestockedFalseOrderByOutOfStockDateDesc(
            OutOfStockBook.RestockPriority priority);

    /**
     * Find out-of-stock books by category
     */
    List<OutOfStockBook> findByCategoryAndIsRestockedFalseOrderByOutOfStockDateDesc(String category);

    /**
     * Find out-of-stock books that are overdue for restock
     */
    @Query("SELECT o FROM OutOfStockBook o WHERE o.expectedRestockDate < :currentDate AND o.isRestocked = false")
    List<OutOfStockBook> findOverdueForRestock(@Param("currentDate") LocalDate currentDate);

    /**
     * Find out-of-stock books by date range
     */
    @Query("SELECT o FROM OutOfStockBook o WHERE o.outOfStockDate BETWEEN :startDate AND :endDate ORDER BY o.outOfStockDate DESC")
    List<OutOfStockBook> findByOutOfStockDateBetween(@Param("startDate") LocalDate startDate, 
                                                   @Param("endDate") LocalDate endDate);

    /**
     * Count out-of-stock books by restock priority
     */
    @Query("SELECT o.restockPriority, COUNT(o) FROM OutOfStockBook o WHERE o.isRestocked = false GROUP BY o.restockPriority")
    List<Object[]> countByRestockPriority();

    /**
     * Find out-of-stock books by ISBN
     */
    OutOfStockBook findByIsbn(String isbn);

    /**
     * Check if a book is already in out-of-stock list
     */
    boolean existsByIsbnAndIsRestockedFalse(String isbn);

    /**
     * Find out-of-stock books by author
     */
    List<OutOfStockBook> findByAuthorContainingIgnoreCaseAndIsRestockedFalseOrderByOutOfStockDateDesc(String author);

    /**
     * Find out-of-stock books by title
     */
    List<OutOfStockBook> findByTitleContainingIgnoreCaseAndIsRestockedFalseOrderByOutOfStockDateDesc(String title);
}
