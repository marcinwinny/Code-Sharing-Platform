package com.marcinwinny.CodeSharingPlatform.controller;

import com.marcinwinny.CodeSharingPlatform.model.Code;
import com.marcinwinny.CodeSharingPlatform.model.CodeDto;
import com.marcinwinny.CodeSharingPlatform.repository.CodeRepository;
import com.marcinwinny.CodeSharingPlatform.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
public class ApiController {

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    CodeService codeService;

    // 1. GET /api/code/id should return JSON with the uploaded code snippet with given id
    @GetMapping(path = "/api/code/{id}")
    public Code getCodeSnippetJsonById(@PathVariable Long id) {
        return codeService.getCodeSnippetById(id);
    }

    // 3. POST /api/code/new should take a JSON object with a field code, time, views, use it as the current code snippet,
    // and return JSON with fields id, code, time, views.
    @PostMapping(path = "/api/code/new")
    public CodeDto postCode(@RequestBody CodeDto codeDto) {

        Code codeToSave = Code.builder()
                .code(codeDto.getCode())
                .time(codeDto.getTime())
                .views(codeDto.getViews())
                .build();

        codeService.applyRestrictions(codeToSave);

        Code codeFromDb = codeRepository.save(codeToSave);

        return CodeDto.builder()
                .id(codeFromDb.getId())
                .code(codeFromDb.getCode())
                .views(codeFromDb.getViews())
                .time(codeFromDb.getTime())
                .build();
    }

    // 5. GET /api/code/latest should return a JSON array with 10 most recently uploaded code snippets sorted from the newest to the oldest.
    @GetMapping(path = "/api/code/latest")
    public List<Code> getTenRecentlyUploadedJson(HttpServletResponse response) {
        response.setContentType("json/application");
        return codeService.getTenRecentlyUploaded();
    }

    @DeleteMapping(path = "/api/code/deleteAll")
    public String deleteAll() {
        codeRepository.deleteAll();
        return "All the code snippets have been deleted";
    }
}
