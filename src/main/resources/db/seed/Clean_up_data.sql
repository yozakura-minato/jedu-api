-- Student -------------------------------------------------------------------------------------------------------------
DELETE
FROM students
WHERE display_id ILIKE 'TEST%';

-- Back office master --------------------------------------------------------------------------------------------------
DELETE
FROM employee_roles;

DELETE
FROM employees
WHERE display_id ILIKE 'TEST%';

DELETE
FROM roles
WHERE display_id ILIKE 'TEST%';

DELETE
FROM departments
WHERE display_id LIKE 'TEST%';
