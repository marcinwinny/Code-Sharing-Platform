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

        // response nie powinno pojawiać się poza kontrolerem
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

}
