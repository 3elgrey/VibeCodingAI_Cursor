<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="${title != null ? title : 'Book Returns Management'}" />
<c:set var="pageName" value="returns" />

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="fas fa-undo me-2"></i>${title != null ? title : 'Book Returns Management'}</h2>
    <a href="/returns/date-range" class="btn btn-outline-primary">
        <i class="fas fa-calendar me-2"></i>Date Range Report
    </a>
</div>

<!-- Statistics -->
<div class="row mb-4">
    <div class="col-md-3">
        <div class="card text-center">
            <div class="card-body">
                <h5 class="card-title text-success">₹<fmt:formatNumber value="${totalFineCollected}" type="number" maxFractionDigits="2"/></h5>
                <p class="card-text">Total Fine Collected</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card text-center">
            <div class="card-body">
                <h5 class="card-title text-warning">₹<fmt:formatNumber value="${totalUnpaidFines}" type="number" maxFractionDigits="2"/></h5>
                <p class="card-text">Unpaid Fines</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card text-center">
            <div class="card-body">
                <h5 class="card-title text-danger">${unpaidFinesCount}</h5>
                <p class="card-text">Unpaid Fine Records</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card text-center">
            <div class="card-body">
                <h5 class="card-title text-info">${returns.size()}</h5>
                <p class="card-text">Total Returns</p>
            </div>
        </div>
    </div>
</div>

<!-- Filter Options -->
<div class="card mb-4">
    <div class="card-body">
        <div class="row g-3">
            <div class="col-md-3">
                <a href="/returns" class="btn btn-outline-primary w-100">All Returns</a>
            </div>
            <div class="col-md-3">
                <a href="/returns/unpaid" class="btn btn-outline-warning w-100">Unpaid Fines</a>
            </div>
            <div class="col-md-3">
                <a href="/returns/paid" class="btn btn-outline-success w-100">Paid Fines</a>
            </div>
            <div class="col-md-3">
                <a href="/returns/date-range" class="btn btn-outline-info w-100">Date Range</a>
            </div>
        </div>
    </div>
</div>

<!-- Returns Table -->
<div class="card">
    <div class="card-body">
        <c:choose>
            <c:when test="${returns.isEmpty()}">
                <div class="text-center py-5">
                    <i class="fas fa-undo fa-3x text-muted mb-3"></i>
                    <h5 class="text-muted">No book returns found</h5>
                    <p class="text-muted">Book returns will appear here once students start returning books.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Return ID</th>
                                <th>Book</th>
                                <th>Student</th>
                                <th>Return Date</th>
                                <th>Condition</th>
                                <th>Fine Amount</th>
                                <th>Fine Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="return" items="${returns}">
                                <tr>
                                    <td><code>#${return.id}</code></td>
                                    <td>
                                        <strong>${return.bookIssue.book.title}</strong>
                                        <br>
                                        <small class="text-muted">by ${return.bookIssue.book.author}</small>
                                    </td>
                                    <td>
                                        <strong>${return.bookIssue.student.fullName}</strong>
                                        <br>
                                        <small class="text-muted">ID: ${return.bookIssue.student.studentId}</small>
                                    </td>
                                    <td><fmt:formatDate value="${return.returnDate}" pattern="MMM dd, yyyy"/></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${return.returnCondition == 'GOOD'}">
                                                <span class="badge bg-success">Good</span>
                                            </c:when>
                                            <c:when test="${return.returnCondition == 'DAMAGED'}">
                                                <span class="badge bg-warning">Damaged</span>
                                            </c:when>
                                            <c:when test="${return.returnCondition == 'VERY_DAMAGED'}">
                                                <span class="badge bg-danger">Very Damaged</span>
                                            </c:when>
                                            <c:when test="${return.returnCondition == 'LOST'}">
                                                <span class="badge bg-dark">Lost</span>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${return.fineAmount > 0}">
                                                <span class="text-danger">₹<fmt:formatNumber value="${return.fineAmount}" type="number" maxFractionDigits="2"/></span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${return.fineAmount > 0}">
                                                <c:choose>
                                                    <c:when test="${return.finePaid}">
                                                        <span class="badge bg-success">Paid</span>
                                                        <br><small class="text-muted">Paid on: <fmt:formatDate value="${return.finePaidDate}" pattern="MMM dd, yyyy"/></small>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-warning">Unpaid</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <c:if test="${return.fineAmount > 0 && !return.finePaid}">
                                                <a href="/returns/pay-fine/${return.id}" 
                                                   class="btn btn-sm btn-outline-success" 
                                                   title="Mark Fine as Paid"
                                                   onclick="return confirm('Mark this fine as paid?')">
                                                    <i class="fas fa-check"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${return.remarks != null && !return.remarks.isEmpty()}">
                                                <button type="button" class="btn btn-sm btn-outline-info" 
                                                        title="View Remarks" 
                                                        data-bs-toggle="tooltip" 
                                                        data-bs-placement="top" 
                                                        data-bs-content="${return.remarks}">
                                                    <i class="fas fa-comment"></i>
                                                </button>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<script>
// Initialize tooltips
var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl);
});
</script>
