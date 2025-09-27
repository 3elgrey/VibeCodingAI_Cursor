<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="row mb-4">
    <div class="col-xl-3 col-md-6 mb-4">
        <div class="card stat-card h-100">
            <div class="card-body">
                <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                        <div class="text-xs font-weight-bold text-uppercase mb-1">Total Books</div>
                        <div class="h5 mb-0 font-weight-bold">${totalBooks}</div>
                    </div>
                    <div class="col-auto">
                        <i class="fas fa-book fa-2x text-white-50"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-xl-3 col-md-6 mb-4">
        <div class="card stat-card h-100">
            <div class="card-body">
                <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                        <div class="text-xs font-weight-bold text-uppercase mb-1">Available Books</div>
                        <div class="h5 mb-0 font-weight-bold">${availableBooks}</div>
                    </div>
                    <div class="col-auto">
                        <i class="fas fa-check-circle fa-2x text-white-50"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-xl-3 col-md-6 mb-4">
        <div class="card stat-card h-100">
            <div class="card-body">
                <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                        <div class="text-xs font-weight-bold text-uppercase mb-1">Issued Books</div>
                        <div class="h5 mb-0 font-weight-bold">${issuedBooks}</div>
                    </div>
                    <div class="col-auto">
                        <i class="fas fa-hand-holding fa-2x text-white-50"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-xl-3 col-md-6 mb-4">
        <div class="card stat-card h-100">
            <div class="card-body">
                <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                        <div class="text-xs font-weight-bold text-uppercase mb-1">Overdue Books</div>
                        <div class="h5 mb-0 font-weight-bold text-warning">${overdueBooks}</div>
                    </div>
                    <div class="col-auto">
                        <i class="fas fa-exclamation-triangle fa-2x text-white-50"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row mb-4">
    <div class="col-xl-3 col-md-6 mb-4">
        <div class="card stat-card h-100">
            <div class="card-body">
                <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                        <div class="text-xs font-weight-bold text-uppercase mb-1">Total Students</div>
                        <div class="h5 mb-0 font-weight-bold">${totalStudents}</div>
                    </div>
                    <div class="col-auto">
                        <i class="fas fa-users fa-2x text-white-50"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-xl-3 col-md-6 mb-4">
        <div class="card stat-card h-100">
            <div class="card-body">
                <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                        <div class="text-xs font-weight-bold text-uppercase mb-1">Fine Collected</div>
                        <div class="h5 mb-0 font-weight-bold">₹<fmt:formatNumber value="${totalFineCollected}" type="number" maxFractionDigits="2"/></div>
                    </div>
                    <div class="col-auto">
                        <i class="fas fa-rupee-sign fa-2x text-white-50"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-xl-3 col-md-6 mb-4">
        <div class="card stat-card h-100">
            <div class="card-body">
                <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                        <div class="text-xs font-weight-bold text-uppercase mb-1">Unpaid Fines</div>
                        <div class="h5 mb-0 font-weight-bold text-warning">₹<fmt:formatNumber value="${unpaidFines}" type="number" maxFractionDigits="2"/></div>
                    </div>
                    <div class="col-auto">
                        <i class="fas fa-exclamation-circle fa-2x text-white-50"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-xl-3 col-md-6 mb-4">
        <div class="card stat-card h-100">
            <div class="card-body">
                <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                        <div class="text-xs font-weight-bold text-uppercase mb-1">Unavailable Books</div>
                        <div class="h5 mb-0 font-weight-bold">${unavailableBooks}</div>
                    </div>
                    <div class="col-auto">
                        <i class="fas fa-times-circle fa-2x text-white-50"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-lg-6">
        <div class="card">
            <div class="card-header">
                <h6 class="m-0 font-weight-bold text-primary">Quick Actions</h6>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <a href="/books/add" class="btn btn-primary w-100">
                            <i class="fas fa-plus me-2"></i>Add New Book
                        </a>
                    </div>
                    <div class="col-md-6 mb-3">
                        <a href="/students/add" class="btn btn-primary w-100">
                            <i class="fas fa-user-plus me-2"></i>Add New Student
                        </a>
                    </div>
                    <div class="col-md-6 mb-3">
                        <a href="/issues/issue" class="btn btn-success w-100">
                            <i class="fas fa-hand-holding me-2"></i>Issue Book
                        </a>
                    </div>
                    <div class="col-md-6 mb-3">
                        <a href="/issues/overdue" class="btn btn-warning w-100">
                            <i class="fas fa-exclamation-triangle me-2"></i>View Overdue
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="col-lg-6">
        <div class="card">
            <div class="card-header">
                <h6 class="m-0 font-weight-bold text-primary">Recent Activity</h6>
            </div>
            <div class="card-body">
                <div class="text-center text-muted">
                    <i class="fas fa-info-circle fa-2x mb-3"></i>
                    <p>Recent activity will be displayed here once the system is in use.</p>
                </div>
            </div>
        </div>
    </div>
</div>
