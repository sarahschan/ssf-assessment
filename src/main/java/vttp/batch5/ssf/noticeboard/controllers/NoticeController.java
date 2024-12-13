package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;
import vttp.batch5.ssf.noticeboard.services.StatusService;

// Use this class to write your request handlers

@Controller
@RequestMapping()
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @Autowired
    StatusService statusService;

    @GetMapping()
    public String view1(Model model) {
        
        Notice notice = new Notice();
        model.addAttribute("notice", notice);

        return "notice";

    }


    @PostMapping("/notice")
    public String handleSubmit(@Valid @ModelAttribute("notice") Notice notice, BindingResult result, Model model) throws Exception{
        
        // check for errors
        if (result.hasErrors()){
            model.addAttribute("notice", notice);
            return "notice";
        }


        // if no errors, make a REST API call to the notice publishing endpoint to publish the notice
        try {
            
            String postID = noticeService.postToNoticeServer(notice);
            model.addAttribute("postID", postID);
            return "view2";

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "view3";
        }
       
    }


    @GetMapping(path = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getStatus() {

        return statusService.checkStatus();

    }

}
