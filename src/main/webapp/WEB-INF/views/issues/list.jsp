<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="${title != null ? title : 'Book Issues Management'}" />
<c:set var="pageName" value="issues" />

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="fas fa-hand-holding me-2"></i>${title != null ? title : 'Book Issues Management'}</h2>
    <div>
        <a href="/issues/issue" class="btn btn-primary me-2">
            <i class="fas fa-plus me-2"></i>Issue Book
        </a>
        <a href="/issues/issue-by-student-id" class="btn btn-outline-primary">
            <i class="fas fa-user-plus me-2"></i>Issue by Student ID
        </a>
    </div>
</div>

<!-- Statistics -->
<div class="row mb-4">
    <div class="col-md-3">
        <div class="card text-center">
            <div class="card-body">
                <h5 class="card-title text-primary">${issuedCount}</h5>
                <p class="card-text">Issued Books</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card text-center">
            <div class="card-body">
                <h5 class="card-title text-success">${returnedCount}</h5>
                <p class="card-text">Returned Books</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card text-center">
            <div class="card-body">
                <h5 class="card-title text-warning">${overdueCount}</h5>
                <p class="card-text">Overdue Books</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card text-center">
            <div class="card-body">
                <h5 class="card-title text-info">${issues.size()}</h5>
                <p class="card-text">Total Issues</p>
            </div>
        </div>
    </div>
</div>

<!-- Filter Options -->
<div class="card mb-4">
    <div class="card-body">
        <div class="row g-3">
            <div class="col-md-3">
                <a href="/issues" class="btn btn-outline-primary w-100">All Issues</a>
            </div>
            <div class="col-md-3">
                <a href="/issues/active" class="btn btn-outline-success w-100">Active Issues</a>
            </div>
            <div class="col-md-3">
                <a href="/issues/overdue" class="btn btn-outline-warning w-100">Overdue Books</a>
            </div>
            <div class="col-md-3">
                <a href="/issues?status=returned" class="btn btn-outline-info w-100">Returned Books</a>
            </div>
        </div>
    </div>
</div>

<!-- Issues Table -->
<div class="card">
    <div class="card-body">
        <c:choose>
            <c:when test="${issues.isEmpty()}">
                <div class="text-center py-5">
                    <i class="fas fa-hand-holding fa-3x text-muted mb-3"></i>
                    <h5 class="text-muted">No book issues found</h5>
                    <p class="text-muted">Start by issuing a book to a student.</p>
                    <a href="/issues/issue" class="btn btn-primary">
                        <i class="fas fa-plus me-2"></i>Issue Book
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Issue ID</th>
                                <th>Book</th>
                                <th>Student</th>
                                <th>Issue Date</th>
                                <th>Due Date</th>
                                <th>Return Date</th>
                                <th>Status</th>
                                <th>Fine</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="issue" items="${issues}">
                                <tr>
                                    <td><code>#${issue.id}</code></td>
                                    <td>
                                        <strong>${issue.book.title}</strong>
                                        <br>
                                        <small class="text-muted">by ${issue.book.author}</small>
                                    </td>
                                    <td>
                                        <strong>${issue.student.fullName}</strong>
                                        <br>
                                        <small class="text-muted">ID: ${issue.student.studentId}</small>
                                    </td>
                                    <td><fmt:formatDate value="${issue.issueDate}" pattern="MMM dd, yyyy"/></td>
                                    <td>
                                        <fmt:formatDate value="${issue.dueDate}" pattern="MMM dd, yyyy"/>
                                        <c:if test="${issue.isOverdue()}">
                                            <br><small class="text-danger">Overdue by ${issue.getDaysOverdue()} days</small>
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${issue.returnDate != null}">
                                                <fmt:formatDate value="${issue.returnDate}" pattern="MMM dd, yyyy"/>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${issue.status == 'ISSUED'}">
                                                <span class="badge bg-success">Issued</span>
                                            </c:when>
                                            <c:when test="${issue.status == 'RETURNED'}">
                                                <span class="badge bg-info">Returned</span>
                                            </c:when>
                                            <c:when test="${issue.status == 'OVERDUE'}">
                                                <span class="badge bg-warning">Overdue</span>
                                            </c:when>
                                            <c:when test="${issue.status == 'LOST'}">
                                                <span class="badge bg-danger">Lost</span>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${issue.fineAmount > 0}">
                                                <span class="text-danger">₹<fmt:formatNumber value="${issue.fineAmount}" type="number" maxFractionDigits="2"/></span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <c:if test="${issue.status == 'ISSUED' || issue.status == 'OVERDUE'}">
                                                <a href="/issues/return/${issue.id}" class="btn btn-sm btn-outline-success" title="Return Book">
                                                    <i class="fas fa-undo"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${issue.status == 'ISSUED'}">
                                                <a href="/issues/mark-overdue/${issue.id}" 
                                                   class="btn btn-sm btn-outline-warning" 
                                                   title="Mark as Overdue"
                                                   onclick="return confirm('Mark this book as overdue?')">
                                                    <i class="fas fa-exclamation-triangle"></i>
                                                </a>
                                            </c:if>
                                            <c:if test="${issue.status == 'ISSUED' || issue.status == 'OVERDUE'}">
                                                <a href="/issues/mark-lost/${issue.id}" 
                                                   class="btn btn-sm btn-outline-danger" 
                                                   title="Mark as Lost"
                                                   onclick="return confirm('Mark this book as lost?')">
                                                    <i class="fas fa-times"></i>
                                                </a>
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
