package com.cube.services.room.roomservices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/rooms")
@Api(value = "rooms", description = "Data Services operations on rooms", tags = ("rooms") )
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping
    @ApiOperation(value = "Get All Rooms",notes = "Get all rooms in the system", nickname = "getRooms")
    public List<Room> findAll(@RequestParam(name = "roomNumber", required = false) String roomNumber){
        if(StringUtils.isNotEmpty(roomNumber)) {
            return Collections.singletonList(this.roomRepository.findByRoomNumber(roomNumber));
        }

        System.out.println("Find Room details for this room number" + roomNumber);
        return (List<Room>) this.roomRepository.findAll();

    }
}
