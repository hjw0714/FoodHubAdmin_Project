package com.application.foodhubAdmin;

import com.application.foodhubAdmin.domain.User;
import com.application.foodhubAdmin.repository.UserMsRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FoodhubAdminApplicationTests {

	@Autowired
	UserMsRepository userMsRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	void contextLoads() {
	}

	@Test
	@Order(1)
	void updateMembership() {
		User user = userMsRepository.findById("3333").orElseThrow(() -> new RuntimeException("test"));
		String membership = "사업자";
		user.updateMemberShipType(membership);
		if(user.getMembershipType().equals("COMMON")){
			Assertions.assertEquals("COMMON", user.getMembershipType());
		}
		else if(user.getMembershipType().equals("BUSSI")){
			Assertions.assertEquals("BUSSI", user.getMembershipType());
		}
	}

	@Test
	@Order(2)
	void deleteUser() {
		User user = userMsRepository.findById("3333").orElseThrow(() -> new RuntimeException("test"));
		user.deleteMember();
		Assertions.assertNotNull(user.getDeletedAt());
	}

	@Test
	@Order(3)
	void notDeleteUser() {
		User user = userMsRepository.findById("3333").orElseThrow(() -> new RuntimeException("test"));
		user.notDeleteMember();
		Assertions.assertNull(user.getDeletedAt());
	}

	@Test
	@Order(4)
	void changePassword() {
		String newPassword = "123456";
		User user = userMsRepository.findById("3333").orElseThrow(() -> new RuntimeException("test"));
		user.changePasswd(passwordEncoder.encode(newPassword));
		Assertions.assertEquals(true, passwordEncoder.matches(newPassword, user.getPasswd()));
	}

}
