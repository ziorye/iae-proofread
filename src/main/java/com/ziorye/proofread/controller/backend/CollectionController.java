package com.ziorye.proofread.controller.backend;

import com.ziorye.proofread.entity.Collection;
import com.ziorye.proofread.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/backend/collections")
public class CollectionController {
    @Autowired
    CollectionService collectionService;

    @GetMapping("")
    String index(Model model,
                 @RequestParam("page") Optional<Integer> page,
                 @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<Collection> pageContent = collectionService.findAll(currentPage, pageSize);
        model.addAttribute("page", pageContent);
        return "backend/collection/index";
    }
}
