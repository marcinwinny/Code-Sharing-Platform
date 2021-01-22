package com.marcinwinny.CodeSharingPlatform.service;

import com.marcinwinny.CodeSharingPlatform.model.Code;
import com.marcinwinny.CodeSharingPlatform.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
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

    private Code code;

    public List<Code> getTenRecentlyUploaded(){

        long howManyOnPage = 10;

        long countNotRestricted = StreamSupport.stream(codeRepository.findAll().spliterator(), false)
                .filter(code -> !code.isRestricted()).count();

        return StreamSupport.stream(codeRepository.findAll().spliterator(), false)
                .filter(code -> !code.isRestricted())
                .skip(Math.max(0, countNotRestricted - howManyOnPage))
                .sorted(Comparator.comparing(Code::getPreciseDate).reversed())
                .collect(Collectors.toList());
    }

    public void updateTimeInAllRestrictedByTime(){

        StreamSupport.stream(codeRepository.findAll().spliterator(), false)
                .filter(code -> code.getTime() > 0)
                .forEach(this::updateTime);
    }

    public Code getCodeSnippetByUUID(String UUID){
        Optional<Code> codeOptional = codeRepository.findById(UUID);

        codeOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Code snippet not found"));

        updateTimeOnRequest(codeOptional.get());

        codeOptional = codeRepository.findById(UUID);

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

    private void updateTime(Code code){
        if(code.getTime() > 0){
            code.setTime(code.getTime() - 1);
            codeRepository.save(code);
            if(code.getTime() == 0){
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
}
