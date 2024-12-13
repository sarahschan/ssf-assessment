package vttp.batch5.ssf.noticeboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
    
    @Autowired
    @Qualifier("notice")
    RedisTemplate<String, Object> template;

    public ResponseEntity<String> checkStatus(){

        HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

        try {
            // redis-cli command: randomkey
            template.randomKey();
        } catch (Exception e) {

            // UNHEALTHY: 503
            return ResponseEntity.status(503).headers(headers).body("{}");
        }

        // HEALTHY: 200
        return ResponseEntity.status(200).headers(headers).body("{}");

    }
}
