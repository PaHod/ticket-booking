package com.pahod.repository;

import com.pahod.model.Event;

import java.util.List;

interface IEventDAO {


    List<Event> getAllEvents();
}
