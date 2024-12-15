package com.farmstory.controller.article;

import com.farmstory.dto.CateDTO;
import com.farmstory.dto.EventDTO;
import com.farmstory.entity.Event;
import com.farmstory.service.CategoryService;
import com.farmstory.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class EventController {


    private final CategoryService categoryService;
    private final EventService eventService;


    @PostMapping("/admin/events")
    public ResponseEntity<String> addEvent(@RequestBody EventDTO event) {
        log.info(event);
        log.info(event);
        EventDTO  savedEvent = eventService.addEvent(event);
        if(savedEvent != null) {
            return ResponseEntity.ok(savedEvent.toString());
        }


        // 이벤트 저장 로직 구현 (예: 데이터베이스에 저장)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Event could not be saved");
    }
    @DeleteMapping("/admin/events/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable int id) {
        // 이벤트 삭제 로직
        boolean isDeleted = eventService.deleteEvent(id);

        if (isDeleted) {
            // 삭제 성공 시
            return ResponseEntity.ok("Event deleted successfully");
        } else {
            // 삭제 실패 시 (예: 이벤트가 존재하지 않음)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Event not found or could not be deleted");
        }
    }


    @GetMapping("/event/calendar")
    public List<Event> getEvents() {
        //이벤트 401

        List<Event>  eventList = eventService.getAllEvents();


        return eventList;  // JSON 형식으로 반환됨
    }

}
