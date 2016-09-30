package com.tiy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventNetworkingAppApplicationTests {

	@Autowired
	UserRepository users;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testUserIntoDatabase() {
		System.out.println("Testing inserting user into db");

		String testEmail = "test::email";
		String testPassword = "test::password";
		String testFirstName = "test::firstName";
		String testLastName = "test::lastName";
		String testTechSkills = "test::techSkills";

		User testUser = new User(testEmail, testPassword, testFirstName, testLastName, testTechSkills);
		users.save(testUser);

//		assertEquals(1, users.count());
		User retrievedUser = users.findOne(testUser.getId());
		assertNotNull(retrievedUser);

		users.delete(testUser);
//		assertEquals(0, users.count());
		retrievedUser = users.findOne(testUser.getId());
		assertNull(retrievedUser);

	}

//	@Test
//	public void testRegisterUser() {
//		System.out.println("Testing inserting user into db");
//
//		String testEmail = "test::email";
//		String testPassword = "test::password";
//		String testFirstName = "test::firstName";
//		String testLastName = "test::lastName";
//		String testTechSkills = "test::techSkills";
//
//
//
//		User testUser = new User(testEmail, testPassword, testFirstName, testLastName, testTechSkills);
//		users.save(testUser);
//
//		NetworkingJSONController jsonController  = new NetworkingJSONController();
//
//		jsonController.register(testUser);
//
//
////		assertEquals(1, users.count());
//		User retrievedUser = users.findOne(testUser.getId());
//		assertNotNull(retrievedUser);
//
//		users.delete(testUser);
////		assertEquals(0, users.count());
//		retrievedUser = users.findOne(testUser.getId());
//		assertNull(retrievedUser);
//
//	}


//	@Test
//	public void testUserInsert() {
//		System.out.println("Putting a test user into db");
//
//		String testEmail = "test@tiy.com";
//		String testPassword = "password";
//		String testFirstName = "testDonald";
//		String testLastName = "testGowens";
//		String testTechSkills = "Java master EXTRAORDINAIRE!!!!";
//
//		User testUser = new User(testEmail, testPassword, testFirstName, testLastName, testTechSkills);
//		users.save(testUser);
//
//	}



}
