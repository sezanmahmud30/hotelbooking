package com.sezanmahmud.hotelbooking.service;

import com.sezanmahmud.hotelbooking.entity.Location;
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
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Value("src/main/resources/static/images")
    private String uploadDir;

    public List<Location> getAllLocations() {

        return locationRepository.findAll();

    }


    public void saveLocation(Location l, MultipartFile imageFile) throws IOException {


        if(imageFile != null && !imageFile.isEmpty()) {
            String imageFileName = saveImage(imageFile,l);

            l.setImage(imageFileName);
        }
       locationRepository.save(l);

    }

    public Location getLocationById(int id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with Id: " + id));


    }

    public void deleteLocation(int id) {
        if (!locationRepository.existsById(id)) {
            throw new EntityNotFoundException("Location not found with Id: " + id);
        } else {
            locationRepository.deleteById(id);
        }
    }

    public Location updateLocation(int id, Location updateLocation,MultipartFile image) throws IOException {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with Id: " + id));

        if (updateLocation.getName() != null) {
            existingLocation.setName(updateLocation.getName());

        }

        if(image != null && !image.isEmpty()) {

            String fileName=saveImage(image,existingLocation);
            existingLocation.setImage(fileName);

        }

        return locationRepository.save(existingLocation);
    }

    public void updateLocation(Location updateLocation) {
        locationRepository.save(updateLocation);

    }
    
    private String saveImage(MultipartFile file, Location l) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/locations");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }


        String fileName = l.getName() + "_" + UUID.randomUUID().toString();
        //Sezan Mahmud_gddsuhkjsdjxfu

        Path filePath=uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(), filePath);

        return fileName;

    }

}
