package com.marcinwinny.CodeSharingPlatform.controller;

import com.marcinwinny.CodeSharingPlatform.model.Code;
import com.marcinwinny.CodeSharingPlatform.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    CodeService codeService;

    @GetMapping(path="/hello")
    public String helloWorld(){
        return "helloWorld";
    }

    // 2. GET /code/N should return HTML that contains the N-th uploaded code snippet.
    @GetMapping(path="/code/{id}")
    // zamiast UUID wystarczy id - będzie wiadomo o co chodzi
    public String getCodeSnippetHtmlByN(HttpServletResponse response, @PathVariable Long id, Model model){
        response.setContentType("text/html");

        model.addAttribute("code", codeService.getCodeSnippetByUUID(id));

        return "codeByUUID";
    }

    // 4. GET /code/new should be the same as in the previous stage.
    @GetMapping(path="code/new")
    public String getNewCodeHtml(HttpServletResponse response) {
        response.setContentType("text/html");
        return "newCodeForm";
//        return codeService.injectNewCodeToHtml();
    }

    // 6. GET /code/latest should return HTML that contains 10 most recently uploaded code snippets.
    // Use the title Latest for this page.
    @GetMapping(path = "/code/latest")
    public String getTenRecentlyUploadedHtml(HttpServletResponse response, Model model){
        response.setContentType("text/html");
        List<Code> codeList = codeService.getTenRecentlyUploaded();
        model.addAttribute("codes", codeList);
        return "latestUploaded";
    }
}
