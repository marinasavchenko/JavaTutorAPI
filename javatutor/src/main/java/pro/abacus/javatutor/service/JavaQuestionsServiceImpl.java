package pro.abacus.javatutor.service;

import org.springframework.stereotype.Service;
import pro.abacus.javatutor.domain.JavaQuestion;
import pro.abacus.javatutor.repository.JavaQuestionRepository;

import java.util.Collection;

@Service
public class JavaQuestionsServiceImpl implements JavaQuestionsService {

	private final JavaQuestionRepository javaQuestionRepository;

	public JavaQuestionsServiceImpl(JavaQuestionRepository javaQuestionRepository) {
		this.javaQuestionRepository = javaQuestionRepository;
	}

	@Override
	public Collection<JavaQuestion> findAllQuestions() {
		return javaQuestionRepository.findAll();
	}

	@Override
	public void saveAllQuestions(Collection<JavaQuestion> javaQuestions) {
		javaQuestionRepository.saveAll(javaQuestions);
	}

}
