package com.ziorye.proofread.controller.backend;

import com.ziorye.proofread.dto.CollectionDto;
import com.ziorye.proofread.entity.Collection;
import com.ziorye.proofread.service.CollectionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller("backendCollectionController")
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

    @DeleteMapping("destroy/{id}")
    String destroy(@PathVariable Long id) {
        collectionService.destroy(id);
        return "redirect:/backend/collections";
    }

    @DeleteMapping("destroy")
    @ResponseBody
    String destroyBatch(@RequestParam(value = "ids[]") List<Long> ids) {
        collectionService.destroyAllById(ids);
        return "DONE";
    }

    @GetMapping("create")
    String create(Model model) {
        model.addAttribute("collection", new Collection());
        return "backend/collection/create";
    }

    @PostMapping("store")
    String store(@RequestParam(value = "coverFile", required = false) MultipartFile file, @Validated(CollectionDto.OnSave.class) @ModelAttribute("collection") CollectionDto collectionDto,
                 BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "backend/collection/create";
        }
        doCover(file, collectionDto);
        collectionService.save(collectionDto);
        return "redirect:/backend/collections";
    }

    @Value("${custom.upload.base-path}")
    String uploadBasePath;
    @Value("${custom.upload.collection-cover-dir-under-base-path}")
    String postCoverDirUnderBasePath;
    private void doCover(MultipartFile file, CollectionDto collectionDto) throws IOException {
        if (file != null && !file.isEmpty()) {
            File dir = new File(uploadBasePath + File.separator + postCoverDirUnderBasePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID() + suffix;
            file.transferTo(new File(dir.getAbsolutePath() + File.separator + newFilename));
            collectionDto.setCover("/" + postCoverDirUnderBasePath + File.separator + newFilename);
        }
    }

    @GetMapping("edit/{id}")
    String edit(@PathVariable Long id, Model model) {
        Optional<Collection> optionalCollection = collectionService.findById(id);
        if (optionalCollection.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collection Not Found");
        } else {
            Collection collection = optionalCollection.get();
            model.addAttribute("collection", collection);
            return "backend/collection/edit";
        }
    }

    @PutMapping("update")
    @PreAuthorize("#collectionDto.user_id == authentication.principal.user.id")
    String update(@RequestParam(value = "coverFile", required = false) MultipartFile file, @Validated(CollectionDto.OnUpdate.class) @ModelAttribute("collection") CollectionDto collectionDto, BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("collection", collectionDto);
            return "backend/collection/edit";
        }

        doCover(file, collectionDto);

        collectionService.save(collectionDto);

        return "redirect:/backend/collections";
    }

    @PostMapping("togglePublished/{id}")
    @ResponseBody
    @Transactional
    public String togglePublished(@PathVariable Long id) {
        Optional<Collection> optionalPost = collectionService.findById(id);

        if (optionalPost.isEmpty()
                || !"doc".equals(optionalPost.get().getType())
        ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doc Not Found");
        }

        collectionService.togglePublished(id);
        return "SUCCESS";
    }
}
