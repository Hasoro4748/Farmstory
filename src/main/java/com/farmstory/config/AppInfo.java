package com.farmstory.config;

import jakarta.persistence.PrePersist;
import jakarta.persistence.Version;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppInfo {

    @Value("${spring.application.version}")
    private String appVersion;


    @Value("${spring.application.name}")
    private String appName;



//    @Value("${spring.application.name}")
//    private String appName;
//
//    @Value("${spring.application.version}")
//    private String appVersion;





}

