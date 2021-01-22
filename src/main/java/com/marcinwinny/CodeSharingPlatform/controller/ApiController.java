package com.marcinwinny.CodeSharingPlatform.controller;

import com.google.gson.JsonObject;
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

    private Code code;

    @Autowired
    CodeService codeService;

    // 1. GET /api/code/N should return JSON with the N-th uploaded code snippet.
    @GetMapping(path="/api/code/{UUID}")
    public Code getCodeSnippetJsonByUUID(@PathVariable String UUID){
        return codeService.getCodeSnippetByUUID(UUID);
    }

    // 3. POST /api/code/new should take a JSON object with a single field code, use it as the current code snippet,
    // and return JSON with a single field id. ID is the unique number of the snippet that helps you can access
    // it via the endpoint GET /code/N.
    @PostMapping(path="/api/code/new")
    public String postCode(@RequestBody CodeDto codeDto){
        code = new Code(codeDto.getCode(), codeDto.getTime(), codeDto.getViews());
        codeRepository.save(code);
        JsonObject json = new JsonObject();
        json.addProperty("id", code.getId());
        return json.toString();
    }

    // 5. GET /api/code/latest should return a JSON array with 10 most recently uploaded code snippets sorted from the newest to the oldest.
    @GetMapping(path="/api/code/latest")
    public List<Code> getTenRecentlyUploadedJson(HttpServletResponse response){
        response.setContentType("json/application");
        return codeService.getTenRecentlyUploaded();
    }

    @DeleteMapping(path="/api/code/deleteAll")
    public String deleteAll(){
        codeRepository.deleteAll();
        return "All the code snippets have been deleted";
    }

}
