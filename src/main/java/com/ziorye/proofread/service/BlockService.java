package com.ziorye.proofread.service;

import com.ziorye.proofread.dto.BlockDto;
import com.ziorye.proofread.entity.Block;

import java.util.List;
import java.util.Optional;

public interface BlockService {
    void save(BlockDto blockDto);

    Optional<Block> findById(Long id);

    void destroy(Long id);

    void destroyAllById(List<Long> ids);
}
