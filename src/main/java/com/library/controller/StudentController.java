package com.library.controller;

import com.library.entity.Student;
import com.library.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @GetMapping
    public String listStudents(Model model, @RequestParam(required = false) String search) {
        List<Student> students;
        if (search != null && !search.trim().isEmpty()) {
            students = studentService.searchStudents(search);
            model.addAttribute("search", search);
        } else {
            students = studentService.getAllStudents();
        }
        model.addAttribute("students", students);
        model.addAttribute("activeCount", studentService.getActiveStudentsCount());
        return "students/list";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/add";
    }
    
    @PostMapping("/add")
    public String addStudent(@Valid @ModelAttribute("student") Student student, 
                           BindingResult result, 
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "students/add";
        }
        
        // Check if Student ID already exists
        if (studentService.getStudentByStudentId(student.getStudentId()).isPresent()) {
            result.rejectValue("studentId", "error.student", "Student ID already exists");
            return "students/add";
        }
        
        // Check if Email already exists
        if (student.getEmail() != null && !student.getEmail().isEmpty() && 
            studentService.getStudentByEmail(student.getEmail()).isPresent()) {
            result.rejectValue("email", "error.student", "Email already exists");
            return "students/add";
        }
        
        studentService.saveStudent(student);
        redirectAttributes.addFlashAttribute("successMessage", "Student added successfully!");
        return "redirect:/students";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            return "students/edit";
        }
        return "redirect:/students";
    }
    
    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id, 
                              @Valid @ModelAttribute("student") Student student, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "students/edit";
        }
        
        // Check if Student ID already exists for different student
        Optional<Student> existingStudent = studentService.getStudentByStudentId(student.getStudentId());
        if (existingStudent.isPresent() && !existingStudent.get().getId().equals(id)) {
            result.rejectValue("studentId", "error.student", "Student ID already exists");
            return "students/edit";
        }
        
        // Check if Email already exists for different student
        if (student.getEmail() != null && !student.getEmail().isEmpty()) {
            Optional<Student> existingEmail = studentService.getStudentByEmail(student.getEmail());
            if (existingEmail.isPresent() && !existingEmail.get().getId().equals(id)) {
                result.rejectValue("email", "error.student", "Email already exists");
                return "students/edit";
            }
        }
        
        student.setId(id);
        studentService.updateStudent(student);
        redirectAttributes.addFlashAttribute("successMessage", "Student updated successfully!");
        return "redirect:/students";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteStudent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Student deleted successfully!");
        return "redirect:/students";
    }
    
    @GetMapping("/deactivate/{id}")
    public String deactivateStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.deactivateStudent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Student deactivated successfully!");
        return "redirect:/students";
    }
    
    @GetMapping("/activate/{id}")
    public String activateStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        studentService.activateStudent(id);
        redirectAttributes.addFlashAttribute("successMessage", "Student activated successfully!");
        return "redirect:/students";
    }
    
    @GetMapping("/active")
    public String listActiveStudents(Model model) {
        List<Student> students = studentService.getActiveStudents();
        model.addAttribute("students", students);
        model.addAttribute("title", "Active Students");
        return "students/list";
    }
    
    @GetMapping("/inactive")
    public String listInactiveStudents(Model model) {
        List<Student> students = studentService.getInactiveStudents();
        model.addAttribute("students", students);
        model.addAttribute("title", "Inactive Students");
        return "students/list";
    }
    
    @GetMapping("/grade/{grade}")
    public String listStudentsByGrade(@PathVariable String grade, Model model) {
        List<Student> students = studentService.getStudentsByGrade(grade);
        model.addAttribute("students", students);
        model.addAttribute("title", "Students in Grade: " + grade);
        return "students/list";
    }
    
    @GetMapping("/section/{section}")
    public String listStudentsBySection(@PathVariable String section, Model model) {
        List<Student> students = studentService.getStudentsBySection(section);
        model.addAttribute("students", students);
        model.addAttribute("title", "Students in Section: " + section);
        return "students/list";
    }
}
