<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="Edit Book" />
<c:set var="pageName" value="books" />

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="fas fa-edit me-2"></i>Edit Book</h2>
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
                <form action="/books/edit/${book.id}" method="post" class="needs-validation" novalidate="novalidate">
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
                            <label for="availableCopies" class="form-label">Available Copies <span class="text-danger">*</span></label>
                            <input type="number" class="form-control" id="availableCopies" name="availableCopies" 
                                   value="${book.availableCopies}" min="0" required>
                            <div class="form-text">Current available copies. Cannot exceed total copies.</div>
                        </div>
                    </div>

                    <div class="d-flex justify-content-end">
                        <a href="/books" class="btn btn-secondary me-2">Cancel</a>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save me-2"></i>Update Book
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="col-lg-4">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Book Details</h5>
            </div>
            <div class="card-body">
                <p><strong>Created:</strong> <fmt:formatDate value="${book.createdDate}" pattern="MMM dd, yyyy"/></p>
                <p><strong>Issued Copies:</strong> ${book.totalCopies - book.availableCopies}</p>
                <p><strong>Available:</strong> ${book.availableCopies}</p>
                <p><strong>Total:</strong> ${book.totalCopies}</p>
            </div>
        </div>

        <div class="card mt-3">
            <div class="card-header">
                <h5 class="mb-0">Help</h5>
            </div>
            <div class="card-body">
                <h6>Available Copies</h6>
                <p class="text-muted small">Make sure available copies doesn't exceed total copies. This represents how many books are currently available for issue.</p>
                
                <h6>Total Copies</h6>
                <p class="text-muted small">This is the total number of physical copies of this book in your library.</p>
            </div>
        </div>
    </div>
</div>

<script>
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

// Validate available copies doesn't exceed total copies
document.getElementById('totalCopies').addEventListener('input', function() {
    var totalCopies = parseInt(this.value);
    var availableCopies = document.getElementById('availableCopies');
    var availableValue = parseInt(availableCopies.value);
    
    if (availableValue > totalCopies) {
        availableCopies.setCustomValidity('Available copies cannot exceed total copies');
    } else {
        availableCopies.setCustomValidity('');
    }
});

document.getElementById('availableCopies').addEventListener('input', function() {
    var availableCopies = parseInt(this.value);
    var totalCopies = parseInt(document.getElementById('totalCopies').value);
    
    if (availableCopies > totalCopies) {
        this.setCustomValidity('Available copies cannot exceed total copies');
    } else {
        this.setCustomValidity('');
    }
});
</script>
