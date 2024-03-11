package com.didt.pagesale.repository;

import com.didt.pagesale.model.Feedbacks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbacksRepository extends JpaRepository<Feedbacks, Long> {
}