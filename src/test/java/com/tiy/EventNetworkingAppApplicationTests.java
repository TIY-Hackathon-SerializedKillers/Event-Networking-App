package com.tiy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventNetworkingAppApplicationTests {

	@Autowired
	UserRepository users;

	@Autowired
	EventRepository events;

	@Autowired
	FriendRepository friends;

	@Autowired
	UserEventRepository userEvents;

	NetworkingJSONController testJSONController = new NetworkingJSONController();
	FriendConnectionContainer friendConnectionContainer = new FriendConnectionContainer();


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

	@Test
	public void testConnectUser(){

		System.out.println("Testing inserting user into db");

		String testEmail = "friendTest::email";
		String testPassword = "friendTest::password";
		String testFirstName = "friendTest::firstName";
		String testLastName = "friendTest::lastName";
		String testTechSkills = "friendTest::techSkills";

		User testUser = new User(testEmail, testPassword, testFirstName, testLastName, testTechSkills);
		users.save(testUser);

		System.out.println("User added: " + testUser);

		String friendTestEmail = "friend2Test::email";
		String friendTestPassword = "friend2Test::password";
		String friendTestFirstName = "friend2Test::firstName";
		String friendTestLastName = "friend2Test::lastName";
		String friendTestTechSkills = "friend2Test::techSkills";

		User testFriend = new User(friendTestEmail, friendTestPassword, friendTestFirstName, friendTestLastName, friendTestTechSkills);
		users.save(testFriend);

		System.out.println("User added: " + testFriend);

		try {
			testJSONController.requestContact(friendConnectionContainer);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		System.out.println("Connection established");

		friends.findAllByUserId(testFriend.getId());
		System.out.println("Friends for userID " + testFriend.getId() + ": " + friends.findAllByUserId(testFriend.getId()));

//		assertTrue();



//		Delete User
		User retrievedUser = users.findOne(testUser.getId());
		users.delete(testUser);
		retrievedUser = users.findOne(testUser.getId());
		assertNull(retrievedUser);

		User retrievedFriend = users.findOne(testFriend.getId());
		users.delete(testFriend);
		retrievedUser = users.findOne(testFriend.getId());
		assertNull(retrievedFriend);
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

 	@Test
	public void testInsertANewUserTestFriendForTestDonald() {
 		User testUser = users.findOne(20);
		Friend testFriend = new Friend(testUser);
		friends.save(testFriend);
	}

//	@Test
//	public void deleteFriendPerson() {
//		friends.delete(34);
//	}


}
