package com.conceptile.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conceptile.quizapp.entity.QuizSession;

public interface QuizSessionRepository extends JpaRepository<QuizSession, Long> {}
