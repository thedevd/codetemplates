package com.dev.bcryptpasswordencoder;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.dev.bcryptpasswordencoder.pojo.User;
import com.dev.bcryptpasswordencoder.repository.UserRepository;
import com.dev.bcryptpasswordencoder.service.UserService;

@RunWith(SpringRunner.class)
public class UserServiceTest {

	@TestConfiguration
	static class EmployeeServiceTestContextConfiguration {

		@Bean
		public UserService userService() {
			return new UserService();
		}
	}

	@MockBean
	UserRepository repositoryMock;

	@Autowired
	UserService userService;

	@Before
	public void setUp() {
		User admin = new User();
		admin.setUserEmail("admin@gmail.com");
		admin.setUserPassword("$2a$10$vuv8ej/tsSKsuRkqhjhbae9iEL0jn1YCAOAvRrT4qf2b19iheOEmW");

		Optional<User> userOptional = Optional.of(admin);

		Mockito.when(repositoryMock.findById(admin.getUserEmail())).thenReturn(userOptional);
	}

	@Test
	public void whenNewUserIsAdded() {

		User newUser = new User();
		newUser.setUserEmail("new@gmail.com");
		newUser.setUserPassword("password");

		String message = userService.addUser(newUser);
		org.junit.Assert.assertEquals("User: " + newUser.getUserEmail() + " is added", message);
	}

	@Test
	public void whenExistingUserIsAdded() {

		User existingUser = new User();
		existingUser.setUserEmail("admin@gmail.com");
		existingUser.setUserPassword("@dmin");

		String message = userService.addUser(existingUser);
		org.junit.Assert.assertEquals("User with email: " + existingUser.getUserEmail() + " already exist", message);
	}

	@Test
	public void whenExistingUserIsLoggedInWithRightPassword() {
		User existingUser = new User();
		existingUser.setUserEmail("admin@gmail.com");
		existingUser.setUserPassword("@dmin");

		String message = userService.verifyUser(existingUser);
		org.junit.Assert.assertEquals("Password matches", message);
	}

	@Test
	public void whenExistingUserIsLoggedInWithWrongPassword() {
		User existingUser = new User();
		existingUser.setUserEmail("admin@gmail.com");
		existingUser.setUserPassword("@dmin1");

		String message = userService.verifyUser(existingUser);
		org.junit.Assert.assertEquals("Password incorrect", message);
	}

	@Test
	public void whenNonExistingUserIsLoggedIn() {
		User noExistingUser = new User();
		noExistingUser.setUserEmail("new@gmail.com");
		noExistingUser.setUserPassword("password");

		String message = userService.verifyUser(noExistingUser);
		org.junit.Assert.assertEquals("userName does not exist", message);
	}

}
