# README

Compulsory (1p)

✅ Create a Spring Boot Project that has support for Spring Data JPA, and PostgreSQL.
✅ Using an SQL script, create tables for:
students (id, code, name, email, year)
instructors (id, name, email)
packs (id, year, semester, name)
courses (id, type, code, abbr, name, instructor_id, pack_id, group_count, description)
✅ A course can be compulsory or optional. Optional courses are grouped in packs. A student will be assigned exactly one course from each pack in their year.
✅ Create the entity class and a repository for students.
✅ Implement a simple test using a CommandLineRunner.

Homework (2p)

✅ Create all the entity classes. Use @OneToMany and @ManyToOne associations. (You may use Lombok, but it is not necessarily required.)
✅ Think carefully if Student and Instructor should inherit a base abstract class.
✅ Create Spring JPA repositories for all entities.
✅ Think what queries may be necessary and define them in the repos: use at least one query based on a specific JPQL string, one derived query, and one transactional, modifying query.
✅ Create a @Service class for each entity, that uses the corresponding repo.
✅ Use Java Faker to populate the database and test CRUD operations for courses.