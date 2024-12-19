package com.sezanmahmud.hotelbooking.restcontroller;


import com.sezanmahmud.hotelbooking.entity.Location;
import com.sezanmahmud.hotelbooking.service.LocationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/location")
public class LocationController {

@Autowired
private LocationService locationService;



    @GetMapping("/")
    public ResponseEntity<List<Location>> getAllLocations(){

        List<Location> LocationList = locationService.getAllLocations();

        return ResponseEntity.ok(LocationList);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveStudents
            (@RequestPart Location l,
             @RequestParam(value="image",required=true) MultipartFile file) throws IOException {

        locationService.saveLocation(l,file);


        return new ResponseEntity<>("Location saved Successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String>deleteLocation(@PathVariable  int id) {

        try {
            locationService.deleteLocation(id);
            return ResponseEntity.ok("Location with this ID " + id + " has been Deleted");

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable int id,@RequestBody Location l){

        Location updateLocation = locationService.updateLocation(id, l);

        return ResponseEntity.ok(updateLocation);
    }

}
