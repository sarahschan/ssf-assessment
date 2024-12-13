package vttp.batch5.ssf.noticeboard.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class NoticeRepository {

	@Autowired
	@Qualifier("notice")
	RedisTemplate<String, Object> template;


	
	public void insertNotices(String postID, String payload) {
		
		// redis-cli command: hset myhashmap <id> <Json Payload as String>
		template.opsForHash().put("success", postID, payload);
		
	}


}
