package com.library.controller;

import com.library.service.BookService;
import com.library.service.StudentService;
import com.library.service.BookIssueService;
import com.library.service.BookReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private BookIssueService bookIssueService;
    
    @Autowired
    private BookReturnService bookReturnService;
    
    @GetMapping("/")
    public String home(Model model) {
        // Dashboard statistics
        model.addAttribute("totalBooks", bookService.getAllBooks().size());
        model.addAttribute("availableBooks", bookService.getAvailableBooksCount());
        model.addAttribute("unavailableBooks", bookService.getUnavailableBooksCount());
        model.addAttribute("totalStudents", studentService.getActiveStudentsCount());
        model.addAttribute("issuedBooks", bookIssueService.getIssuedBooksCount());
        model.addAttribute("overdueBooks", bookIssueService.getOverdueBooksCount());
        model.addAttribute("totalFineCollected", bookReturnService.getTotalFineCollected());
        model.addAttribute("unpaidFines", bookReturnService.getTotalUnpaidFines());
        
        return "index";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return home(model);
    }
}
