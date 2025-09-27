# End-to-End (E2E) Tests for School Library Management System

This directory contains comprehensive E2E tests using Playwright framework for testing the School Library Management System.

## Test Structure

### Base Test Class
- `BaseE2ETest.java` - Base class containing common setup, teardown, and utility methods

### Test Classes
- `DashboardE2ETest.java` - Tests for dashboard functionality and navigation
- `BookManagementE2ETest.java` - Tests for book CRUD operations
- `StudentManagementE2ETest.java` - Tests for student CRUD operations
- `BookIssueReturnE2ETest.java` - Tests for book issue and return operations
- `E2ETestSuite.java` - Test suite runner for all E2E tests

### Utilities
- `TestDataSetup.java` - Utility for setting up test data
- `application-test.properties` - Test-specific configuration

## Test Coverage

### Dashboard Tests
- ✅ Dashboard display with statistics
- ✅ Sidebar navigation functionality
- ✅ Quick action buttons
- ✅ Responsive design on mobile viewport

### Book Management Tests
- ✅ Books list page display
- ✅ Add book form validation
- ✅ Book creation functionality
- ✅ Search functionality
- ✅ Form validation for required fields
- ✅ ISBN format validation

### Student Management Tests
- ✅ Students list page display
- ✅ Add student form validation
- ✅ Student creation functionality
- ✅ Search functionality
- ✅ Email format validation
- ✅ Unique student ID validation

### Book Issue/Return Tests
- ✅ Issue book functionality
- ✅ Return book functionality
- ✅ Issue by student ID
- ✅ Returns by date range
- ✅ Form validation for all operations

## Running the Tests

### Prerequisites
1. Java 17 or higher
2. Maven 3.6 or higher
3. Docker (for Testcontainers)

### Running All E2E Tests
```bash
mvn verify
```

### Running Specific Test Classes
```bash
# Run dashboard tests only
mvn test -Dtest=DashboardE2ETest

# Run book management tests only
mvn test -Dtest=BookManagementE2ETest

# Run student management tests only
mvn test -Dtest=StudentManagementE2ETest

# Run issue/return tests only
mvn test -Dtest=BookIssueReturnE2ETest
```

### Running with Different Browsers
The tests are configured to run with Chromium by default. To run with other browsers:

```bash
# Firefox
mvn test -Dplaywright.browser=firefox

# WebKit
mvn test -Dplaywright.browser=webkit
```

## Test Configuration

### Browser Settings
- **Headless Mode**: Set to `false` by default for better debugging
- **Slow Motion**: 100ms delay between actions for better visibility
- **Viewport**: 1280x720 by default

### Database
- Uses Testcontainers with MySQL 8.0
- Database is recreated for each test run
- Test data is automatically set up

### Test Data
- Sample books and students are created automatically
- Each test run starts with a clean database
- Test data is isolated per test class

## Test Reports

After running the tests, you can find:
- **Surefire Reports**: `target/surefire-reports/`
- **Failsafe Reports**: `target/failsafe-reports/`
- **Screenshots**: Automatically captured on test failures

## Debugging Tests

### Running Tests in Debug Mode
```bash
mvn test -Dtest=DashboardE2ETest -Dplaywright.headless=false
```

### Taking Screenshots
Screenshots are automatically taken on test failures and saved to:
- `target/screenshots/`

### Video Recording
To enable video recording:
```bash
mvn test -Dplaywright.video=on
```

## Best Practices

1. **Test Isolation**: Each test is independent and can run in any order
2. **Data Cleanup**: Tests clean up after themselves
3. **Wait Strategies**: Proper waits for elements and page loads
4. **Assertions**: Comprehensive assertions for all test scenarios
5. **Error Handling**: Graceful handling of missing elements or data

## Troubleshooting

### Common Issues

1. **Port Already in Use**
   - Solution: Kill existing processes or use different port

2. **Browser Not Found**
   - Solution: Install Playwright browsers: `mvn exec:java -e -Dexec.mainClass="com.microsoft.playwright.CLI" -Dexec.args="install"`

3. **Database Connection Issues**
   - Solution: Ensure Docker is running for Testcontainers

4. **Test Timeouts**
   - Solution: Increase timeout values in test configuration

### Debug Commands

```bash
# Check if application is running
curl http://localhost:8080

# Check database connection
docker ps

# View test logs
mvn test -X
```

## Contributing

When adding new tests:
1. Follow the existing naming conventions
2. Use the base test class utilities
3. Add proper assertions
4. Include test documentation
5. Ensure tests are independent and can run in any order
