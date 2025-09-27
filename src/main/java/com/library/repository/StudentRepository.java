package com.library.repository;

import com.library.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Optional<Student> findByStudentId(String studentId);
    
    Optional<Student> findByEmail(String email);
    
    List<Student> findByFirstNameContainingIgnoreCase(String firstName);
    
    List<Student> findByLastNameContainingIgnoreCase(String lastName);
    
    List<Student> findByGrade(String grade);
    
    List<Student> findBySection(String section);
    
    List<Student> findByIsActive(Boolean isActive);
    
    @Query("SELECT s FROM Student s WHERE s.firstName LIKE %:keyword% OR s.lastName LIKE %:keyword% OR s.studentId LIKE %:keyword% OR s.email LIKE %:keyword%")
    List<Student> searchStudents(@Param("keyword") String keyword);
    
    @Query("SELECT s FROM Student s WHERE s.grade = :grade AND s.section = :section")
    List<Student> findByGradeAndSection(@Param("grade") String grade, @Param("section") String section);
    
    @Query("SELECT COUNT(s) FROM Student s WHERE s.isActive = true")
    Long countActiveStudents();
}
