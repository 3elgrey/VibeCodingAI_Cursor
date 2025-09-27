package com.library.controller;

import com.library.entity.Book;
import com.library.entity.BookIssue;
import com.library.entity.Student;
import com.library.service.BookIssueService;
import com.library.service.BookService;
import com.library.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/issues")
public class BookIssueController {
    
    @Autowired
    private BookIssueService bookIssueService;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private StudentService studentService;
    
    @GetMapping
    public String listIssues(Model model, @RequestParam(required = false) String status) {
        List<BookIssue> issues;
        if (status != null && !status.isEmpty()) {
            try {
                BookIssue.IssueStatus issueStatus = BookIssue.IssueStatus.valueOf(status.toUpperCase());
                issues = bookIssueService.getBookIssuesByStatus(issueStatus);
            } catch (IllegalArgumentException e) {
                issues = bookIssueService.getAllBookIssues();
            }
        } else {
            issues = bookIssueService.getAllBookIssues();
        }
        
        model.addAttribute("issues", issues);
        model.addAttribute("issuedCount", bookIssueService.getIssuedBooksCount());
        model.addAttribute("returnedCount", bookIssueService.getReturnedBooksCount());
        model.addAttribute("overdueCount", bookIssueService.getOverdueBooksCount());
        return "issues/list";
    }
    
    @GetMapping("/active")
    public String listActiveIssues(Model model) {
        List<BookIssue> issues = bookIssueService.getActiveBookIssues();
        model.addAttribute("issues", issues);
        model.addAttribute("title", "Active Book Issues");
        return "issues/list";
    }
    
    @GetMapping("/overdue")
    public String listOverdueIssues(Model model) {
        List<BookIssue> issues = bookIssueService.getOverdueBooks();
        model.addAttribute("issues", issues);
        model.addAttribute("title", "Overdue Books");
        return "issues/list";
    }
    
    @GetMapping("/issue")
    public String showIssueForm(Model model) {
        List<Book> availableBooks = bookService.getAvailableBooks();
        List<Student> activeStudents = studentService.getActiveStudents();
        
        model.addAttribute("availableBooks", availableBooks);
        model.addAttribute("activeStudents", activeStudents);
        model.addAttribute("issue", new BookIssue());
        return "issues/issue";
    }
    
    @PostMapping("/issue")
    public String issueBook(@RequestParam Long bookId, 
                          @RequestParam Long studentId, 
                          @RequestParam(defaultValue = "14") int daysToReturn,
                          RedirectAttributes redirectAttributes) {
        try {
            BookIssue bookIssue = bookIssueService.issueBook(bookId, studentId, daysToReturn);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Book issued successfully! Due date: " + bookIssue.getDueDate());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/issues";
    }
    
    @GetMapping("/issue-by-student-id")
    public String showIssueByStudentIdForm(Model model) {
        List<Book> availableBooks = bookService.getAvailableBooks();
        model.addAttribute("availableBooks", availableBooks);
        return "issues/issue-by-student-id";
    }
    
    @PostMapping("/issue-by-student-id")
    public String issueBookByStudentId(@RequestParam Long bookId, 
                                     @RequestParam String studentId, 
                                     @RequestParam(defaultValue = "14") int daysToReturn,
                                     RedirectAttributes redirectAttributes) {
        try {
            BookIssue bookIssue = bookIssueService.issueBookByStudentId(bookId, studentId, daysToReturn);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Book issued successfully! Due date: " + bookIssue.getDueDate());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/issues";
    }
    
    @GetMapping("/student/{studentId}")
    public String listIssuesByStudent(@PathVariable String studentId, Model model) {
        List<BookIssue> issues = bookIssueService.getActiveIssuesByStudentId(studentId);
        model.addAttribute("issues", issues);
        model.addAttribute("studentId", studentId);
        return "issues/student-issues";
    }
    
    @GetMapping("/book/{bookId}")
    public String listIssuesByBook(@PathVariable Long bookId, Model model) {
        List<BookIssue> issues = bookIssueService.getBookIssuesByBook(bookId);
        model.addAttribute("issues", issues);
        return "issues/book-issues";
    }
    
    @GetMapping("/mark-overdue/{id}")
    public String markAsOverdue(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookIssueService.markAsOverdue(id);
        redirectAttributes.addFlashAttribute("successMessage", "Book marked as overdue!");
        return "redirect:/issues";
    }
    
    @GetMapping("/mark-lost/{id}")
    public String markAsLost(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookIssueService.markAsLost(id);
        redirectAttributes.addFlashAttribute("successMessage", "Book marked as lost!");
        return "redirect:/issues";
    }
    
    @GetMapping("/return/{id}")
    public String showReturnForm(@PathVariable Long id, Model model) {
        BookIssue issue = bookIssueService.getBookIssueById(id)
                .orElseThrow(() -> new RuntimeException("Book issue not found"));
        
        double fineAmount = bookIssueService.calculateFineForIssue(id);
        model.addAttribute("issue", issue);
        model.addAttribute("fineAmount", fineAmount);
        return "issues/return";
    }
}
