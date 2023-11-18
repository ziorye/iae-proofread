package com.ziorye.proofread.service;

import com.ziorye.proofread.dto.LectureDto;
import com.ziorye.proofread.entity.Lecture;

import java.util.List;
import java.util.Optional;

public interface LectureService {
    void save(LectureDto lectureDto);

    Optional<Lecture> findById(Long id);

    void destroy(Long id);

    void destroyAllById(List<Long> ids);
}
