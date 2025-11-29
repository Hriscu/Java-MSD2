package org.quickgrade.controller;

import org.quickgrade.config.RabbitConfig;
import org.quickgrade.dto.GradeDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grades")
public class GradePublisherController {

    private final RabbitTemplate template;

    public GradePublisherController(RabbitTemplate template) {
        this.template = template;
    }

    @PostMapping
    public String publishGrade(@RequestBody GradeDto grade) {
        template.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTING_KEY, grade);
        return "Grade published for student " + grade.getStudentCode();
    }
}