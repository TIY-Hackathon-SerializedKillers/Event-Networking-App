package com.tiy;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by jessicatracy on 9/29/16.
 */
public interface EventRepository extends CrudRepository<Event, Integer> {

    List<Event> findByText(String text);

}
