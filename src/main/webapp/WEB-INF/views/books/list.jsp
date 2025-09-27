<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="${title != null ? title : 'Books Management'}" />
<c:set var="pageName" value="books" />

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="fas fa-book me-2"></i>${title != null ? title : 'Books Management'}</h2>
    <a href="/books/add" class="btn btn-primary">
        <i class="fas fa-plus me-2"></i>Add New Book
    </a>
</div>

<!-- Search and Filter -->
<div class="card mb-4">
    <div class="card-body">
        <form method="get" action="/books" class="row g-3">
            <div class="col-md-8">
                <input type="text" class="form-control search-box" name="search" 
                       placeholder="Search books by title, author, ISBN..." 
                       value="${search}">
            </div>
            <div class="col-md-4">
                <button type="submit" class="btn btn-outline-primary me-2">
                    <i class="fas fa-search me-2"></i>Search
                </button>
                <a href="/books" class="btn btn-outline-secondary">
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
                <h5 class="card-title text-primary">${availableCount}</h5>
                <p class="card-text">Available Books</p>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card text-center">
            <div class="card-body">
                <h5 class="card-title text-warning">${unavailableCount}</h5>
                <p class="card-text">Unavailable Books</p>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card text-center">
            <div class="card-body">
                <h5 class="card-title text-info">${books.size()}</h5>
                <p class="card-text">Total Books</p>
            </div>
        </div>
    </div>
</div>

<!-- Books Table -->
<div class="card">
    <div class="card-body">
        <c:choose>
            <c:when test="${books.isEmpty()}">
                <div class="text-center py-5">
                    <i class="fas fa-book fa-3x text-muted mb-3"></i>
                    <h5 class="text-muted">No books found</h5>
                    <p class="text-muted">
                        <c:choose>
                            <c:when test="${search != null}">
                                No books match your search criteria.
                            </c:when>
                            <c:otherwise>
                                Start by adding your first book to the library.
                            </c:otherwise>
                        </c:choose>
                    </p>
                    <a href="/books/add" class="btn btn-primary">
                        <i class="fas fa-plus me-2"></i>Add New Book
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>ISBN</th>
                                <th>Title</th>
                                <th>Author</th>
                                <th>Publisher</th>
                                <th>Category</th>
                                <th>Available</th>
                                <th>Total</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="book" items="${books}">
                                <tr>
                                    <td><code>${book.isbn}</code></td>
                                    <td>
                                        <strong>${book.title}</strong>
                                        <br>
                                        <small class="text-muted">
                                            Published: <fmt:formatDate value="${book.publicationDate}" pattern="yyyy"/>
                                        </small>
                                    </td>
                                    <td>${book.author}</td>
                                    <td>${book.publisher}</td>
                                    <td>
                                        <span class="badge bg-secondary">${book.category}</span>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${book.availableCopies > 0}">
                                                <span class="badge bg-success">${book.availableCopies}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-danger">0</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${book.totalCopies}</td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <a href="/books/edit/${book.id}" class="btn btn-sm btn-outline-primary" title="Edit">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="/books/delete/${book.id}" 
                                               class="btn btn-sm btn-outline-danger" 
                                               title="Delete"
                                               onclick="return confirm('Are you sure you want to delete this book?')">
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
