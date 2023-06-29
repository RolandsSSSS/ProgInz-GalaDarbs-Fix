package lv.venta.models;

import java.time.LocalDateTime;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Student;

/*
 * 
 * pārlikt uz citu tabulu, kurai ir saite uz pasniedzeju, kas to temu piedāva
 * Joma
Grūtības_pakāpe
Tēmas_apraksts
Tēmas_pieejamība

 * 
 * 
 * 
 */

@Table(name = "thesis_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
//@ToString
public class Thesis {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "Idt")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idt;
	
	
	//TODO pievienopt nepieciešamās validācijas
	@Column(name = "TitleLv")
	private String titleLv;
	
	//TODO pievienopt nepieciešamās validācijas
	@Column(name = "TitleEn")
	private String titleEn;
	
	//TODO pievienopt nepieciešamās validācijas
	@Column(name = "Aim")
	private String aim;
		
	//TODO pievienopt nepieciešamās validācijas
	@Column(name = "Tasks")
	private String tasks;
	
	
	//TODO servisā vai kontrsuktorā pie jauna objekta izveides jāizmanto LocalDateTime.now()
	@Column(name ="SubmitDateTime")
	private LocalDateTime submitDateTime;
	
	@Column(name = "statusFromSupervisor")
	private boolean statusFromSupervisor;
	
	//TODO servisā vai konstruktora uzlikt submit pēc noklusējuma
	@Column(name = "accStatus")
	private AcceptanceStatus accStatus;
	
	@Column(name ="AccDateTime")
	private LocalDateTime accDateTime;
	
	@ManyToOne
	@JoinColumn(name = "Ids")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "Ida")
	private AcademicPersonel supervisor;
	//TODO ja nepieciesams, izveidot saiti ar konsultantu/vērtētaju utt
	@ManyToMany
	@JoinTable(name = "thesis_reviewers",
	joinColumns = @JoinColumn(name = "Idt"),
	inverseJoinColumns = @JoinColumn(name = "Ida"))
	private Collection<AcademicPersonel> reviewers = new ArrayList<>();
	
	
	//TODO līdzīgu funkciju uztaisīt Academicpersonel klasē
	public void addReviewer(AcademicPersonel reviewer) {
		if(!reviewers.contains(reviewer)) {
			reviewers.add(reviewer);
		}
	}

	@OneToMany(mappedBy = "thesis")
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
		this.accStatus = AcceptanceStatus.submited;
	}
	
	
	
	
	
}
