package com.library.controller;

import com.library.entity.BookReturn;
import com.library.service.BookReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/returns")
public class BookReturnController {
    
    @Autowired
    private BookReturnService bookReturnService;
    
    @GetMapping
    public String listReturns(Model model, @RequestParam(required = false) String status) {
        List<BookReturn> returns;
        if ("unpaid".equals(status)) {
            returns = bookReturnService.getUnpaidFines();
        } else if ("paid".equals(status)) {
            returns = bookReturnService.getPaidFines();
        } else {
            returns = bookReturnService.getAllBookReturns();
        }
        
        model.addAttribute("returns", returns);
        model.addAttribute("totalFineCollected", bookReturnService.getTotalFineCollected());
        model.addAttribute("totalUnpaidFines", bookReturnService.getTotalUnpaidFines());
        model.addAttribute("unpaidFinesCount", bookReturnService.getUnpaidFinesCount());
        return "returns/list";
    }
    
    @GetMapping("/unpaid")
    public String listUnpaidFines(Model model) {
        List<BookReturn> returns = bookReturnService.getUnpaidFines();
        model.addAttribute("returns", returns);
        model.addAttribute("title", "Unpaid Fines");
        return "returns/list";
    }
    
    @GetMapping("/paid")
    public String listPaidFines(Model model) {
        List<BookReturn> returns = bookReturnService.getPaidFines();
        model.addAttribute("returns", returns);
        model.addAttribute("title", "Paid Fines");
        return "returns/list";
    }
    
    @PostMapping("/return")
    public String returnBook(@RequestParam Long issueId, 
                           @RequestParam String condition, 
                           @RequestParam(required = false) String remarks,
                           RedirectAttributes redirectAttributes) {
        try {
            BookReturn.BookCondition bookCondition = BookReturn.BookCondition.valueOf(condition.toUpperCase());
            BookReturn bookReturn = bookReturnService.returnBook(issueId, bookCondition, remarks);
            
            String message = "Book returned successfully!";
            if (bookReturn.getFineAmount() > 0) {
                message += " Fine amount: ₹" + bookReturn.getFineAmount();
            }
            
            redirectAttributes.addFlashAttribute("successMessage", message);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/issues";
    }
    
    @GetMapping("/pay-fine/{id}")
    public String payFine(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookReturnService.payFine(id);
        redirectAttributes.addFlashAttribute("successMessage", "Fine paid successfully!");
        return "redirect:/returns";
    }
    
    @GetMapping("/student/{studentId}")
    public String listReturnsByStudent(@PathVariable Long studentId, Model model) {
        List<BookReturn> returns = bookReturnService.getBookReturnsByStudent(studentId);
        model.addAttribute("returns", returns);
        model.addAttribute("studentId", studentId);
        return "returns/student-returns";
    }
    
    @GetMapping("/book/{bookId}")
    public String listReturnsByBook(@PathVariable Long bookId, Model model) {
        List<BookReturn> returns = bookReturnService.getBookReturnsByBook(bookId);
        model.addAttribute("returns", returns);
        return "returns/book-returns";
    }
    
    @GetMapping("/date-range")
    public String showDateRangeForm(Model model) {
        return "returns/date-range";
    }
    
    @PostMapping("/date-range")
    public String listReturnsByDateRange(@RequestParam LocalDate startDate, 
                                       @RequestParam LocalDate endDate, 
                                       Model model) {
        List<BookReturn> returns = bookReturnService.getBookReturnsByDateRange(startDate, endDate);
        model.addAttribute("returns", returns);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "returns/list";
    }
}
