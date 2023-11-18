package com.ziorye.proofread.controller.backend;

import com.ziorye.proofread.dto.LectureDto;
import com.ziorye.proofread.entity.Lecture;
import com.ziorye.proofread.service.LectureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("backendLectureController")
@RequestMapping("/backend/lectures/")
public class LectureController {
    @Autowired
    LectureService lectureService;

    @GetMapping("create")
    String create(Model model) {
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
