/**
 * Tests different CTC login possibilities
 * @author meyling
 */
package ctc;

import static org.junit.Assert.*;

import org.junit.Test;

public class LoginTest 
{

	@Test public void testLogin() 
	{
		LoginController loginTester = new LoginController();
		User user = loginTester.login("root", "password");
		assertNotNull("Username: root, Password: password is a valid user", user);
		assertEquals("User has correct name: root", "root", user.name);
		assertTrue("User has dispatcher permissions", user.isDispatcher());
		assertTrue("User has maintenance permissions", user.isMaintenance());
		assertTrue("User has track creator permissions", user.isTrackCreator());
		assertFalse("User does not have admin permissions", user.isAdmin());
	}
	
	@Test public void testLoginNoUser() 
	{
		LoginController loginTester = new LoginController();
		User user = loginTester.login("poo", "poo2");
		assertNull("Invalid credentials does not create user object", user);
	}
	
	@Test public void testLoginWrongPw() 
	{
		LoginController loginTester = new LoginController();
		User user = loginTester.login("root", "wrong");
		assertNull("Wrong password does not create user object", user);
	}
	
	@Test public void testLoginBlank()
	{
		LoginController loginTester = new LoginController();
		User user = loginTester.login("", "");
		assertNull("Username and password blank does not create user object", user);
	}

}
