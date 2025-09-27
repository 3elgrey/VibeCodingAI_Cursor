package com.library.controller;

import com.library.entity.Book;
import com.library.service.BookService;
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
@RequestMapping("/books")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @GetMapping
    public String listBooks(Model model, @RequestParam(required = false) String search) {
        List<Book> books;
        if (search != null && !search.trim().isEmpty()) {
            books = bookService.searchBooks(search);
            model.addAttribute("search", search);
        } else {
            books = bookService.getAllBooks();
        }
        model.addAttribute("books", books);
        model.addAttribute("availableCount", bookService.getAvailableBooksCount());
        model.addAttribute("unavailableCount", bookService.getUnavailableBooksCount());
        return "books/list";
    }
    
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/add";
    }
    
    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") Book book, 
                         BindingResult result, 
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "books/add";
        }
        
        // Check if ISBN already exists
        if (bookService.getBookByIsbn(book.getIsbn()).isPresent()) {
            result.rejectValue("isbn", "error.book", "ISBN already exists");
            return "books/add";
        }
        
        bookService.saveBook(book);
        redirectAttributes.addFlashAttribute("successMessage", "Book added successfully!");
        return "redirect:/books";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            return "books/edit";
        }
        return "redirect:/books";
    }
    
    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id, 
                           @Valid @ModelAttribute("book") Book book, 
                           BindingResult result, 
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "books/edit";
        }
        
        // Check if ISBN already exists for different book
        Optional<Book> existingBook = bookService.getBookByIsbn(book.getIsbn());
        if (existingBook.isPresent() && !existingBook.get().getId().equals(id)) {
            result.rejectValue("isbn", "error.book", "ISBN already exists");
            return "books/edit";
        }
        
        book.setId(id);
        bookService.updateBook(book);
        redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully!");
        return "redirect:/books";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        bookService.deleteBook(id);
        redirectAttributes.addFlashAttribute("successMessage", "Book deleted successfully!");
        return "redirect:/books";
    }
    
    @GetMapping("/available")
    public String listAvailableBooks(Model model) {
        List<Book> books = bookService.getAvailableBooks();
        model.addAttribute("books", books);
        model.addAttribute("title", "Available Books");
        return "books/list";
    }
    
    @GetMapping("/unavailable")
    public String listUnavailableBooks(Model model) {
        List<Book> books = bookService.getUnavailableBooks();
        model.addAttribute("books", books);
        model.addAttribute("title", "Unavailable Books");
        return "books/list";
    }
    
    @GetMapping("/category/{category}")
    public String listBooksByCategory(@PathVariable String category, Model model) {
        List<Book> books = bookService.getBooksByCategory(category);
        model.addAttribute("books", books);
        model.addAttribute("title", "Books in Category: " + category);
        return "books/list";
    }
    
    @GetMapping("/author/{author}")
    public String listBooksByAuthor(@PathVariable String author, Model model) {
        List<Book> books = bookService.getBooksByAuthor(author);
        model.addAttribute("books", books);
        model.addAttribute("title", "Books by Author: " + author);
        return "books/list";
    }
}
