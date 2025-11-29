package org.example.demo.messaging;

import org.example.demo.entity.Course;
import org.example.demo.entity.Grade;
import org.example.demo.repository.CourseRepository;
import org.example.demo.repository.GradeRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class GradeReceiver {

    private final CourseRepository courseRepo;
    private final GradeRepository gradeRepo;

    public GradeReceiver(CourseRepository courseRepo, GradeRepository gradeRepo) {
        this.courseRepo = courseRepo;
        this.gradeRepo = gradeRepo;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consumeMessage(GradeMessage message) {
        System.out.println(" [x] Processing grade for: " + message.getStudentCode());

        if (message.getGrade() < 0) {
            System.out.println(" [!] Error detected! Grade is negative. Throwing exception to trigger DLQ...");
            throw new RuntimeException("Simulated processing error for DLQ testing");
        }

        Optional<Course> courseOpt = courseRepo.findByCode(message.getCourseCode());

        if (courseOpt.isPresent()) {
            Course c = courseOpt.get();
            if (c.getType() == Course.CourseType.compulsory) {
                Grade g = new Grade(message.getStudentCode(), message.getCourseCode(), message.getGrade());
                gradeRepo.save(g);
                System.out.println(" [V] Saved COMPULSORY grade in DB.");
            } else {
                System.out.println(" [-] Ignored OPTIONAL course grade.");
            }
        } else {
            System.out.println(" [?] Course not found: " + message.getCourseCode());
        }
    }
}