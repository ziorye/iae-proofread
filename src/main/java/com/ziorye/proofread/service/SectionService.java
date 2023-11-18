package com.ziorye.proofread.service;

import com.ziorye.proofread.dto.SectionDto;
import com.ziorye.proofread.entity.Section;

import java.util.Optional;

public interface SectionService {
    void save(SectionDto sectionDto);

    Optional<Section> findById(Long id);

    void destroy(Long id);
}
