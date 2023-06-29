package lv.venta.models;

import java.time.LocalDateTime;
import java.util.Collection;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.models.users.AcademicPersonel;
import lv.venta.models.users.Student;

@Table(name = "comment_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "Idco")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idco;
	
	@Column(name =  "Description")
	private String description;
	
	@Column(name = "commentDate")
	private LocalDateTime commentDate;
	
	@ManyToOne
	@JoinColumn(name="Ida")
	private AcademicPersonel personel;
	
	@ManyToOne
	@JoinColumn(name = "Idt")
	private Thesis thesis;

	public Comment(String description, AcademicPersonel personel, Thesis thesis) {
		super();
		this.description = description;
		this.personel = personel;
		this.thesis = thesis;
		this.commentDate = LocalDateTime.now();
	}
	
	
	
}
