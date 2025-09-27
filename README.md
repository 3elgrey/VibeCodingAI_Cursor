# School Library Management System

A comprehensive library management system built with Spring Boot, JSP, and Hibernate for managing books, students, book issues, and returns in a school environment.

## Features

### 📚 Book Management
- Add, edit, delete books
- Search books by title, author, ISBN
- Track available and total copies
- Categorize books by subject
- ISBN validation and duplicate checking

### 👥 Student Management
- Register and manage student information
- Student ID and email validation
- Grade and section organization
- Activate/deactivate student accounts
- Search students by various criteria

### 📖 Book Issue Management
- Issue books to students
- Track issue and due dates
- Automatic overdue detection
- Fine calculation (₹5 per day)
- Issue by student ID or selection

### 🔄 Book Return Management
- Return books with condition tracking
- Automatic fine calculation
- Fine payment tracking
- Return date range reports
- Book condition assessment (Good, Damaged, Very Damaged, Lost)

### 📊 Dashboard & Reports
- Real-time statistics
- Overdue book tracking
- Fine collection reports
- Date range filtering
- Quick action buttons

## Technology Stack

- **Backend**: Spring Boot 3.2.0
- **Database**: MySQL 8.0 / H2 (for testing)
- **ORM**: Hibernate/JPA
- **Frontend**: JSP with Bootstrap 5
- **Build Tool**: Maven
- **Java Version**: 17

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher (optional, can use H2 for testing)

## Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd school-library-management
```

### 2. Database Setup

#### Option A: MySQL (Recommended for Production)
1. Create a MySQL database:
```sql
CREATE DATABASE school_library;
```

2. Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/school_library?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

#### Option B: H2 Database (For Testing)
The application is configured to use H2 database by default. No additional setup required.

### 3. Build and Run
```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### 4. Access the Application
Open your browser and navigate to:
```
http://localhost:8080
```

## Default Configuration

- **Server Port**: 8080
- **Database**: H2 (in-memory) by default
- **Fine Rate**: ₹5 per day for overdue books
- **Maximum Fine**: ₹500
- **Default Issue Period**: 14 days

## Usage Guide

### 1. Initial Setup
1. Start the application
2. Add books to the library
3. Register students
4. Begin issuing books

### 2. Book Management
- Navigate to "Books" section
- Click "Add New Book" to add books
- Use search functionality to find specific books
- Edit book details as needed

### 3. Student Management
- Go to "Students" section
- Register new students with their details
- Manage student status (active/inactive)
- Search students by various criteria

### 4. Book Issuing
- Navigate to "Book Issues" section
- Click "Issue Book" to issue books to students
- Select book and student from dropdowns
- Set return period (default: 14 days)

### 5. Book Returns
- Go to "Book Returns" section
- Process book returns with condition assessment
- Handle fine payments
- Generate reports for specific date ranges

## API Endpoints

### Books
- `GET /books` - List all books
- `GET /books/add` - Show add book form
- `POST /books/add` - Add new book
- `GET /books/edit/{id}` - Show edit book form
- `POST /books/edit/{id}` - Update book
- `GET /books/delete/{id}` - Delete book

### Students
- `GET /students` - List all students
- `GET /students/add` - Show add student form
- `POST /students/add` - Add new student
- `GET /students/edit/{id}` - Show edit student form
- `POST /students/edit/{id}` - Update student
- `GET /students/delete/{id}` - Delete student

### Book Issues
- `GET /issues` - List all issues
- `GET /issues/issue` - Show issue form
- `POST /issues/issue` - Issue book
- `GET /issues/return/{id}` - Show return form
- `POST /returns/return` - Return book

### Book Returns
- `GET /returns` - List all returns
- `GET /returns/pay-fine/{id}` - Pay fine
- `GET /returns/date-range` - Date range report

## Database Schema

### Books Table
- `id` (Primary Key)
- `isbn` (Unique)
- `title`
- `author`
- `publisher`
- `publication_date`
- `category`
- `total_copies`
- `available_copies`
- `created_date`

### Students Table
- `id` (Primary Key)
- `student_id` (Unique)
- `first_name`
- `last_name`
- `email` (Unique)
- `phone`
- `grade`
- `section`
- `enrollment_date`
- `is_active`

### Book Issues Table
- `id` (Primary Key)
- `book_id` (Foreign Key)
- `student_id` (Foreign Key)
- `issue_date`
- `due_date`
- `return_date`
- `fine_amount`
- `status` (ISSUED, RETURNED, OVERDUE, LOST)
- `created_at`
- `updated_at`

### Book Returns Table
- `id` (Primary Key)
- `book_issue_id` (Foreign Key)
- `return_date`
- `fine_amount`
- `fine_paid`
- `fine_paid_date`
- `remarks`
- `return_condition` (GOOD, DAMAGED, VERY_DAMAGED, LOST)
- `created_at`

## Fine Calculation

- **Rate**: ₹5 per day for overdue books
- **Maximum Fine**: ₹500
- **Calculation**: `min(days_overdue * 5, 500)`
- **Payment**: Can be paid later through the returns management section

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For support and questions, please create an issue in the repository.

## Future Enhancements

- [ ] Email notifications for overdue books
- [ ] Barcode scanning for books
- [ ] Advanced reporting and analytics
- [ ] Multi-library support
- [ ] Mobile responsive design improvements
- [ ] Integration with school management systems
- [ ] Automated fine reminders
- [ ] Book reservation system
- [ ] Digital book support
- [ ] User role management
