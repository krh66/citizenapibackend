package citizenAPITest;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import citizenAPITest.controller.MembersController;
import citizenAPITest.entity.Member;
import citizenAPITest.repository.MemberRepository;

@WebMvcTest(MembersController.class)
class CitizenApiTestApplicationTests {
	
	@Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    MemberRepository repo;

    Member member1 = new Member(1l, "Marcus", "Hsu", "2 Burley Rd", "", "Leeds", "West Yorkshire", "LS3 1JB", "GB", "M", "01/01/1980");
    Member member2 = new Member(2l, "Sam", "Jojo", "2 Clarendence Rd", "", "Glasgow", "West Yorkshire", "LS2 1CG", "GB", "M", "02/02/1981");
    Member member3 = new Member(3l, "Alex", "Wang", "2 Smith Rd", "", "London", "West Yorkshire", "LS1 9JT", "GB", "M", "03/03/1982");

	@Test
	public void getAllMembers() throws Exception{
		List<Member> members = new ArrayList<Member>(Arrays.asList(member1, member2, member3));
		Mockito.when(repo.findAll()).thenReturn(members);
		mockMvc.perform(MockMvcRequestBuilders
		            .get("/api/v1/members")
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
					.andExpect(jsonPath("$", hasSize(3)))
		            .andExpect(jsonPath("$[0].firstname", is("Marcus")));
	}
	
	@Test
	public void ascSortingTest() throws Exception{
		List<Member> members = new ArrayList<Member>(Arrays.asList(member3, member1, member2));
		Mockito.when(repo.findAll(Sort.by(Order.asc("firstname")))).thenReturn(members);
		mockMvc.perform(MockMvcRequestBuilders
		            .get("/api/v1/members")
		            .param("sorts", "firstname:asc")
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
					.andExpect(jsonPath("$", hasSize(3)))
		            .andExpect(jsonPath("$[0].firstname", is("Alex")))
		            .andExpect(jsonPath("$[1].firstname", is("Marcus")))
		            .andExpect(jsonPath("$[2].firstname", is("Sam")));
	}
	
	@Test
	public void descSortingTest() throws Exception{
		List<Member> members = new ArrayList<Member>(Arrays.asList(member2, member1, member3));
		Mockito.when(repo.findAll(Sort.by(Order.desc("firstname")))).thenReturn(members);
		mockMvc.perform(MockMvcRequestBuilders
		            .get("/api/v1/members")
		            .param("sorts", "firstname:desc")
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
					.andExpect(jsonPath("$", hasSize(3)))
		            .andExpect(jsonPath("$[0].firstname", is("Sam")))
		            .andExpect(jsonPath("$[1].firstname", is("Marcus")))
		            .andExpect(jsonPath("$[2].firstname", is("Alex")));
	}
	
	@Test
	public void filterAndSortingTest() throws Exception{
		String cityname = "Leeds";
		List<Member> members = new ArrayList<Member>(Arrays.asList(member1));
		Mockito.when(repo.findByCity(cityname, Sort.by(Order.asc("id")))).thenReturn(members);
		
		mockMvc.perform(MockMvcRequestBuilders
		            .get("/api/v1/members")
		            .param("filter", cityname)
		            .param("sorts", "id:asc")
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
					.andExpect(jsonPath("$", hasSize(1)))
		            .andExpect(jsonPath("$[0].firstname", is("Marcus")));
	}
}
