package com.ziorye.proofread.controller.backend;

import com.ziorye.proofread.controller.WithMockUserForAdminBaseTest;
import com.ziorye.proofread.entity.Collection;
import com.ziorye.proofread.entity.User;
import com.ziorye.proofread.repository.CollectionRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.UUID;

class CollectionControllerTest extends WithMockUserForAdminBaseTest {

    @Test
    void index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/backend/collections"))
                .andExpect(MockMvcResultMatchers.view().name("backend/collection/index"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Collection 管理")))
        ;
    }

    @Test
    void deleteById(@Autowired CollectionRepository collectionRepository) throws Exception {
        Collection collection = new Collection();
        collection.setTitle(UUID.randomUUID().toString());
        collection.setSlug(UUID.randomUUID().toString());
        collection.setType("doc");
        collection.setUser(new User(1L));

        collectionRepository.save(collection);

        mvc.perform(MockMvcRequestBuilders.delete("/backend/collections/destroy/" + collection.getId()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/backend/collections"))
        ;

        Optional<Collection> byId = collectionRepository.findById(collection.getId());
        Assertions.assertTrue(byId.isEmpty());
    }
}