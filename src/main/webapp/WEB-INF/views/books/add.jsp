<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="Add New Book" />
<c:set var="pageName" value="books" />

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="fas fa-plus me-2"></i>Add New Book</h2>
    <a href="/books" class="btn btn-outline-secondary">
        <i class="fas fa-arrow-left me-2"></i>Back to Books
    </a>
</div>

<div class="row">
    <div class="col-lg-8">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Book Information</h5>
            </div>
            <div class="card-body">
                <form action="/books/add" method="post" class="needs-validation" novalidate="novalidate">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="isbn" class="form-label">ISBN <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="isbn" name="isbn" 
                                   value="${book.isbn}" required>
                            <div class="invalid-feedback">
                                Please provide a valid ISBN.
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="title" class="form-label">Title <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="title" name="title" 
                                   value="${book.title}" required>
                            <div class="invalid-feedback">
                                Please provide a book title.
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="author" class="form-label">Author <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="author" name="author" 
                                   value="${book.author}" required>
                            <div class="invalid-feedback">
                                Please provide the author name.
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="publisher" class="form-label">Publisher <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="publisher" name="publisher" 
                                   value="${book.publisher}" required>
                            <div class="invalid-feedback">
                                Please provide the publisher name.
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="publicationDate" class="form-label">Publication Date <span class="text-danger">*</span></label>
                            <input type="date" class="form-control" id="publicationDate" name="publicationDate" 
                                   value="${book.publicationDate}" required>
                            <div class="invalid-feedback">
                                Please provide the publication date.
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="category" class="form-label">Category <span class="text-danger">*</span></label>
                            <select class="form-select" id="category" name="category" required>
                                <option value="">Select Category</option>
                                <option value="Fiction" ${book.category == 'Fiction' ? 'selected' : ''}>Fiction</option>
                                <option value="Non-Fiction" ${book.category == 'Non-Fiction' ? 'selected' : ''}>Non-Fiction</option>
                                <option value="Science" ${book.category == 'Science' ? 'selected' : ''}>Science</option>
                                <option value="Mathematics" ${book.category == 'Mathematics' ? 'selected' : ''}>Mathematics</option>
                                <option value="History" ${book.category == 'History' ? 'selected' : ''}>History</option>
                                <option value="Literature" ${book.category == 'Literature' ? 'selected' : ''}>Literature</option>
                                <option value="Reference" ${book.category == 'Reference' ? 'selected' : ''}>Reference</option>
                                <option value="Textbook" ${book.category == 'Textbook' ? 'selected' : ''}>Textbook</option>
                                <option value="Other" ${book.category == 'Other' ? 'selected' : ''}>Other</option>
                            </select>
                            <div class="invalid-feedback">
                                Please select a category.
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="totalCopies" class="form-label">Total Copies <span class="text-danger">*</span></label>
                            <input type="number" class="form-control" id="totalCopies" name="totalCopies" 
                                   value="${book.totalCopies}" min="1" required>
                            <div class="invalid-feedback">
                                Please provide the total number of copies.
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="availableCopies" class="form-label">Available Copies</label>
                            <input type="number" class="form-control" id="availableCopies" name="availableCopies" 
                                   value="${book.availableCopies != null ? book.availableCopies : book.totalCopies}" 
                                   min="0" readonly>
                            <div class="form-text">This will be set to the same as total copies for new books.</div>
                        </div>
                    </div>

                    <div class="d-flex justify-content-end">
                        <a href="/books" class="btn btn-secondary me-2">Cancel</a>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save me-2"></i>Add Book
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="col-lg-4">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Help</h5>
            </div>
            <div class="card-body">
                <h6>ISBN Format</h6>
                <p class="text-muted small">Enter the 13-digit ISBN number without hyphens or spaces.</p>
                
                <h6>Categories</h6>
                <p class="text-muted small">Choose the most appropriate category for the book to help with organization and searching.</p>
                
                <h6>Copies</h6>
                <p class="text-muted small">Total copies is the number of physical books you have. Available copies will be set automatically.</p>
            </div>
        </div>
    </div>
</div>

<script>
// Auto-set available copies when total copies changes
document.getElementById('totalCopies').addEventListener('input', function() {
    document.getElementById('availableCopies').value = this.value;
});

// Form validation
(function() {
    'use strict';
    window.addEventListener('load', function() {
        var forms = document.getElementsByClassName('needs-validation');
        var validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();
</script>
