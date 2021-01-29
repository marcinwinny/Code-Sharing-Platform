package com.marcinwinny.CodeSharingPlatform.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CodeDto {

    private String code;
    private Long time;
    private Long views;
    private Long id;

}
