package org.example.demo;

import com.github.javafaker.Faker;
import org.example.demo.entity.*;
import org.example.demo.entity.Course.CourseType;
import org.example.demo.repository.CourseRepository;
import org.example.demo.repository.GradeRepository;
import org.example.demo.repository.InstructorPreferenceRepository;
import org.example.demo.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Locale;
import java.util.Random;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(
            StudentService studentService,
            InstructorService instructorService,
            PackService packService,
            CourseService courseService,
            EnrollmentService enrollmentService,
            CourseRepository courseRepository,
            GradeRepository gradeRepo,
            InstructorPreferenceRepository prefRepo
    ) {
        return args -> {
            if (courseRepository.count() > 0) {
                System.out.println("=== DB ALREADY POPULATED - SKIPPING GENERATION ===");
                return;
            }

            System.out.println("=== GENERATING INITIAL FAKE DATA (COMPLEX) ===");

            Faker faker = new Faker(new Locale("ro"));
            Random rnd = new Random();

            Instructor i1 = instructorService.save(new Instructor(faker.name().fullName(), "prof1@uni.ro"));
            Instructor i2 = instructorService.save(new Instructor(faker.name().fullName(), "prof2@uni.ro"));

            Course cOOP = courseService.create(new Course(
                    CourseType.compulsory, "OOP", "OOP", "Object Oriented Programming",
                    i1, null, 5, "Fundamentele programării"
            ));

            Course cJava = courseService.create(new Course(
                    CourseType.compulsory, "JAVA", "JAVA", "Advanced Java",
                    i1, null, 6, "Java Spring Boot"
            ));

            Course cDb = courseService.create(new Course(
                    CourseType.compulsory, "DB", "DB", "Databases",
                    i2, null, 4, "PostgreSQL & SQL"
            ));

            Pack p1 = packService.save(new Pack(1, 1, "Software Engineering Track"));

            Course cOptWeb = courseService.create(new Course(
                    CourseType.optional, "OPT-WEB", "WEB", "Modern Web Dev",
                    i2, p1, 4, "React & Angular"
            ));

            Course cOptCloud = courseService.create(new Course(
                    CourseType.optional, "OPT-CLOUD", "CLOUD", "Cloud Computing",
                    i2, p1, 4, "AWS & Azure"
            ));

            prefRepo.save(new InstructorPreference(cOptWeb, "JAVA", 0.6));
            prefRepo.save(new InstructorPreference(cOptWeb, "OOP", 0.4));
            prefRepo.save(new InstructorPreference(cOptCloud, "DB", 1.0));

            Pack p2 = packService.save(new Pack(1, 1, "Artificial Intelligence Track"));

            Course cOptML = courseService.create(new Course(
                    CourseType.optional, "OPT-ML", "ML", "Machine Learning",
                    i1, p2, 4, "Neural Networks"
            ));

            Course cOptVis = courseService.create(new Course(
                    CourseType.optional, "OPT-VIS", "VIS", "Computer Vision",
                    i1, p2, 4, "Image Processing"
            ));

            prefRepo.save(new InstructorPreference(cOptML, "DB", 0.7));
            prefRepo.save(new InstructorPreference(cOptML, "JAVA", 0.3));
            prefRepo.save(new InstructorPreference(cOptVis, "OOP", 1.0));

            for (int i = 1; i <= 15; i++) {
                String code = "S" + String.format("%03d", i);
                String name = faker.name().fullName();

                Student s = studentService.save(new Student(code, name, code.toLowerCase() + "@student.ro", 1));

                createRandomGrade(s, cOOP, gradeRepo, rnd);
                createRandomGrade(s, cJava, gradeRepo, rnd);
                createRandomGrade(s, cDb, gradeRepo, rnd);
            }

            System.out.println("=== DATA GENERATION DONE (2 Packs Created) ===");
        };
    }

    private void createRandomGrade(Student s, Course c, GradeRepository repo, Random rnd) {
        double val = 5.0 + (10.0 - 5.0) * rnd.nextDouble();
        val = Math.round(val * 100.0) / 100.0;

        repo.save(new Grade(s.getCode(), c.getCode(), val));
    }
}