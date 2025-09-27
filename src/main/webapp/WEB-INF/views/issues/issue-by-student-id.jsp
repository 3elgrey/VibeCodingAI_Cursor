<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="Issue Book by Student ID" />
<c:set var="pageName" value="issues" />

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="fas fa-user-plus me-2"></i>Issue Book by Student ID</h2>
    <a href="/issues" class="btn btn-outline-secondary">
        <i class="fas fa-arrow-left me-2"></i>Back to Issues
    </a>
</div>

<div class="row">
    <div class="col-lg-8">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Issue Book by Student ID</h5>
            </div>
            <div class="card-body">
                <form action="/issues/issue-by-student-id" method="post" class="needs-validation" novalidate="novalidate">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="bookId" class="form-label">Select Book <span class="text-danger">*</span></label>
                            <select class="form-select" id="bookId" name="bookId" required>
                                <option value="">Choose a book...</option>
                                <c:forEach var="book" items="${availableBooks}">
                                    <option value="${book.id}">
                                        ${book.title} by ${book.author} (Available: ${book.availableCopies})
                                    </option>
                                </c:forEach>
                            </select>
                            <div class="invalid-feedback">
                                Please select a book.
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="studentId" class="form-label">Student ID <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="studentId" name="studentId" 
                                   placeholder="Enter student ID" required>
                            <div class="invalid-feedback">
                                Please enter a student ID.
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="daysToReturn" class="form-label">Days to Return <span class="text-danger">*</span></label>
                            <select class="form-select" id="daysToReturn" name="daysToReturn" required>
                                <option value="7">7 days</option>
                                <option value="14" selected>14 days (Default)</option>
                                <option value="21">21 days</option>
                                <option value="30">30 days</option>
                            </select>
                            <div class="invalid-feedback">
                                Please select the return period.
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Due Date</label>
                            <input type="text" class="form-control" id="dueDate" readonly>
                            <div class="form-text">This will be calculated automatically.</div>
                        </div>
                    </div>

                    <div class="d-flex justify-content-end">
                        <a href="/issues" class="btn btn-secondary me-2">Cancel</a>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-hand-holding me-2"></i>Issue Book
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="col-lg-4">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Available Books</h5>
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${availableBooks.isEmpty()}">
                        <p class="text-muted">No books available for issue.</p>
                    </c:when>
                    <c:otherwise>
                        <div class="list-group list-group-flush">
                            <c:forEach var="book" items="${availableBooks}" end="4">
                                <div class="list-group-item px-0">
                                    <h6 class="mb-1">${book.title}</h6>
                                    <p class="mb-1 text-muted small">by ${book.author}</p>
                                    <small class="text-success">Available: ${book.availableCopies}</small>
                                </div>
                            </c:forEach>
                            <c:if test="${availableBooks.size() > 5}">
                                <div class="list-group-item px-0 text-center">
                                    <small class="text-muted">... and ${availableBooks.size() - 5} more books</small>
                                </div>
                            </c:if>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="card mt-3">
            <div class="card-header">
                <h5 class="mb-0">Help</h5>
            </div>
            <div class="card-body">
                <h6>Student ID</h6>
                <p class="text-muted small">Enter the exact student ID as registered in the system. The system will validate the student exists and is active.</p>
                
                <h6>Book Selection</h6>
                <p class="text-muted small">Only books with available copies are shown in the dropdown.</p>
                
                <h6>Return Period</h6>
                <p class="text-muted small">Choose how many days the student has to return the book. Default is 14 days.</p>
            </div>
        </div>
    </div>
</div>

<script>
// Calculate due date when days to return changes
document.getElementById('daysToReturn').addEventListener('change', function() {
    var days = parseInt(this.value);
    var today = new Date();
    var dueDate = new Date(today.getTime() + (days * 24 * 60 * 60 * 1000));
    
    var options = { year: 'numeric', month: 'short', day: 'numeric' };
    document.getElementById('dueDate').value = dueDate.toLocaleDateString('en-US', options);
});

// Set initial due date
document.getElementById('daysToReturn').dispatchEvent(new Event('change'));

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
