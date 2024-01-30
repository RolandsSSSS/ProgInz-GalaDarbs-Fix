package lv.venta.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Student;

@Table(name = "thesis_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
// @ToString
public class Thesis {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "Idt")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idt;

	@Column(name = "TitleLv")
	@NotNull
	@Size(min = 3, max = 25)
	private String titleLv;

	@Column(name = "TitleEn")
	@NotNull
	@Size(min = 3, max = 25)
	private String titleEn;

	@Column(name = "Aim")
	@NotNull
	@Size(min = 3, max = 50)
	private String aim;

	@Column(name = "Tasks")
	@NotNull
	@Size(min = 3, max = 20)
	private String tasks;

	@Column(name = "SubmitDateTime")
	private LocalDateTime submitDateTime;

	@Column(name = "statusFromSupervisor")
	private boolean statusFromSupervisor;

	@Column(name = "accStatus")
	private AcceptanceStatus accStatus;

	@Column(name = "AccDateTime")
	private LocalDateTime accDateTime;

	@ManyToOne
	@JoinColumn(name = "Ids")
	private Student student;

	@ManyToOne
	@JoinColumn(name = "Ida")
	private AcademicPersonel supervisor;

	@ManyToMany
	@JoinTable(name = "thesis_reviewers", joinColumns = @JoinColumn(name = "Idt"), inverseJoinColumns = @JoinColumn(name = "Ida"))
	private Collection<AcademicPersonel> reviewers = new ArrayList<>();

	public void addReviewer(AcademicPersonel reviewer) {
		if (!reviewers.contains(reviewer)) {
			reviewers.add(reviewer);
		}
	}

	@OneToMany(mappedBy = "thesis", cascade = CascadeType.ALL)
	private Collection<Comment> comments;

	public Thesis(String titleLv, String titleEn, String aim, String tasks, Student student,
			AcademicPersonel supervisor) {
		super();
		this.titleLv = titleLv;
		this.titleEn = titleEn;
		this.aim = aim;
		this.tasks = tasks;
		this.student = student;
		this.supervisor = supervisor;
		this.submitDateTime = LocalDateTime.now();
		this.accStatus = AcceptanceStatus.SUBMITTED;
	}

}
