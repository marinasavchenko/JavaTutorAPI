package pro.abacus.javatutor.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pro.abacus.javatutor.domain.JavaQuestion;
import pro.abacus.javatutor.repository.JavaQuestionRepository;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JavaQuestionsServiceImplTest {

	private JavaQuestionsService javaQuestionService;

	private ArrayList<JavaQuestion> javaQuestions;

	@Mock
	private JavaQuestionRepository javaQuestionRepository;

	@Before
	public void setup() {
		javaQuestionService = new JavaQuestionsServiceImpl(javaQuestionRepository);
		javaQuestions = new ArrayList<>();
	}

	@Test
	public void shouldFindAllQuestions() throws Exception {
		javaQuestionService.findAllQuestions();
		verify(javaQuestionRepository).findAll();
	}

	@Test
	public void shouldSaveAllQuestions() throws Exception {
		javaQuestionService.saveAllQuestions(javaQuestions);
		verify(javaQuestionRepository).saveAll(javaQuestions);
	}

}
