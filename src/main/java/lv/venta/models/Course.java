package lv.venta.models;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.venta.models.users.Student;

@Table(name = "course_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
//@ToString
public class Course {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "Idc")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idc;
	
	@Column(name = "Title")
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam")
	@Size(min = 5, max = 25)
	private String title;
	
	@Min(value = 1)
	@Max(value = 20)
	@Column(name = "CreditPoints")
	private int creditPoints;
	
	@ManyToMany(mappedBy = "debtCourses")
	private Collection<Student> debtStudents = new ArrayList<>();
	
	
	public Course(
			@NotNull @Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ]{1}[a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam") @Size(min = 5, max = 25) String title,
			@Min(1) @Max(20) int creditPoints) {
		this.title = title;
		this.creditPoints = creditPoints;
		
	}
	
	public void addStudent(Student student) {
		if(!debtStudents.contains(student)) {
			debtStudents.add(student);
		}
	}
	
	
	//TODO izveidot removeStudent
	
}
