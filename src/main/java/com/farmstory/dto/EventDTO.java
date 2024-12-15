package com.farmstory.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private int id;        // 이벤트 ID
    private String title;   // 이벤트 제목
    private LocalDate start; // 시작일
    private LocalDate end;   // 종료일 (선택)

}
