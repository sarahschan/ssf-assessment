package vttp.batch5.ssf.noticeboard.services;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;
import vttp.batch5.ssf.noticeboard.utilities.Utilities;

@Service
public class NoticeService {

	RestTemplate restTemplate = new RestTemplate();

	@Autowired
	Utilities utilities;

	@Autowired
	NoticeRepository noticeRepository;

	@Value("${publishing.host}")
  	private String publishingHost;

	// Task 3 - Make a REST API call to the notice publishing endpoint to publish the notice
	public String postToNoticeServer(Notice notice) throws Exception {

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

		// Set the headers
		HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			headers.set("Accept", "application/json");

		// Create the request entity
		RequestEntity<String> request = RequestEntity.post(publishingHost)
													 .headers(headers)
													 .body(requestBody.toString());


		// Use the rest template to send the POST request to the API													 
		try {
			
			ResponseEntity<String> response = restTemplate.exchange(request, String.class);

			System.out.println(response.getStatusCode());

			if (response.getStatusCode() != HttpStatusCode.valueOf(200)) {				
				throw new Exception();
			}

			// save the response body to redis
			try {
				
				String payload = response.getBody();
				JsonObject payloadJson = Json.createReader(new StringReader(payload)).readObject();
				String postID = payloadJson.getString("id");

				noticeRepository.insertNotices(postID, payload);
				
				return postID;

			} catch (Exception e) {
				throw new Exception("Save to redis failed: " + e.getMessage(), e);
			}

		} catch (Exception e) {

			// 406 Not Acceptable: "{"message":"Payload is not JSON","timestamp":1734071051346}"

			String errorRaw = e.getMessage();
			String errorMessage = errorRaw;

			// Attempt to parse message (also so that if it's not an error message like above it will just go through)
			try {
				
				int startIndex = errorRaw.indexOf("{");
				
				if (startIndex != -1){
					String jsonString = errorRaw.substring(startIndex);
					JsonObject errorJson = Json.createReader(new StringReader(jsonString)).readObject();
					errorMessage = errorJson.getString("message");
				}
			} catch (Exception parseException) {
				System.out.println("Cannot parse this error message");
			}
			
			throw new Exception("I/O error on POST request for \"http://localhost:3000/notice\": " + errorMessage, e);

		}
		
	}
}
