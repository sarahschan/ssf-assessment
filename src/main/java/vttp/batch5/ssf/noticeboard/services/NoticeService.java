package vttp.batch5.ssf.noticeboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.batch5.ssf.noticeboard.constants.Constant;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.utilities.Utilities;

@Service
public class NoticeService {

	RestTemplate restTemplate = new RestTemplate();

	@Autowired
	Utilities utilities;
	// Task 3 - Make a REST API call to the notice publishing endpoint to publish the notice
	public void postToNoticeServer(Notice notice) throws Exception {

		System.out.println("Now in postToNoticeServer");

		// Build the categories array
		JsonArrayBuilder categoriesArrayBuilder = Json.createArrayBuilder();
		String[] categoriesStringArray = notice.getCategories().split(",");
		for (String category : categoriesStringArray){
			categoriesArrayBuilder.add(category);
		}
		JsonArray categoriesJsonArray = categoriesArrayBuilder.build();
		System.out.println(categoriesJsonArray.toString());		

		// Build the request body
		JsonObject requestBody = Json.createObjectBuilder()
										.add("title", notice.getTitle())
										.add("poster", notice.getPoster())
										.add("postDate", utilities.dateToEpochMilli(notice.getPostDate()))
										.add("categories", categoriesJsonArray)
										.add("text", notice.getText())
										.build();

		System.out.println(requestBody);

		// Set the headers
		HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			headers.set("Accept", "application/json");

		// Create a request entity to send to the API
		RequestEntity<String> request = RequestEntity.post(Constant.PUBLISHING_URL + "notice")
													 .headers(headers)
													 .body(requestBody.toString());


		// Use the rest template to send the POST request to the API													 
		System.out.println("Attempting call to: " + Constant.PUBLISHING_URL);
		try {
			ResponseEntity<String> response = restTemplate.exchange(request, String.class);

			System.out.println(response.getStatusCode());
			System.out.println(response.getHeaders());
			System.out.println(response.getBody());

		} catch (Exception e) {
			throw new Exception("Call failed: " + e.getMessage());
		}
	}
}
