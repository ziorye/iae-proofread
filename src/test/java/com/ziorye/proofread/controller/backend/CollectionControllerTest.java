package com.ziorye.proofread.controller.backend;

import com.ziorye.proofread.controller.WithMockUserForAdminBaseTest;
import com.ziorye.proofread.entity.Collection;
import com.ziorye.proofread.entity.User;
import com.ziorye.proofread.repository.CollectionRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
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

    @Test
    void batchDelete(@Autowired CollectionRepository collectionRepository) throws Exception {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Collection collection = new Collection();
            collection.setTitle(UUID.randomUUID().toString());
            collection.setSlug(UUID.randomUUID().toString());
            collection.setType("doc");
            collection.setUser(new User(1L));

            Collection c = collectionRepository.save(collection);
            ids.add(c.getId());
        }

        mvc.perform(MockMvcRequestBuilders.delete("/backend/collections/destroy")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("ids[]", ids.get(0).toString())
                        .param("ids[]", ids.get(1).toString())
                )
                .andExpect(MockMvcResultMatchers.content().string("DONE"))
        ;

        List<Collection> allById = collectionRepository.findAllById(ids);
        Assertions.assertTrue(allById.isEmpty());
    }

    @Test
    void store(@Autowired CollectionRepository collectionRepository) throws Exception {
        String title = "title-" + UUID.randomUUID();
        mvc.perform(MockMvcRequestBuilders
                        .post("/backend/collections/store")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("title", title)
                        .param("slug", UUID.randomUUID().toString())
                        .param("type", "doc")
                        .param("description", "content-" + UUID.randomUUID())
                        .param("user_id", "1")
                )
                .andExpect(MockMvcResultMatchers.redirectedUrl("/backend/collections"))
        ;

        Optional<Collection> co = collectionRepository.findFirstByTitle(title);
        Assertions.assertTrue(co.isPresent());

        collectionRepository.delete(co.get());
    }

    @Test
    void storeWithCoverImage(@Autowired CollectionRepository collectionRepository, @Autowired Environment env) throws Exception {
        String title = "title-" + UUID.randomUUID();
        MockMultipartFile coverFile = new MockMultipartFile("coverFile", "cover.png", MediaType.IMAGE_PNG_VALUE, new byte[] { 1, 2, 3 });
        mvc.perform(MockMvcRequestBuilders
                        .multipart("/backend/collections/store")
                        //.contentType(MediaType.MULTIPART_FORM_DATA)
                        .file(coverFile)
                        .param("id", "")
                        .param("title", title)
                        .param("slug", UUID.randomUUID().toString())
                        .param("type", "doc")
                        .param("description", "content-" + UUID.randomUUID())
                        .param("user_id", "1")
                )
                .andExpect(MockMvcResultMatchers.redirectedUrl("/backend/collections"))
        ;

        Optional<Collection> co = collectionRepository.findFirstByTitle(title);
        Assertions.assertTrue(co.isPresent());

        String cover = co.get().getCover();
        File coverOnDisk = new File(env.getProperty("custom.upload.base-path") + cover);
        Assertions.assertTrue(Files.exists(coverOnDisk.toPath()));
        Assertions.assertTrue(coverOnDisk.delete());

        collectionRepository.delete(co.get());
    }

    @Test
    void update(@Autowired CollectionRepository collectionRepository) throws Exception {
        String title = "title-" + UUID.randomUUID();
        mvc.perform(MockMvcRequestBuilders.post("/backend/collections/store")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("title", title)
                        .param("slug", UUID.randomUUID().toString())
                        .param("type", "doc")
                        .param("description", "content-" + UUID.randomUUID())
                        .param("user_id", "1")
                )
                .andExpect(MockMvcResultMatchers.redirectedUrl("/backend/collections"))
        ;
        Optional<Collection> co = collectionRepository.findFirstByTitle(title);
        Assertions.assertTrue(co.isPresent());
        Collection collection = co.get();

        String descriptionUpdated = "description--updated";
        mvc.perform(MockMvcRequestBuilders.put("/backend/collections/update")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", collection.getId().toString())
                        .param("title", collection.getTitle())
                        .param("slug", UUID.randomUUID().toString())
                        .param("type", collection.getType())
                        .param("description", descriptionUpdated)
                        .param("user_id", collection.getUser().getId().toString())
                )
                .andExpect(MockMvcResultMatchers.redirectedUrl("/backend/collections"))
        ;

        Collection collectionUpdated = collectionRepository.findFirstByTitle(title).get();
        Assertions.assertEquals(descriptionUpdated, collectionUpdated.getDescription());

        collectionRepository.delete(co.get());
    }
}