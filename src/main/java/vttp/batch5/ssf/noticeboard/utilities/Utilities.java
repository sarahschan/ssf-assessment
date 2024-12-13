package vttp.batch5.ssf.noticeboard.utilities;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.stereotype.Component;

@Component
public class Utilities {
    
    public long dateToEpochMilli(LocalDate postDate){
        return postDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
