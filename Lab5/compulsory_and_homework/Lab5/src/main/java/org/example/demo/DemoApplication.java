package org.example.demo;

import com.github.javafaker.Faker;
import org.example.demo.entity.*;
import org.example.demo.entity.Course.CourseType;
import org.example.demo.repository.CourseRepository;
import org.example.demo.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Locale;
import java.util.Random;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) { SpringApplication.run(DemoApplication.class, args); }

    @Bean
    CommandLineRunner runner(
            StudentService studentService,
            InstructorService instructorService,
            PackService packService,
            CourseService courseService,
            EnrollmentService enrollmentService,
            CourseRepository courseRepository
    ) {
        return args -> {
            Faker faker = new Faker(new Locale("ro"));
            Random rnd = new Random();

            Instructor i1 = instructorService.save(new Instructor(faker.name().fullName(), "i1@uni.ro"));
            Instructor i2 = instructorService.save(new Instructor(faker.name().fullName(), "i2@uni.ro"));

            Pack p11 = packService.save(new Pack(1, 1, "Pack-1.1"));
            Pack p12 = packService.save(new Pack(1, 2, "Pack-1.2"));

            Course cAlg = courseService.create(new Course(
                    CourseType.compulsory, "ALG-101", "ALG", "Algebra",
                    i1, null, 3, "Curs compulsory de algebră"
            ));

            Course cOptA = courseService.create(new Course(
                    CourseType.optional, "OPT-A-111", "OPTA", "Programare Funcțională",
                    i2, p11, 2, "Optional PF"
            ));
            Course cOptB = courseService.create(new Course(
                    CourseType.optional, "OPT-B-112", "OPTB", "Arhitectura Calculatoarelor",
                    i2, p11, 2, "Optional Arch"
            ));
            Course cOptC = courseService.create(new Course(
                    CourseType.optional, "OPT-C-121", "OPTC", "Baze de Date",
                    i1, p12, 2, "Optional BD"
            ));

            Student s1 = studentService.save(new Student("S001", faker.name().fullName(), "s1@uni.ro", 1));
            Student s2 = studentService.save(new Student("S002", faker.name().fullName(), "s2@uni.ro", 1));

            enrollmentService.save(new Enrollment(s1, rnd.nextBoolean()? cOptA : cOptB, p11));
            enrollmentService.save(new Enrollment(s1, cOptC, p12));
            enrollmentService.save(new Enrollment(s2, rnd.nextBoolean()? cOptA : cOptB, p11));
            enrollmentService.save(new Enrollment(s2, cOptC, p12));

            System.out.println("\n=== CRUD COURSES ===");

            courseService.all().forEach(c ->
                    System.out.println(c.getCode() + " :: " + c.getName() + " [" + c.getType() + "]")
            );

            System.out.println("\nUpdate description for OPT-A-111");
            int updated = courseService.updateDescriptionByCode("OPT-A-111", "Descriere nouă prin @Modifying");
            System.out.println("Rows updated = " + updated);

            // Create + Delete
            Course temp = courseService.create(new Course(
                    CourseType.optional, "TEMP-999", "TMP", "Temporar",
                    i1, p12, 1, "Va fi șters"
            ));
            System.out.println("Created TEMP-999 id=" + temp.getId());
            courseService.delete(temp.getId());
            System.out.println("Deleted TEMP-999");

            System.out.println("\nCursuri OPTIONAL ale instructorului i2@uni.ro:");
            courseService.byTypeAndInstructorEmail(CourseType.optional, "i2@uni.ro")
                    .forEach(c -> System.out.println(" - " + c.getCode() + " " + c.getName()));

            System.out.println("\nPacks pentru anul 1:");
            packService.findPacksForYear(1).forEach(p ->
                    System.out.println(" - " + p.getName() + " (sem " + p.getSemester() + ")")
            );

            System.out.println("\nStudenți anul 1:");
            studentService.findByYear(1).forEach(s ->
                    System.out.println(" - " + s.getCode() + " " + s.getName())
            );

            System.out.println("\n=== DONE ===\n");
        };
    }
}
