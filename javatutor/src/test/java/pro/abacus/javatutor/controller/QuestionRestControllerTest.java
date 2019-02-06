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
import pro.abacus.javatutor.domain.JavaQuestion;
import pro.abacus.javatutor.service.JavaQuestionsService;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(QuestionRestController.class)
@AutoConfigureMockMvc(secure = false)
public class QuestionRestControllerTest {

	@MockBean
	private JavaQuestionsService javaQuestionsService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private QuestionRestController questionRestController = new QuestionRestController(javaQuestionsService);

	public static String javaQuestionInJson(String question, String answer) {
		return "[{ \"question\": \"" + question + "\", " +
				"\"answer\":\"" + answer + "\"}]";
	}


	@Test
	public void shouldReadJavaQuestions() throws Exception {
		mockMvc.perform(get("/api/javaquestions")).andExpect(status().isOk());

		verify(javaQuestionsService).findAllQuestions();
	}

	@Test
	public void shouldPostJavaQuestions() throws Exception {
		mockMvc.perform(post("/api/javaquestions").contentType(MediaType.APPLICATION_JSON)
				.content(javaQuestionInJson("What is java object", "Java object is a complex entity with fields and methods")))
				.andExpect(status().isOk());

	}

	@Test
	public void shouldSaveJavaQuestions() {
		Collection<JavaQuestion> collection = new ArrayList<JavaQuestion>();
		questionRestController.writeJavaQuestions(collection);
		verify(javaQuestionsService).saveAllQuestions(collection);

	}

}
