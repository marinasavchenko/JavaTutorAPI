package pro.abacus.javatutor.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pro.abacus.javatutor.domain.User;
import pro.abacus.javatutor.service.UserRegistrationService;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
@AutoConfigureMockMvc(secure = false)
public class RegistrationControllerTest {

	private static final String ANY_USER_NAME = "Brigette";
	private static final String PASSWORD = "12345678";
	private static final String URI_SIGNUP = "/api/signup";

	@MockBean
	private UserRegistrationService userService;

	@MockBean
	private User user;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	final private RegistrationController registrationController = new RegistrationController(userService);

	private static String userInJson(String name, String password) {
		return "{ \"name\": \"" + name + "\", " +
				"\"password\":\"" + password + "\"}";
	}

	@Test
	public void shouldPostRegistrationDetails() throws Exception {

		mockMvc.perform(post(URI_SIGNUP)
				.contentType(MediaType.APPLICATION_JSON)
				.content(userInJson(ANY_USER_NAME, PASSWORD)))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldSaveUser() throws Exception {
		registrationController.registerUser(user);

		verify(userService).saveUserAccount(user);
	}

}
