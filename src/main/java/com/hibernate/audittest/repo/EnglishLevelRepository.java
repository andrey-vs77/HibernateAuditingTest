package com.hibernate.audittest.repo;

import com.hibernate.audittest.model.EnglishLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnglishLevelRepository extends JpaRepository<EnglishLevel, Integer> {

}
