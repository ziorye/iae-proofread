package com.ziorye.proofread.service;

import com.ziorye.proofread.dto.CollectionDto;
import com.ziorye.proofread.entity.Collection;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CollectionService {
    Page<Collection> findAll(int pageNumber, int pageSize);

    void destroy(Long id);

    void destroyAllById(List<Long> ids);

    void save(CollectionDto collectionDto);

    Optional<Collection> findById(Long id);
}
