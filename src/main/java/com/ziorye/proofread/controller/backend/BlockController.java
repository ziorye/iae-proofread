package com.ziorye.proofread.controller.backend;

import com.ziorye.proofread.dto.BlockDto;
import com.ziorye.proofread.entity.Block;
import com.ziorye.proofread.entity.Lecture;
import com.ziorye.proofread.service.BlockService;
import com.ziorye.proofread.service.CollectionService;
import com.ziorye.proofread.service.LectureService;
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

@Controller("backendBlockController")
@RequestMapping("/backend/blocks/")
public class BlockController {
    @Autowired
    BlockService blockService;
    @Autowired
    LectureService lectureService;
    @Autowired
    CollectionService collectionService;

    @GetMapping("create")
    String create(@RequestParam("lecture_id") Long lectureId, @RequestParam("collection_id") Long collectionId, Model model) {
        Lecture lecture = lectureService.findById(lectureId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("lecture", lecture);
        model.addAttribute("collection", collectionService.findById(collectionId).orElseThrow());
        model.addAttribute("block", new Block());
        return "backend/block/create";
    }

    @PostMapping("store")
    String store(@Valid @ModelAttribute("block") BlockDto blockDto,
                 BindingResult result) {
        if (result.hasErrors()) {
            return "backend/block/create";
        }
        blockService.save(blockDto);
        return "redirect:/backend/collections/edit/" + blockDto.getCollection_id();
    }

    @GetMapping("edit/{id}")
    String edit(
            @PathVariable Long id,
            @RequestParam("lecture_id") Long lectureId,
            @RequestParam("collection_id") Long collectionId,
            Model model
    ) {
        Optional<Block> optionalBlock = blockService.findById(id);
        if (optionalBlock.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            Block block = optionalBlock.get();
            Lecture lecture = lectureService.findById(lectureId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            model.addAttribute("lecture", lecture);
            model.addAttribute("collection", collectionService.findById(collectionId).orElseThrow());
            model.addAttribute("block", block);
            return "backend/block/edit";
        }
    }

    @PutMapping("update")
    String update(
            @Validated @ModelAttribute("block") BlockDto blockDto,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("block", blockDto);
            model.addAttribute("lecture", lectureService.findById(blockDto.getLecture_id()).orElseThrow());
            model.addAttribute("collection", collectionService.findById(blockDto.getCollection_id()).orElseThrow());

            return "backend/block/edit";
        }

        blockService.save(blockDto);

        return "redirect:/backend/collections/edit/" + blockDto.getCollection_id();
    }

    @DeleteMapping("destroy/{id}")
    String destroy(@PathVariable Long id) {
        Block block = blockService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        blockService.destroy(id);
        return "redirect:/backend/collections/edit/" + block.getLecture().getCollection().getId();
    }
}
