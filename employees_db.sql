CREATE DATABASE IF NOT EXISTS company_db;
USE company_db;

CREATE TABLE IF NOT EXISTS employees (1
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    department VARCHAR(50),
    position VARCHAR(50),
    salary DECIMAL(10,2),
    hire_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO employees (name, email, department, position, salary, hire_date) VALUES
('John Smith', 'john.smith@company.com', 'Engineering', 'Software Engineer', 75000.00, '2022-01-15'),
('Emma Wilson', 'emma.wilson@company.com', 'Marketing', 'Marketing Manager', 65000.00, '2021-03-20'),
('Mike Johnson', 'mike.johnson@company.com', 'Sales', 'Sales Representative', 55000.00, '2023-02-10'),
('Sarah Brown', 'sarah.brown@company.com', 'HR', 'HR Specialist', 60000.00, '2020-11-05');