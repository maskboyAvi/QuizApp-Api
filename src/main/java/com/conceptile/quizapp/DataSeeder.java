package com.conceptile.quizapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.conceptile.quizapp.entity.Question;
import com.conceptile.quizapp.repository.QuestionRepository;

import java.util.List;

@Component
public class DataSeeder {

    @Bean
    public CommandLineRunner seedData(QuestionRepository questionRepository) {
        return args -> {
            questionRepository.save(new Question(null, "What is the capital of France?",
                    List.of("Paris", "Rome", "Berlin", "Madrid"), "Paris"));
            questionRepository.save(new Question(null, "Who wrote Hamlet?",
                    List.of("Shakespeare", "Homer", "Virgil", "Dante"), "Shakespeare"));
            questionRepository.save(new Question(null, "What is 2 + 2?",
                    List.of("3", "4", "5", "6"), "4"));
        };
    }
}
