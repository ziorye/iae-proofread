package com.ziorye.proofread.controller.backend;

import com.ziorye.proofread.dto.SectionDto;
import com.ziorye.proofread.entity.Section;
import com.ziorye.proofread.service.CollectionService;
import com.ziorye.proofread.service.SectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller("backendSectionController")
@RequestMapping("/backend/sections/")
public class SectionController {
    @Autowired
    SectionService sectionService;
    @Autowired
    CollectionService collectionService;

    @GetMapping("create")
    String create(@RequestParam("collection_id") Long collectionId, Model model) {
        model.addAttribute("collection", collectionService.findById(collectionId).get());
        model.addAttribute("section", new Section());
        return "backend/section/create";
    }

    @PostMapping("store")
    String store(@Valid @ModelAttribute("section") SectionDto sectionDto,
                 BindingResult result) {
        if (result.hasErrors()) {
            return "backend/section/create";
        }
        sectionService.save(sectionDto);
        return "redirect:/backend/collections/edit/" + sectionDto.getCollection_id();
    }
}
