package com.example.spring_data_jpa;

import com.example.spring_data_jpa.entity.*;
import com.example.spring_data_jpa.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepo,
						   DepartmentRepository deptRepo,
						   ArtistRepository artistRepository,
						   PostRepository postRepository,
						   TagRepository tagRepository) {
		return args -> {
//			initUser(userRepo, deptRepo);
//			initArtist(artistRepository, postRepository);
		};
	}

	private void initUser(UserRepository userRepo, DepartmentRepository deptRepo) {
		Department it = new Department();
		it.setDepartmentName("IT");

		Department hr = new Department();
		hr.setDepartmentName("HR");

		it = deptRepo.save(it);
		hr = deptRepo.save(hr);

		for (int i = 1; i <= 2; i++) {
			User u = new User();
			u.setUsername("User " + i);
			u.setDepartment(i % 2 == 0 ? it : hr);
			userRepo.save(u);
		}
	}

	@Transactional
	public void initArtist(ArtistRepository artistRepository, PostRepository postRepository) {
		Post post1 = new Post();
		post1.setContent("Post 1");

		Post post2 = new Post();
		post2.setContent("Post 2");

		Post post3 = new Post();
		post3.setContent("Post 3");

		Set<Post> posts = new HashSet<>();
		posts.add(post1);
		posts.add(post2);
		posts.add(post3);

		Tag tag1 = new Tag();
		tag1.setTagName("Pop");

		Tag tag2 = new Tag();
		tag2.setTagName("Rock");

		Tag tag3 = new Tag();
		tag3.setTagName("EDM");

		Artist artist = new Artist();
		artist.setArtistName("Artist 1");
		artist.setPosts(posts);
		artist.setTags(Set.of(tag1, tag2, tag3));

		artistRepository.save(artist);
	}

}
