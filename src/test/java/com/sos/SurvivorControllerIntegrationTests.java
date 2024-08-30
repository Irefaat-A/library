package com.sos;

import com.sos.models.Item;
import com.sos.models.Location;
import com.sos.models.enums.Gender;
import com.sos.models.enums.ItemType;
import com.sos.models.requests.CreateSurvivorRequest;
import com.sos.models.responses.api.ApiSuccessfulResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurvivorControllerIntegrationTests {
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;
	@Test
	void testCreateSurvivor() throws Exception {

		CreateSurvivorRequest createSurvivorRequest = CreateSurvivorRequest
				.builder()
				.name("TTT")
				.age((byte) 1)
				.gender(Gender.MALE.getDescription())
				.identity("III")
				.location(Location.builder().latitude("asda").longitude("asda").build())
				.supplies(List.of(Item.builder().description("rrr").itemType(ItemType.MEDICATION.getDescription()).quantity(5).build()))
				.build();

		ApiSuccessfulResponse apiResponse = this.restTemplate.postForObject(
				"http://localhost:" + port + "/v1/apocalypse/survivor",
				createSurvivorRequest,
				ApiSuccessfulResponse.class
		);

		LinkedHashMap data = (LinkedHashMap) apiResponse.getData();
		assertThat(data.get("id"),equalTo(1));
	}
}
