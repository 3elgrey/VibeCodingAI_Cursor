<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="Return Book" />
<c:set var="pageName" value="issues" />

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="fas fa-undo me-2"></i>Return Book</h2>
    <a href="/issues" class="btn btn-outline-secondary">
        <i class="fas fa-arrow-left me-2"></i>Back to Issues
    </a>
</div>

<div class="row">
    <div class="col-lg-8">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Book Return Details</h5>
            </div>
            <div class="card-body">
                <!-- Issue Information -->
                <div class="row mb-4">
                    <div class="col-md-6">
                        <h6>Book Information</h6>
                        <p><strong>Title:</strong> ${issue.book.title}</p>
                        <p><strong>Author:</strong> ${issue.book.author}</p>
                        <p><strong>ISBN:</strong> <code>${issue.book.isbn}</code></p>
                    </div>
                    <div class="col-md-6">
                        <h6>Student Information</h6>
                        <p><strong>Name:</strong> ${issue.student.fullName}</p>
                        <p><strong>Student ID:</strong> <code>${issue.student.studentId}</code></p>
                        <p><strong>Grade:</strong> ${issue.student.grade}${issue.student.section}</p>
                    </div>
                </div>

                <div class="row mb-4">
                    <div class="col-md-6">
                        <h6>Issue Details</h6>
                        <p><strong>Issue Date:</strong> <fmt:formatDate value="${issue.issueDate}" pattern="MMM dd, yyyy"/></p>
                        <p><strong>Due Date:</strong> <fmt:formatDate value="${issue.dueDate}" pattern="MMM dd, yyyy"/></p>
                        <c:if test="${issue.isOverdue()}">
                            <p class="text-danger"><strong>Overdue by:</strong> ${issue.getDaysOverdue()} days</p>
                        </c:if>
                    </div>
                    <div class="col-md-6">
                        <h6>Fine Information</h6>
                        <c:choose>
                            <c:when test="${fineAmount > 0}">
                                <p class="text-danger"><strong>Fine Amount:</strong> ₹<fmt:formatNumber value="${fineAmount}" type="number" maxFractionDigits="2"/></p>
                                <p class="text-muted small">Fine calculated at ₹5 per day for overdue books.</p>
                            </c:when>
                            <c:otherwise>
                                <p class="text-success"><strong>No Fine</strong></p>
                                <p class="text-muted small">Book is being returned on time.</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <!-- Return Form -->
                <form action="/returns/return" method="post" class="needs-validation" novalidate="novalidate">
                    <input type="hidden" name="issueId" value="${issue.id}">
                    
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="condition" class="form-label">Book Condition <span class="text-danger">*</span></label>
                            <select class="form-select" id="condition" name="condition" required>
                                <option value="">Select condition...</option>
                                <option value="GOOD">Good - No damage</option>
                                <option value="DAMAGED">Damaged - Minor wear</option>
                                <option value="VERY_DAMAGED">Very Damaged - Significant damage</option>
                                <option value="LOST">Lost - Book not returned</option>
                            </select>
                            <div class="invalid-feedback">
                                Please select the book condition.
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="remarks" class="form-label">Remarks</label>
                            <textarea class="form-control" id="remarks" name="remarks" rows="3" 
                                      placeholder="Any additional notes about the return..."></textarea>
                        </div>
                    </div>

                    <c:if test="${fineAmount > 0}">
                        <div class="alert alert-warning">
                            <i class="fas fa-exclamation-triangle me-2"></i>
                            <strong>Fine Notice:</strong> This book has a fine of ₹<fmt:formatNumber value="${fineAmount}" type="number" maxFractionDigits="2"/>. 
                            The fine can be paid later from the returns management section.
                        </div>
                    </c:if>

                    <div class="d-flex justify-content-end">
                        <a href="/issues" class="btn btn-secondary me-2">Cancel</a>
                        <button type="submit" class="btn btn-success">
                            <i class="fas fa-undo me-2"></i>Return Book
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="col-lg-4">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Return Guidelines</h5>
            </div>
            <div class="card-body">
                <h6>Book Condition</h6>
                <ul class="list-unstyled small">
                    <li><strong>Good:</strong> No visible damage, ready for reissue</li>
                    <li><strong>Damaged:</strong> Minor wear, still usable</li>
                    <li><strong>Very Damaged:</strong> Significant damage, needs repair</li>
                    <li><strong>Lost:</strong> Book not physically returned</li>
                </ul>

                <h6>Fine Policy</h6>
                <ul class="list-unstyled small">
                    <li>₹5 per day for overdue books</li>
                    <li>Maximum fine: ₹500</li>
                    <li>Fines can be paid later</li>
                    <li>Lost books may have additional charges</li>
                </ul>
            </div>
        </div>

        <div class="card mt-3">
            <div class="card-header">
                <h5 class="mb-0">Student's Active Issues</h5>
            </div>
            <div class="card-body">
                <p class="text-muted small">This student may have other active book issues. Check the issues list for complete details.</p>
                <a href="/issues/student/${issue.student.studentId}" class="btn btn-sm btn-outline-primary">
                    View All Issues
                </a>
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
</script>
