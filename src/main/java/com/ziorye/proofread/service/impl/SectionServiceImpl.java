package com.ziorye.proofread.service.impl;

import com.ziorye.proofread.dto.SectionDto;
import com.ziorye.proofread.entity.Collection;
import com.ziorye.proofread.entity.Section;
import com.ziorye.proofread.repository.SectionRepository;
import com.ziorye.proofread.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SectionServiceImpl implements SectionService {
    @Autowired
    SectionRepository sectionRepository;

    @Override
    public void save(SectionDto sectionDto) {
        Section section = new Section();

        section.setTitle(sectionDto.getTitle());
        section.setSortOrder(sectionDto.getSortOrder());
        section.setDescription(sectionDto.getDescription());
        section.setCollection(new Collection(sectionDto.getCollection_id()));
        section.setCreatedAt(LocalDateTime.now());
        sectionRepository.save(section);
    }

    @Override
    public Optional<Section> findById(Long id) {
        return sectionRepository.findById(id);
    }
}
