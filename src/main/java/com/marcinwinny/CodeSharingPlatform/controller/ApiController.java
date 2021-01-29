package com.marcinwinny.CodeSharingPlatform.controller;

import com.google.gson.JsonObject;
import com.marcinwinny.CodeSharingPlatform.model.Code;
import com.marcinwinny.CodeSharingPlatform.model.CodeDto;
import com.marcinwinny.CodeSharingPlatform.repository.CodeRepository;
import com.marcinwinny.CodeSharingPlatform.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    // and return JSON with a single field id.
    @PostMapping(path = "/api/code/new")
    public CodeDto postCode(@RequestBody CodeDto codeDto) {

        Code codeToSave = Code.builder()
                .code(codeDto.getCode())
                .time(codeDto.getTime())
                .views(codeDto.getViews())
                .build();

        applyRestrictions(codeToSave);

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

    //temporary, throw this to service or mapping
    private Code applyRestrictions(Code code){

        code.setPreciseDate(LocalDateTime.now());
        code.setDate(formatDate(code.getPreciseDate()));
        code.setRestrictedTime(false);
        code.setRestrictedViews(false);
        code.setRestricted(false);

        if(code.getTime() < 0){
            code.setTime(0L);
        }
        if(code.getViews() < 0){
            code.setViews(0L);
        }

        if(code.getTime() > 0){
            code.setRestrictedTime(true);
        }
        if(code.getViews() > 0){
            code.setRestrictedViews(true);
        }

        if(code.getRestrictedTime() || code.getRestrictedViews()){
            code.setRestricted(true);
        }

        code.setTerminateDate(code.getPreciseDate().plusSeconds(code.getTime()));

        return code;
    }

    private String formatDate(LocalDateTime dateToFormat){
        final String DATE_FORMATTER= "yyyy/MM/dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        return dateToFormat.format(formatter);
    }

}
