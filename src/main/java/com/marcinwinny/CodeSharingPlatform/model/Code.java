package com.marcinwinny.CodeSharingPlatform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Builder
@Entity
@Table(name = "code")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private String code;
    private String date;

    private Long time;
    private Long views;

    @JsonIgnore
    private Boolean restricted;
    @JsonIgnore
    private Boolean restrictedTime;
    @JsonIgnore
    private Boolean restrictedViews;

    @JsonIgnore
    private LocalDateTime preciseDate;
    @JsonIgnore
    private LocalDateTime terminateDate;
}
