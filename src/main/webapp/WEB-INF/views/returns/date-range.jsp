<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="Date Range Report" />
<c:set var="pageName" value="returns" />

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="fas fa-calendar me-2"></i>Date Range Report</h2>
    <a href="/returns" class="btn btn-outline-secondary">
        <i class="fas fa-arrow-left me-2"></i>Back to Returns
    </a>
</div>

<div class="row">
    <div class="col-lg-4">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Select Date Range</h5>
            </div>
            <div class="card-body">
                <form action="/returns/date-range" method="post" class="needs-validation" novalidate="novalidate">
                    <div class="mb-3">
                        <label for="startDate" class="form-label">Start Date <span class="text-danger">*</span></label>
                        <input type="date" class="form-control" id="startDate" name="startDate" 
                               value="${startDate}" required>
                        <div class="invalid-feedback">
                            Please select a start date.
                        </div>
                    </div>
                    
                    <div class="mb-3">
                        <label for="endDate" class="form-label">End Date <span class="text-danger">*</span></label>
                        <input type="date" class="form-control" id="endDate" name="endDate" 
                               value="${endDate}" required>
                        <div class="invalid-feedback">
                            Please select an end date.
                        </div>
                    </div>
                    
                    <button type="submit" class="btn btn-primary w-100">
                        <i class="fas fa-search me-2"></i>Generate Report
                    </button>
                </form>
            </div>
        </div>

        <div class="card mt-3">
            <div class="card-header">
                <h5 class="mb-0">Quick Reports</h5>
            </div>
            <div class="card-body">
                <div class="d-grid gap-2">
                    <a href="/returns/date-range?startDate=${today}&endDate=${today}" class="btn btn-outline-primary btn-sm">
                        Today's Returns
                    </a>
                    <a href="/returns/date-range?startDate=${thisWeekStart}&endDate=${thisWeekEnd}" class="btn btn-outline-primary btn-sm">
                        This Week
                    </a>
                    <a href="/returns/date-range?startDate=${thisMonthStart}&endDate=${thisMonthEnd}" class="btn btn-outline-primary btn-sm">
                        This Month
                    </a>
                    <a href="/returns/date-range?startDate=${lastMonthStart}&endDate=${lastMonthEnd}" class="btn btn-outline-primary btn-sm">
                        Last Month
                    </a>
                </div>
            </div>
        </div>
    </div>

    <div class="col-lg-8">
        <c:if test="${returns != null}">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">
                        Returns Report
                        <c:if test="${startDate != null && endDate != null}">
                            <small class="text-muted">(${startDate} to ${endDate})</small>
                        </c:if>
                    </h5>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${returns.isEmpty()}">
                            <div class="text-center py-4">
                                <i class="fas fa-chart-line fa-2x text-muted mb-3"></i>
                                <h6 class="text-muted">No returns found for the selected date range</h6>
                                <p class="text-muted small">Try selecting a different date range.</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <!-- Summary Statistics -->
                            <div class="row mb-4">
                                <div class="col-md-3">
                                    <div class="text-center">
                                        <h4 class="text-primary">${returns.size()}</h4>
                                        <small class="text-muted">Total Returns</small>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="text-center">
                                        <h4 class="text-success">
                                            <c:set var="goodReturns" value="0" />
                                            <c:forEach var="return" items="${returns}">
                                                <c:if test="${return.returnCondition == 'GOOD'}">
                                                    <c:set var="goodReturns" value="${goodReturns + 1}" />
                                                </c:if>
                                            </c:forEach>
                                            ${goodReturns}
                                        </h4>
                                        <small class="text-muted">Good Condition</small>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="text-center">
                                        <h4 class="text-warning">
                                            <c:set var="damagedReturns" value="0" />
                                            <c:forEach var="return" items="${returns}">
                                                <c:if test="${return.returnCondition == 'DAMAGED' || return.returnCondition == 'VERY_DAMAGED'}">
                                                    <c:set var="damagedReturns" value="${damagedReturns + 1}" />
                                                </c:if>
                                            </c:forEach>
                                            ${damagedReturns}
                                        </h4>
                                        <small class="text-muted">Damaged</small>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="text-center">
                                        <h4 class="text-danger">
                                            <c:set var="totalFines" value="0" />
                                            <c:forEach var="return" items="${returns}">
                                                <c:set var="totalFines" value="${totalFines + return.fineAmount}" />
                                            </c:forEach>
                                            ₹<fmt:formatNumber value="${totalFines}" type="number" maxFractionDigits="2"/>
                                        </h4>
                                        <small class="text-muted">Total Fines</small>
                                    </div>
                                </div>
                            </div>

                            <!-- Returns Table -->
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                        <tr>
                                            <th>Return Date</th>
                                            <th>Book</th>
                                            <th>Student</th>
                                            <th>Condition</th>
                                            <th>Fine</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="return" items="${returns}">
                                            <tr>
                                                <td><fmt:formatDate value="${return.returnDate}" pattern="MMM dd, yyyy"/></td>
                                                <td>
                                                    <strong>${return.bookIssue.book.title}</strong>
                                                    <br><small class="text-muted">by ${return.bookIssue.book.author}</small>
                                                </td>
                                                <td>
                                                    <strong>${return.bookIssue.student.fullName}</strong>
                                                    <br><small class="text-muted">${return.bookIssue.student.studentId}</small>
                                                </td>
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
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:if>
    </div>
</div>

<script>
// Set default dates
document.addEventListener('DOMContentLoaded', function() {
    var today = new Date();
    var startDate = document.getElementById('startDate');
    var endDate = document.getElementById('endDate');
    
    if (!startDate.value) {
        startDate.value = today.toISOString().split('T')[0];
    }
    if (!endDate.value) {
        endDate.value = today.toISOString().split('T')[0];
    }
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
