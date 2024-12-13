// package vttp.batch5.ssf.noticeboard.controllers;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping()
// public class StatusController {
    
//     @Autowired
//     @Qualifier("notice")
//     RedisTemplate<String, Object> template;

//     @GetMapping(path="/status", produces = MediaType.APPLICATION_JSON_VALUE)
//     public ResponseEntity<String> getStatus(){
        
//         HttpHeaders headers = new HttpHeaders();
// 			    headers.set("Content-Type", "application/json");

//         try {
            
//             template.randomKey();
            
//         } catch (Exception e) {

//             return ResponseEntity.status(503).headers(headers).body("");
//         }

//         return ResponseEntity.status(200).headers(headers).body("");

//     }
// }
