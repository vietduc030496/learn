package com.example.spring_data;

import com.example.spring_data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

//	@Test
//	@Transactional
//	void save() {
//		UserEntity user = new UserEntity();
//		user.setUserEmail("email");
//		user.setUserName("admin");
//
//		DepartmentEntity department = new DepartmentEntity();
//		department.setDepartmentName("<UNK>");
//		user.setDepartment(department);
//		user = userRepository.save(user);
//		Assertions.assertNotNull(user);
//	}

//	@Test
//	@Transactional
//	void testCascade() {
//		Optional<UserEntity> option = userRepository.findById(1L);
//
//		if (option.isEmpty()) {
//			return;
//		}
//
//		UserEntity user = option.get();
//		userRepository.delete(user);
//
//		option = userRepository.findById(1L);
//		Assertions.assertTrue(option.isEmpty());
//	}

}
