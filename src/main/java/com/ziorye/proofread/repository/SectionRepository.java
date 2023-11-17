package com.ziorye.proofread.repository;

import com.ziorye.proofread.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Long> {
    Optional<Section> findFirstByTitle(String title);
}
