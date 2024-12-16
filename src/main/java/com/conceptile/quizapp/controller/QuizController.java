package com.conceptile.quizapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.conceptile.quizapp.entity.Question;
import com.conceptile.quizapp.entity.QuizSession;
import com.conceptile.quizapp.repository.QuestionRepository;
import com.conceptile.quizapp.repository.QuizSessionRepository;

import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizSessionRepository sessionRepository;

    // Start a new quiz session
    @PostMapping("/start")
    public ResponseEntity<QuizSession> startQuiz(@RequestParam String username) {
        QuizSession session = new QuizSession();
        session.setUsername(username);
        QuizSession savedSession = sessionRepository.save(session);
        return ResponseEntity.ok(savedSession);
    }

    // Get a random question
    @GetMapping("/question")
    public ResponseEntity<Question> getQuestion(@RequestParam Long sessionId) {
        Optional<QuizSession> session = sessionRepository.findById(sessionId);
        if (session.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        QuizSession quizSession = session.get();
        Question question = questionRepository.findAll().stream()
                .filter(q -> !quizSession.getAnsweredQuestions().contains(q.getId()))
                .findAny()
                .orElse(null);

        if (question == null) {
            return ResponseEntity.noContent().build(); // No more questions
        }

        return ResponseEntity.ok(question);
    }

    // Submit an answer
    @PostMapping("/submit")
    public ResponseEntity<String> submitAnswer(@RequestParam Long sessionId,
                                                @RequestParam Long questionId,
                                                @RequestParam String answer) {
        Optional<QuizSession> session = sessionRepository.findById(sessionId);
        if (session.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid session ID");
        }

        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid question ID");
        }

        QuizSession quizSession = session.get();
        Question questionObj = question.get();

        if (quizSession.getAnsweredQuestions().contains(questionId)) {
            return ResponseEntity.badRequest().body("Question already answered");
        }

        quizSession.getAnsweredQuestions().add(questionId);

        if (questionObj.getCorrectOption().equalsIgnoreCase(answer)) {
            quizSession.setCorrectAnswers(quizSession.getCorrectAnswers() + 1);
        } else {
            quizSession.setIncorrectAnswers(quizSession.getIncorrectAnswers() + 1);
        }

        sessionRepository.save(quizSession);
        return ResponseEntity.ok("Answer submitted successfully");
    }

    // Get quiz results
    @GetMapping("/results")
    public ResponseEntity<QuizSession> getResults(@RequestParam Long sessionId) {
        Optional<QuizSession> session = sessionRepository.findById(sessionId);
        if (session.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(session.get());
    }
}
