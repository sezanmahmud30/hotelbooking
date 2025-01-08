package com.sezanmahmud.hotelbooking.repository;


import com.sezanmahmud.hotelbooking.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository  extends JpaRepository<Location, Integer> {

}
