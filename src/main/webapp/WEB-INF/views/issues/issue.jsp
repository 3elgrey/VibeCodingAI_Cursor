<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="Issue Book" />
<c:set var="pageName" value="issues" />

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="fas fa-hand-holding me-2"></i>Issue Book</h2>
    <a href="/issues" class="btn btn-outline-secondary">
        <i class="fas fa-arrow-left me-2"></i>Back to Issues
    </a>
</div>

<div class="row">
    <div class="col-lg-8">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Issue Book to Student</h5>
            </div>
            <div class="card-body">
                <form action="/issues/issue" method="post" class="needs-validation" novalidate="novalidate">
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
                            <label for="studentId" class="form-label">Select Student <span class="text-danger">*</span></label>
                            <select class="form-select" id="studentId" name="studentId" required>
                                <option value="">Choose a student...</option>
                                <c:forEach var="student" items="${activeStudents}">
                                    <option value="${student.id}">
                                        ${student.fullName} (${student.studentId}) - Grade ${student.grade}${student.section}
                                    </option>
                                </c:forEach>
                            </select>
                            <div class="invalid-feedback">
                                Please select a student.
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
                <h5 class="mb-0">Active Students</h5>
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${activeStudents.isEmpty()}">
                        <p class="text-muted">No active students found.</p>
                    </c:when>
                    <c:otherwise>
                        <div class="list-group list-group-flush">
                            <c:forEach var="student" items="${activeStudents}" end="4">
                                <div class="list-group-item px-0">
                                    <h6 class="mb-1">${student.fullName}</h6>
                                    <p class="mb-1 text-muted small">ID: ${student.studentId}</p>
                                    <small class="text-info">Grade ${student.grade}${student.section}</small>
                                </div>
                            </c:forEach>
                            <c:if test="${activeStudents.size() > 5}">
                                <div class="list-group-item px-0 text-center">
                                    <small class="text-muted">... and ${activeStudents.size() - 5} more students</small>
                                </div>
                            </c:if>
                        </div>
                    </c:otherwise>
                </c:choose>
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
