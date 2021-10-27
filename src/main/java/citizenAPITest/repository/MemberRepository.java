package citizenAPITest.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import citizenAPITest.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	List<Member> findByCity(String city, Sort sort);
}
