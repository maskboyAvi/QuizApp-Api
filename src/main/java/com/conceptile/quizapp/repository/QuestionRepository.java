package com.conceptile.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.conceptile.quizapp.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {}
