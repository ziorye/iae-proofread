package com.ziorye.proofread.controller.backend;

import com.ziorye.proofread.dto.LectureDto;
import com.ziorye.proofread.entity.Lecture;
import com.ziorye.proofread.service.CollectionService;
import com.ziorye.proofread.service.LectureService;
import com.ziorye.proofread.service.SectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller("backendLectureController")
@RequestMapping("/backend/lectures/")
public class LectureController {
    @Autowired
    LectureService lectureService;
    @Autowired
    SectionService sectionService;
    @Autowired
    CollectionService collectionService;

    @GetMapping("create")
    String create(@RequestParam("collection_id") Long collectionId, @RequestParam("section_id") Long sectionId, Model model) {
        model.addAttribute("collection", collectionService.findById(collectionId).get());
        model.addAttribute("section", sectionService.findById(sectionId).get());
        model.addAttribute("lecture", new Lecture());
        return "backend/lecture/create";
    }

    @PostMapping("store")
    String store(@Valid @ModelAttribute("lecture") LectureDto lectureDto,
                 BindingResult result) {
        if (result.hasErrors()) {
            return "backend/lecture/create";
        }
        lectureService.save(lectureDto);
        return "redirect:/backend/collections/edit/" + lectureDto.getCollection_id();
    }
}
