package com.ziorye.proofread.controller.backend;

import com.ziorye.proofread.dto.SectionDto;
import com.ziorye.proofread.entity.Section;
import com.ziorye.proofread.service.CollectionService;
import com.ziorye.proofread.service.SectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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

    @GetMapping("edit/{id}")
    String edit(
            @PathVariable Long id,
            @RequestParam("collection_id") Long collectionId,
            Model model
    ) {
        Optional<Section> optionalSection = sectionService.findById(id);
        if (optionalSection.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            Section section = optionalSection.get();
            model.addAttribute("collection", collectionService.findById(collectionId).get());
            model.addAttribute("section", section);
            return "backend/section/edit";
        }
    }

    @PutMapping("update")
    String update(
            @Validated @ModelAttribute("section") SectionDto sectionDto,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("collection", collectionService.findById(sectionDto.getCollection_id()).get());
            model.addAttribute("section", sectionDto);

            return "backend/section/edit";
        }

        sectionService.save(sectionDto);

        return "redirect:/backend/collections/edit/" + sectionDto.getCollection_id();
    }
}
