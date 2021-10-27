package citizenAPITest.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import citizenAPITest.entity.Member;
import citizenAPITest.repository.MemberRepository;

@RestController
@RequestMapping("/api/v1")
public class MembersController {
	
	@Autowired
	MemberRepository repo;
	
	@GetMapping("/members")
	public ResponseEntity<List<Member>> readMembers(
			@RequestParam(required = false) String[] sorts,
			@RequestParam(required = false) String filter) {	
		try {
			List<Order> orders = new ArrayList<Order>();
			List<Member> response = new ArrayList<Member>();
			
			if(sorts == null && filter == null) {
				response = repo.findAll();
			} else {
				if(sorts != null) {
					for(String sort : sorts) {
						String[] temp = sort.split(":");
						orders.add(new Order(getSortDirection(temp[1]),temp[0]));
					}
				}
				if(filter != null) {
					response = repo.findByCity(filter, Sort.by(orders));
				} else {
					response = repo.findAll(Sort.by(orders));
				}
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private Sort.Direction getSortDirection(String direction) {
	    if (direction.equals("asc")) {
	      return Sort.Direction.ASC;
	    } else if (direction.equals("desc")) {
	      return Sort.Direction.DESC;
	    }
	    return Sort.Direction.ASC;
	  }
}
