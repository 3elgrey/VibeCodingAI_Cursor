package com.library.service;

import com.library.entity.Student;
import com.library.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    public Optional<Student> getStudentByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }
    
    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
    
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
    
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }
    
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    
    public List<Student> searchStudents(String keyword) {
        return studentRepository.searchStudents(keyword);
    }
    
    public List<Student> getStudentsByGrade(String grade) {
        return studentRepository.findByGrade(grade);
    }
    
    public List<Student> getStudentsBySection(String section) {
        return studentRepository.findBySection(section);
    }
    
    public List<Student> getStudentsByGradeAndSection(String grade, String section) {
        return studentRepository.findByGradeAndSection(grade, section);
    }
    
    public List<Student> getActiveStudents() {
        return studentRepository.findByIsActive(true);
    }
    
    public List<Student> getInactiveStudents() {
        return studentRepository.findByIsActive(false);
    }
    
    public Long getActiveStudentsCount() {
        return studentRepository.countActiveStudents();
    }
    
    public boolean isStudentActive(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        return student.isPresent() && student.get().getIsActive();
    }
    
    public void deactivateStudent(Long studentId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setIsActive(false);
            studentRepository.save(student);
        }
    }
    
    public void activateStudent(Long studentId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setIsActive(true);
            studentRepository.save(student);
        }
    }
}
