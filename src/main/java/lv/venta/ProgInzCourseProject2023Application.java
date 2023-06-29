package lv.venta;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lv.venta.models.Comment;
import lv.venta.models.Course;
import lv.venta.models.Thesis;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Degree;
import lv.venta.models.users.Student;
import lv.venta.models.users.User;
import lv.venta.repos.ICommentRepo;
import lv.venta.repos.ICourseRepo;
import lv.venta.repos.IThesisRepo;
import lv.venta.repos.users.IAcademicPersonelRepo;
import lv.venta.repos.users.IPersonRepo;
import lv.venta.repos.users.IStudentRepo;
import lv.venta.repos.users.IUserRepo;

@SpringBootApplication
public class ProgInzCourseProject2023Application {

	public static void main(String[] args) {
		SpringApplication.run(ProgInzCourseProject2023Application.class, args);
	}

	@Bean
	public CommandLineRunner testModelLayer(IUserRepo userRepo, IPersonRepo personRepo,
			IStudentRepo studentRepo, IAcademicPersonelRepo personalRepo, 
			ICourseRepo courseRepo, IThesisRepo thesisRepo, ICommentRepo commentRepo ) {
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				User us1 = new User("123", "karina.krinkele@venta.lv");//pasniedzējs
				User us2 = new User("123", "karlis.immers@venta.lv");//pasniedzējs
				User us3 = new User("123", "janis.berzins@venta.lv");//stundents
				User us4 = new User("123", "baiba.kalnina@venta.lv");//students
				userRepo.save(us1);
				userRepo.save(us2);
				userRepo.save(us3);
				userRepo.save(us4);
				
				Course c1 = new Course("Javaa", 4);
				Course c2 = new Course("Datastr", 2);
				courseRepo.save(c1);
				courseRepo.save(c2);
				
				
				AcademicPersonel ac1 = new AcademicPersonel("Karina", "Skirmante", 
						"121212-12121", us1, Degree.mg);
				AcademicPersonel ac2 = new AcademicPersonel("Karlis", "Immers", 
						"121212-12123", us2, Degree.mg);
				personalRepo.save(ac1);
				personalRepo.save(ac2);
				
				Student s1 = new Student("Janis", "Berzins", 
						"211221-34567", us3, "12345678", false);
				Student s2 = new Student("Baiba", "Kalnina", 
						"121256-98765", us4, "12899876", true);
				s2.addDebtCourse(c1);
				s2.addDebtCourse(c2);
				studentRepo.save(s1);
				studentRepo.save(s2);
				c1.addStudent(s2);
				c2.addStudent(s2);
				courseRepo.save(c1);
				courseRepo.save(c2);
				
				Thesis th1 = new Thesis("Sistēmas izstrāde", "Development of System",
						"Development", "1...2.3..4", s1, ac1);
				Thesis th2 = new Thesis("Pētījums", "Research",
						"Research", "1...2.3..4", s2, ac2);
				
				th1.addReviewer(ac2);
				th2.addReviewer(ac1);
				thesisRepo.save(th1);
				thesisRepo.save(th2);
				ac1.addThesisForReviews(th2);
				ac2.addThesisForReviews(th1);
				personalRepo.save(ac1);
				personalRepo.save(ac2);
				
				
				Comment com1 = new Comment("Neprecīzs nosaukums", ac2, th1);
				Comment com2 = new Comment("Mērķi nav atbilstoši", ac1, th1);
				commentRepo.save(com1);
				commentRepo.save(com2);
				
			}
		};
	}
	
}
