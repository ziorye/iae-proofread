package com.ziorye.proofread.controller.backend;

import com.ziorye.proofread.controller.WithMockUserForAdminBaseTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

class CollectionControllerTest extends WithMockUserForAdminBaseTest {

    @Test
    void index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/backend/collections"))
                .andExpect(MockMvcResultMatchers.view().name("backend/collection/index"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Collection 管理")))
        ;
    }
}