package com.yummy.repository;

import com.yummy.modal.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {

    Track getTrackById(Long id);

    Track getTrackByOrderid(Long orderid);

}
