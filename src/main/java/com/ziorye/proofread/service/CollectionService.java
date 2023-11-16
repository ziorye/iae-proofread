package com.ziorye.proofread.service;

import com.ziorye.proofread.entity.Collection;
import org.springframework.data.domain.Page;

public interface CollectionService {
    Page<Collection> findAll(int pageNumber, int pageSize);

    void destroy(Long id);
}
