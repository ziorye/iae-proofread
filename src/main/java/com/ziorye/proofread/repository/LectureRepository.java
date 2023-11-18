package com.ziorye.proofread.repository;

import com.ziorye.proofread.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Optional<Lecture> findFirstByTitle(String title);
}
