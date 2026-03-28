package com.example.spring_data_jpa.service;

import com.example.spring_data_jpa.entity.Artist;
import com.example.spring_data_jpa.entity.Tag;
import com.example.spring_data_jpa.repository.ArtistRepository;
import com.example.spring_data_jpa.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final TagRepository tagRepository;

    public List<Artist> getAllArtist() {
        return artistRepository.findAllArtist();
    }

    @Transactional
    public void testTag() {
        Optional<Tag> optional = tagRepository.findById(1L);
        if (optional.isPresent()) {
            Tag tag = optional.get();
            tag.getArtists().add(new Artist());

            tagRepository.save(tag);
        }
    }
}
