package com.example.spring_data_jpa.controller;

import com.example.spring_data_jpa.entity.Artist;
import com.example.spring_data_jpa.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/artists")
@AllArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtist() {
        List<Artist> artists = artistService.getAllArtist();

        for (Artist artist : artists) {
            System.out.println(artist.getArtistName() + " - " + artist.getPosts().size());
        }

        return ResponseEntity.ok(artists);
    }

    @GetMapping("/tag")
    public ResponseEntity<String> testTag() {
        artistService.testTag();
        return ResponseEntity.ok("Success");
    }
}
