package com.sezanmahmud.hotelbooking.restcontroller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sezanmahmud.hotelbooking.entity.Hotel;
import com.sezanmahmud.hotelbooking.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/")
    public ResponseEntity<List<Hotel>> getAllHotels(){

        List<Hotel>hotels = hotelService.getAllHotels();

        return ResponseEntity.ok(hotels);
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,String>> saveHotel(
                 @RequestPart(value = "hotel")String hotelJson,
                @RequestParam(value = "image")MultipartFile file

    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Hotel hotel = objectMapper.readValue(hotelJson,Hotel.class);

        try{

            hotelService.saveHotel(hotel,file);
            Map<String,String> response = new HashMap<>();
            response.put("Message","Hotel Added Successfully");

            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e){

            Map<String,String> errorresponse = new HashMap<>();
            errorresponse.put("Message","Hotel Add Failed");

            return new ResponseEntity<>(errorresponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    @GetMapping("/{id}")
    public ResponseEntity<Hotel> findHotelById(@PathVariable int id){
        try{
            Hotel hotel = hotelService.findHotelById(id);

            return ResponseEntity.ok(hotel);
        }
        catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/h/searchhotel")
    public ResponseEntity<List<Hotel>> findHotelByLocationName(@RequestParam(value = "locationName")String locationName){

        List<Hotel>hotels = hotelService.findHotelByLocationName(locationName);

        return ResponseEntity.ok(hotels);
    }


}
