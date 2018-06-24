package pro.abacus.javatutor.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pro.abacus.javatutor.domain.JavaQuestion;
import pro.abacus.javatutor.services.JavaQuestionsService;

@RestController
@RequestMapping("/api")
public class QuestionRestController {

	private JavaQuestionsService javaQuestionsService;

	@Autowired
	public QuestionRestController(JavaQuestionsService javaQuestionsService) {
		this.javaQuestionsService = javaQuestionsService;
	}

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping(value = "/javaquestions")
	public Collection<JavaQuestion> readAllJavaQuestions() {
		return this.javaQuestionsService.findAllQuestions();
	}

	@PostMapping(value = "/javaquestions", consumes = "application/json")
	public void writeJavaQuestions(@RequestBody Collection<JavaQuestion> javaQuestions) {
		javaQuestionsService.saveAllQuestions(javaQuestions);

	}

}
