package pro.abacus.javatutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class that bootstraps this project.
 */
@SpringBootApplication
public class JavaTutorApplication {
	/**
	 * Main method, used to run this application.
	 *
	 * @param args the string array, that contains command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(JavaTutorApplication.class, args);
	}
}