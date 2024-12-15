package com.farmstory.service;

import com.farmstory.dto.EventDTO;
import com.farmstory.entity.Event;
import com.farmstory.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class EventService {


    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;


    public List<Event> getAllEvents() {
        List<Event> events= eventRepository.findAll();
          if(events.size()==0){
              return null;

          }

        return events;
    }

    public EventDTO addEvent(EventDTO eventdto) {
        Event savedEvent = eventRepository.save(modelMapper.map(eventdto, Event.class));

        return modelMapper.map(savedEvent, EventDTO.class);
    }

    public boolean deleteEvent(int id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        } else {
            return false;  // 해당 ID의 이벤트가 존재하지 않음
        }
    }
}
