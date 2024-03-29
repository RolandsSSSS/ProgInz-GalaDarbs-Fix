package lv.venta.models.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "person_table")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Person {
	@Setter(value = AccessLevel.NONE)
	@Column(name = "Idp")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idp;

	@Column(name = "Name")
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ][a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam")
	@Size(min = 3, max = 15)
	private String name;

	@NotNull
	@Size(min = 3, max = 15)
	@Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ][a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam")
	@Column(name = "Surname")
	private String surname;

	@NotNull
	@Size(min = 11, max = 13)
	@Pattern(regexp = "\\d{6}-\\d{5}", message = "Neatbilstošs personas kods")
	@Column(name = "Personcode")
	private String personcode;

	@OneToOne
	@JoinColumn(name = "Idu")
	private User user;

	public Person(
			@NotNull @Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ][a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam") @Size(min = 3, max = 15) String name,
			@NotNull @Size(min = 3, max = 15) @Pattern(regexp = "[A-ZĒŪĪĻĶŠĀŽČŅ][a-zēūīļķšāžčņ\\ ]+", message = "Pirmajam burtam jābūt lielajam") String surname,
			@NotNull @Size(min = 11, max = 13) @Pattern(regexp = "\\d{6}-\\d{5}", message = "Neatbilstošs personas kods") String personcode,
			User user) {
		this.name = name;
		this.surname = surname;
		this.personcode = personcode;
		this.user = user;
	}

}
