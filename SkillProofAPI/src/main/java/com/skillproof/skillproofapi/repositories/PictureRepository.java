package com.skillproof.skillproofapi.repositories;

import com.skillproof.skillproofapi.model.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
