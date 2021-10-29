package citizenAPITest.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import citizenAPITest.entity.Member;

/***
 * 
 * This repository provides sorting, filter(by city), and group(by address) functions
 * @author Marcus Hsu
 * 
 ***/

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	List<Member> findByCity(String city, Sort sort);
	
	@Query(value = "select concat(trim(address1),' ', trim(address2)) as address, count(t1.id)\n"
			+ "from tbl_members as t1\n"
			+ "group by address;\n", nativeQuery = true)
	List<Object[]> countTotalMemberByCity();
}
