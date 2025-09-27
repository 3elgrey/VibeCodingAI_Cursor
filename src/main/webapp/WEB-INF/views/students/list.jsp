<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="${title != null ? title : 'Students Management'}" />
<c:set var="pageName" value="students" />

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="fas fa-users me-2"></i>${title != null ? title : 'Students Management'}</h2>
    <a href="/students/add" class="btn btn-primary">
        <i class="fas fa-plus me-2"></i>Add New Student
    </a>
</div>

<!-- Search and Filter -->
<div class="card mb-4">
    <div class="card-body">
        <form method="get" action="/students" class="row g-3">
            <div class="col-md-8">
                <input type="text" class="form-control search-box" name="search" 
                       placeholder="Search students by name, ID, email..." 
                       value="${search}">
            </div>
            <div class="col-md-4">
                <button type="submit" class="btn btn-outline-primary me-2">
                    <i class="fas fa-search me-2"></i>Search
                </button>
                <a href="/students" class="btn btn-outline-secondary">
                    <i class="fas fa-times me-2"></i>Clear
                </a>
            </div>
        </form>
    </div>
</div>

<!-- Statistics -->
<div class="row mb-4">
    <div class="col-md-4">
        <div class="card text-center">
            <div class="card-body">
                <h5 class="card-title text-success">${activeCount}</h5>
                <p class="card-text">Active Students</p>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card text-center">
            <div class="card-body">
                <h5 class="card-title text-info">${students.size()}</h5>
                <p class="card-text">Total Students</p>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card text-center">
            <div class="card-body">
                <h5 class="card-title text-warning">${students.size() - activeCount}</h5>
                <p class="card-text">Inactive Students</p>
            </div>
        </div>
    </div>
</div>

<!-- Students Table -->
<div class="card">
    <div class="card-body">
        <c:choose>
            <c:when test="${students.isEmpty()}">
                <div class="text-center py-5">
                    <i class="fas fa-users fa-3x text-muted mb-3"></i>
                    <h5 class="text-muted">No students found</h5>
                    <p class="text-muted">
                        <c:choose>
                            <c:when test="${search != null}">
                                No students match your search criteria.
                            </c:when>
                            <c:otherwise>
                                Start by adding your first student to the system.
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <a href="/students/add" class="btn btn-primary">
                        <i class="fas fa-plus me-2"></i>Add New Student
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Student ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Grade</th>
                                <th>Section</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="student" items="${students}">
                                <tr>
                                    <td><code>${student.studentId}</code></td>
                                    <td>
                                        <strong>${student.fullName}</strong>
                                        <br>
                                        <small class="text-muted">
                                            Enrolled: <fmt:formatDate value="${student.enrollmentDate}" pattern="MMM dd, yyyy"/>
                                        </small>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${student.email != null && !student.email.isEmpty()}">
                                                <a href="mailto:${student.email}">${student.email}</a>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${student.phone != null && !student.phone.isEmpty()}">
                                                ${student.phone}
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">-</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><span class="badge bg-info">${student.grade}</span></td>
                                    <td><span class="badge bg-secondary">${student.section}</span></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${student.isActive}">
                                                <span class="badge bg-success">Active</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-danger">Inactive</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <a href="/students/edit/${student.id}" class="btn btn-sm btn-outline-primary" title="Edit">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <c:choose>
                                                <c:when test="${student.isActive}">
                                                    <a href="/students/deactivate/${student.id}" 
                                                       class="btn btn-sm btn-outline-warning" 
                                                       title="Deactivate"
                                                       onclick="return confirm('Are you sure you want to deactivate this student?')">
                                                        <i class="fas fa-user-times"></i>
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="/students/activate/${student.id}" 
                                                       class="btn btn-sm btn-outline-success" 
                                                       title="Activate"
                                                       onclick="return confirm('Are you sure you want to activate this student?')">
                                                        <i class="fas fa-user-check"></i>
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>
                                            <a href="/students/delete/${student.id}" 
                                               class="btn btn-sm btn-outline-danger" 
                                               title="Delete"
                                               onclick="return confirm('Are you sure you want to delete this student?')">
                                                <i class="fas fa-trash"></i>
                                            </a>
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
