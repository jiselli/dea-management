package br.com.dea.management;

import br.com.dea.management.student.domain.Student;
import br.com.dea.management.student.repository.StudentRepository;
import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.repository.UserRepository;
import br.com.dea.management.user.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "Dea Management", version = "1.0", description = "Dea Management API Description"),
		servers = {
				@Server(url = "http://localhost:8080${server.servlet.contextPath}", description = "Local environment URL"),
				@Server(url = "https://deamanagement.com.br${server.servlet.contextPath}", description = "Development environment URL")
		}
)
public class DeaManagementApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DeaManagementApplication.class, args);
	}

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StudentRepository studentRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void run(String... args) throws Exception {
		//Deleting all Users
//		this.userRepository.deleteAll();
//
//		//Creating some students
		for (int i = 0; i < 100; i++) {
			User u = new User();
			u.setEmail("email " + i);
			u.setName("name " + i);
			u.setLinkedin("linkedin " + i);
			u.setPassword("pwd " + i);

//			Student student = Student.builder()
//					.university("UNI " + i)
//					.graduation("Grad " + i)
//					.finishDate(LocalDate.now())
//					.user(u)
//					.build();
			Student student = new Student();
			student.setUniversity("UNI " + i);
			student.setGraduation("Grad " + i);
			student.setFinishDate(LocalDate.now());
			student.setUser(u);

//			this.studentRepository.save(student);
		}
//
//
//		//Loading all Users
//		List<User> users = this.userService.findAllUsers();
//		users.forEach(u -> System.out.println("Name: " + u.getName()));
//
//		//Loading by @Query
//		Optional<User> loadedUserByName = this.userRepository.findByName("name 1");
//		System.out.println("User name 1 (From @Query) name: " + loadedUserByName.get().getName());
//
//		TypedQuery<User> q = entityManager.createNamedQuery("myQuery", User.class);
//		q.setParameter("name", "name 2");
//		User userFromNamedQuery = q.getResultList().get(0);
//		System.out.println("User name 2 (From NamedQuery) name: " + userFromNamedQuery.getName());
//
//		//Loading user by email
//		User loadedUser = this.userService.findUserByEmail("email 1");
//		System.out.println("User email 1 name: " + loadedUser.getName());
//
//		//Updating user name 1 linkedin
//		loadedUser.setLinkedin("new linkedin");
//		this.userRepository.save(loadedUser);

	}
}
