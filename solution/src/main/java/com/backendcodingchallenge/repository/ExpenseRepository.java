package com.backendcodingchallenge.repository;

import com.backendcodingchallenge.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Expense class repository
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
