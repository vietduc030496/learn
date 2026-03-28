package com.example.spring_data_jpa.repository;

import com.example.spring_data_jpa.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query("SELECT a FROM Artist a JOIN FETCH a.posts JOIN FETCH a.tags")
    List<Artist> findAllArtist();
}
