package com.ziorye.proofread.service.impl;

import com.ziorye.proofread.entity.Collection;
import com.ziorye.proofread.repository.CollectionRepository;
import com.ziorye.proofread.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    CollectionRepository collectionRepository;

    @Override
    public Page<Collection> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by("id").descending());
        return this.collectionRepository.findAll(pageable);
    }

    @Override
    public void destroy(Long id) {
        this.collectionRepository.deleteById(id);
    }
}
