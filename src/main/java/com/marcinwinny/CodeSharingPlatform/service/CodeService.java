package com.marcinwinny.CodeSharingPlatform.service;

import com.marcinwinny.CodeSharingPlatform.model.Code;
import com.marcinwinny.CodeSharingPlatform.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class CodeService {

    @Autowired
    CodeRepository codeRepository;

    public List<Code> getTenRecentlyUploaded(){

        long howManyOnPage = 10;

        long countNotRestricted = codeRepository.countAllByRestrictedFalse();

        return codeRepository.findAll().stream()
                .filter(code -> !code.getRestricted())
                .skip(Math.max(0, countNotRestricted - howManyOnPage))
                .sorted(Comparator.comparing(Code::getPreciseDate).reversed())
                .collect(Collectors.toList());
    }

    public Code getCodeSnippetById(Long id){
        Optional<Code> codeOptional = codeRepository.findById(id);

        // response should be only in controller
        codeOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Code snippet not found"));

        updateTimeOnRequest(codeOptional.get());

        codeOptional = codeRepository.findById(id);

        codeOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Code snippet not found"));

        updateViews(codeOptional.get());

        return codeOptional.get();
    }

    private void updateViews(Code code) {
        if(code.getViews() > 0){
            code.setViews(code.getViews() - 1);
            codeRepository.save(code);
            if(code.getViews() == 0){
                codeRepository.delete(code);
            }
        }
    }

    private void updateTimeOnRequest(Code code){
        if(code.getTime() > 0){
            long timeDifference = ChronoUnit.SECONDS.between(LocalDateTime.now(), code.getTerminateDate());
            System.out.println(code.getTime());
            code.setTime(timeDifference);
            codeRepository.save(code);

            if(code.getTime() <= 0){
                codeRepository.delete(code);
            }
        }
    }

    public Code applyRestrictions(Code code){

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
