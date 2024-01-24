package lv.venta.models.users;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.venta.models.Comment;
import lv.venta.models.Thesis;

@Table(name = "academic_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AttributeOverride(name = "Idp", column = @Column(name = "Ida"))
public class AcademicPersonel extends Person {
	
	@Column(name = "Degree")
	private Degree degree;
	
	@OneToMany(mappedBy = "supervisor", cascade = CascadeType.REMOVE)
	private Collection<Thesis> thesis;
	
	@ManyToMany(mappedBy = "reviewers", cascade = CascadeType.REMOVE)
	private Collection<Thesis> thesisForReviews = new ArrayList<>();
	
	public void addThesisForReviews(Thesis inputThesis) {
		if(! thesisForReviews.contains(inputThesis)) {
			thesisForReviews.add(inputThesis);
		}
	}

	public AcademicPersonel(
			@NotNull @Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ][a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam") @Size(min = 3, max = 15) String name,
			@NotNull @Size(min = 3, max = 15) @Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ][a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam") String surname,
			@NotNull @Size(min = 12, max = 12) @Pattern(regexp = "\\d{6}-\\d{5}", message = "Neatbilstošs personas kods") String personcode,
			User user, Degree degree) {
		super(name, surname, personcode, user);
		this.degree = degree;
	}
	@OneToMany(mappedBy = "personel", cascade = CascadeType.REMOVE)
	private Collection<Comment> comments;

	public void remove() {
	    if (this.thesis != null) {
	        for (Thesis academicThesis : this.thesis) {
	        	academicThesis.setSupervisor(null);
	        }
	    }
	}

}
