package citizenAPITest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
/***
 * 
 * This controller provides readMember() and groupMember() functions.
 * 1. readMember() uses the parameters in the URL to implement sort and/or filter functions.
 * 2. groupMember() uses SQL native query language to count the same address.
 * @author Marcus Hsu
 * 
 ***/


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
	
	@GetMapping("/members/group")
	public ResponseEntity<List<Map<String, String>>> groupMember() {
		
		try {
			List<Map<String, String>> response = new ArrayList<>();
			List<Object[]> result = repo.countTotalMemberByCity();
			Map<String,String> map = null;
			if(result != null && !result.isEmpty()){
			for (Object[] object : result) {
				map = new HashMap<String,String>();
				map.put("address", object[0].toString());
				map.put("count", object[1].toString());
				response.add(map);
				}
			}
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		catch (Exception e) {
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
