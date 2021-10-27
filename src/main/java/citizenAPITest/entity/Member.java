package citizenAPITest.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Table(name="tbl_members")
@Data
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private Long id;
	private String firstname;
	private String surname; //can be empty?
	private String address1;
	private String address2; //can be empty?
	private String city;
	private String state;
	private String postcode;
	private String countrycode;
	private String gender;
	private String dateofbirth_DMY;
	/*
	@JsonIgnore
	@DateTimeFormat(pattern="yyyy/MM/dd")
	private Date dateofbirth_YMD;
	*/
}