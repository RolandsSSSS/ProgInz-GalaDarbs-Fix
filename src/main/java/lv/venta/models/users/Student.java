package lv.venta.models.users;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.models.Course;
import lv.venta.models.Thesis;

@Table(name = "student_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AttributeOverride(name = "Idp", column = @Column(name = "Idp"))
public class Student extends Person {

	@Column(name = "matriculaNo")
	@NotNull
	// @Size(min = 8, max = 20)
	@Pattern(regexp = "\\d{8,20}")
	private String matriculaNo;

	@Column(name = "FinancialDebt")
	private boolean financialDebt;

	@ManyToMany
	@JoinTable(name = "student_debt_courses_table", joinColumns = @JoinColumn(name = "Ids"), inverseJoinColumns = @JoinColumn(name = "Idc"))

	private Collection<Course> debtCourses = new ArrayList<>();

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Thesis> thesis;

	public Student(
			@NotNull @Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ][a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam") @Size(min = 3, max = 15) String name,
			@NotNull @Size(min = 3, max = 15) @Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ][a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam") String surname,
			@NotNull @Size(min = 12, max = 12) @Pattern(regexp = "\\d{6}-\\d{5}", message = "Neatbilstošs personas kods") String personcode,
			User user, @NotNull @Pattern(regexp = "\\d{8,20}") String matriculaNo, boolean financialDebt) {
		super(name, surname, personcode, user);
		this.matriculaNo = matriculaNo;
		this.financialDebt = financialDebt;
	}

	public void addDebtCourse(Course course) {
		if (!debtCourses.contains(course)) {
			debtCourses.add(course);
		}
	}

}
