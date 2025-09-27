<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="Add New Student" />
<c:set var="pageName" value="students" />

<div class="d-flex justify-content-between align-items-center mb-4">
    <h2><i class="fas fa-user-plus me-2"></i>Add New Student</h2>
    <a href="/students" class="btn btn-outline-secondary">
        <i class="fas fa-arrow-left me-2"></i>Back to Students
    </a>
</div>

<div class="row">
    <div class="col-lg-8">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Student Information</h5>
            </div>
            <div class="card-body">
                <form action="/students/add" method="post" class="needs-validation" novalidate="novalidate">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="studentId" class="form-label">Student ID <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="studentId" name="studentId" 
                                   value="${student.studentId}" required>
                            <div class="invalid-feedback">
                                Please provide a student ID.
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email" 
                                   value="${student.email}">
                            <div class="invalid-feedback">
                                Please provide a valid email address.
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="firstName" class="form-label">First Name <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="firstName" name="firstName" 
                                   value="${student.firstName}" required>
                            <div class="invalid-feedback">
                                Please provide the first name.
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="lastName" class="form-label">Last Name <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" id="lastName" name="lastName" 
                                   value="${student.lastName}" required>
                            <div class="invalid-feedback">
                                Please provide the last name.
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="phone" class="form-label">Phone Number</label>
                            <input type="tel" class="form-control" id="phone" name="phone" 
                                   value="${student.phone}" pattern="[0-9]{10}">
                            <div class="invalid-feedback">
                                Please provide a valid 10-digit phone number.
                            </div>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="grade" class="form-label">Grade/Class <span class="text-danger">*</span></label>
                            <select class="form-select" id="grade" name="grade" required>
                                <option value="">Select Grade</option>
                                <option value="1" ${student.grade == '1' ? 'selected' : ''}>Grade 1</option>
                                <option value="2" ${student.grade == '2' ? 'selected' : ''}>Grade 2</option>
                                <option value="3" ${student.grade == '3' ? 'selected' : ''}>Grade 3</option>
                                <option value="4" ${student.grade == '4' ? 'selected' : ''}>Grade 4</option>
                                <option value="5" ${student.grade == '5' ? 'selected' : ''}>Grade 5</option>
                                <option value="6" ${student.grade == '6' ? 'selected' : ''}>Grade 6</option>
                                <option value="7" ${student.grade == '7' ? 'selected' : ''}>Grade 7</option>
                                <option value="8" ${student.grade == '8' ? 'selected' : ''}>Grade 8</option>
                                <option value="9" ${student.grade == '9' ? 'selected' : ''}>Grade 9</option>
                                <option value="10" ${student.grade == '10' ? 'selected' : ''}>Grade 10</option>
                                <option value="11" ${student.grade == '11' ? 'selected' : ''}>Grade 11</option>
                                <option value="12" ${student.grade == '12' ? 'selected' : ''}>Grade 12</option>
                            </select>
                            <div class="invalid-feedback">
                                Please select a grade.
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="section" class="form-label">Section <span class="text-danger">*</span></label>
                            <select class="form-select" id="section" name="section" required>
                                <option value="">Select Section</option>
                                <option value="A" ${student.section == 'A' ? 'selected' : ''}>Section A</option>
                                <option value="B" ${student.section == 'B' ? 'selected' : ''}>Section B</option>
                                <option value="C" ${student.section == 'C' ? 'selected' : ''}>Section C</option>
                                <option value="D" ${student.section == 'D' ? 'selected' : ''}>Section D</option>
                                <option value="E" ${student.section == 'E' ? 'selected' : ''}>Section E</option>
                            </select>
                            <div class="invalid-feedback">
                                Please select a section.
                            </div>
                        </div>
                    </div>

                    <div class="d-flex justify-content-end">
                        <a href="/students" class="btn btn-secondary me-2">Cancel</a>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save me-2"></i>Add Student
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="col-lg-4">
        <div class="card">
            <div class="card-header">
                <h5 class="mb-0">Help</h5>
            </div>
            <div class="card-body">
                <h6>Student ID</h6>
                <p class="text-muted small">Enter a unique identifier for the student. This will be used for book issuing.</p>
                
                <h6>Phone Number</h6>
                <p class="text-muted small">Enter a 10-digit phone number without spaces or special characters.</p>
                
                <h6>Grade and Section</h6>
                <p class="text-muted small">Select the appropriate grade and section for the student.</p>
                
                <h6>Email</h6>
                <p class="text-muted small">Email is optional but recommended for notifications and communication.</p>
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
