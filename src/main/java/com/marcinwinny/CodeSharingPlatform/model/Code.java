package com.marcinwinny.CodeSharingPlatform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Builder
@Entity
@Table(name = "code")
@Getter
@Setter
public class Code {

    // w encji powinno być jak najmniej operacji i pól których nie ma w bazie
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private String code;
    private String date;

    private Long time;
    private Long views;

    @JsonIgnore
    private Boolean restricted = false;
    @JsonIgnore
    private Boolean restrictedTime = false;
    @JsonIgnore
    private Boolean restrictedViews = false;


    @JsonIgnore
    private LocalDateTime preciseDate;
    @JsonIgnore
    private LocalDateTime terminateDate;


    public Code() {
    }

    // taka logika powinna być gdzieś w serwisie albo np. mapperze przy przechodzeniu z DTO na Encje

    public Code(String code, Long time, Long views) {
        this.code = code;
        this.preciseDate = LocalDateTime.now();
        this.date = formatDate(preciseDate);
        // tego nie potrzeba - id obiektu powinna nadawać baza i powinno być jedno
//        this.id = UUID.randomUUID().toString();

        if(time < 0){
            time = 0L;
        }
        if(views < 0){
            views = 0L;
        }
        this.time = time;
        this.views = views;

        if(this.time > 0){
            restrictedTime = true;
        }
        if(this.views > 0){
            restrictedViews = true;
        }

        if(this.time > 0 || this.views > 0){
            this.restricted = true;
        }

        this.terminateDate = preciseDate.plusSeconds(time);

    }

    public String formatDate(LocalDateTime dateToFormat){
        final String DATE_FORMATTER= "yyyy/MM/dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        String formatDateTime = dateToFormat.format(formatter);
        return formatDateTime;
    }

    // zamiast boilerplate kodu ja wciskam lomboka gdzie się da i robię to adnotacjami jak wyżej -> @Getter, @Setter, @Builder itp
//    public Boolean isRestricted() {
//        return restricted;
//    }
//
//    public long getTime() {
//        return time;
//    }
//
//    public void setTime(long time) {
//        this.time = time;
//    }
//
//    public Long getViews() {
//        return views;
//    }
//
//    public void setViews(Long views) {
//        this.views = views;
//    }
//
//    public LocalDateTime getPreciseDate() {
//        return preciseDate;
//    }
//
//    public String getDate() {
//        return date;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public Boolean isRestrictedTime() {
//        return restrictedTime;
//    }
//
//    public Boolean isRestrictedViews() {
//        return restrictedViews;
//    }
//
//    public LocalDateTime getTerminateDate() {
//        return terminateDate;
//    }
}
