package com.sezanmahmud.hotelbooking.service;


import com.sezanmahmud.hotelbooking.entity.Hotel;
import com.sezanmahmud.hotelbooking.entity.Location;
import com.sezanmahmud.hotelbooking.repository.HotelRepository;
import com.sezanmahmud.hotelbooking.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Value("src/main/resources/static/images")
    private String uploadDir;

    public List<Hotel> getAllHotels() {

        return hotelRepository.findAll();

    }

    public void saveHotel(Hotel h, MultipartFile imageFile) throws IOException {

        if(imageFile != null && !imageFile.isEmpty()) {
            String imageFileName = saveImage(imageFile,h);

            h.setImage(imageFileName);
        }

        Location location = locationRepository.findById(h.getLocation().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Location not found with Id: " +h.getLocation().getId()));

        h.setLocation(location);

        hotelRepository.save(h);

    }

    public Hotel findHotelById(int id) {

        return hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with Id: " + id));
    }



    public Hotel findHotelByName(String name) {

        return hotelRepository.findByName(name)

                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with Name: " + name));
    }



    public Hotel updateHotel(int id, Hotel updateHotel, MultipartFile image) throws IOException {

        Hotel existingHotel = hotelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found with Id: " + id));


        existingHotel.setName(updateHotel.getName());
        existingHotel.setAddress(updateHotel.getAddress());
        existingHotel.setRating(updateHotel.getRating());
        existingHotel.setMaximumprice(updateHotel.getMaximumprice());
        existingHotel.setMinimumprice(updateHotel.getMinimumprice());

        //Update Location -

        Location location = locationRepository.findById(updateHotel.getLocation().getId())
                .orElseThrow(() -> new EntityNotFoundException("Location not found with Id: " +updateHotel.getLocation().getId()));

        existingHotel.setLocation(location);


        //Update Image -

        if(image != null && !image.isEmpty()) {

            String filename=saveImage(image,existingHotel);
            existingHotel.setImage(filename);

        }

        return updateHotel;
    }

    public List<Hotel> findHotelByLocationName(String locationName) {

        return hotelRepository.findHotelByLocationName(locationName);
    }


    public void deleteHotel(int id) {
        if (!hotelRepository.existsById(id)) {
            throw new EntityNotFoundException("Hotel not found with Id: " + id);
        } else {
            hotelRepository.deleteById(id);
        }
    }


    private String saveImage(MultipartFile file, Hotel h) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/hotels");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = h.getName()+"_"+ UUID.randomUUID().toString();
        //Sezan Mahmud_gddsuhkjsdjxfu

        Path filePath=uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath);

        return fileName;
    }

}
