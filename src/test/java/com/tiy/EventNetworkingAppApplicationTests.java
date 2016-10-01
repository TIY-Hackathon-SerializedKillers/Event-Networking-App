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

	@Autowired
	EventRepository events;

	@Autowired
	UserEventRepository userEvents;

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










/******* The following methods are just for inserting test data into the database. ***********/
/******* They are not actually unit tests. ***************************************************/

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

//	@Test
//	public void testEventInsert() {
//		System.out.println("Putting a test event into db");
//
//		String testName = "testEvent";
//		String testLocation = "testLocation";
//		String testDate = "1/1/16";
//		String testTime = "12:00";
//		String testTechSkills = "testSkills";
//
//		Event testEvent = new Event(testName, testLocation, testDate, testTime);
//		events.save(testEvent);
//	}


// 	@Test
//	public void deleteExtraTestDonaldsInUserEvents() {
//		userEvents.deleteAll();
//	}



}
