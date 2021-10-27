package citizenAPITest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import citizenAPITest.entity.Member;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CitizenApiTestApplication.class)
class CitizenApiTestApplicationTests {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	private final String url = "http://localhost:8080/api/v1";
	
	@Test
	void contextLoads() {
	}

	@Test
	void testGetAllMembers() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<List<Member>> entity = new HttpEntity<List<Member>>(null, headers);

//		ResponseEntity<List<Member>> response = testRestTemplate.exchange(getRootUrl() + "/members";
//		HttpMethod.GET, entity, Object.class);
		ResponseEntity<String> response = 
				testRestTemplate.getForEntity(url + "/members", String.class);
		assertNotNull(response.getBody());
	}
}
