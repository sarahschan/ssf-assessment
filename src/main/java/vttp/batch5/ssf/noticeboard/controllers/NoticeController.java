package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

// Use this class to write your request handlers

@Controller
@RequestMapping()
public class NoticeController {

    @Autowired
    NoticeService noticeService;


    // Task 1 - Landing page
    @GetMapping()
    public String view1(Model model) {
        
        Notice notice = new Notice();
        model.addAttribute("notice", notice);

        return "notice";

    }


    // Task 2 - Write a request handler in NoticeController to process the submission
    @PostMapping("/notice")
    public String handleSubmit(@Valid @ModelAttribute("notice") Notice notice, BindingResult result, Model model){
        
        System.out.println("Recieved from form: " + notice);

        // check for errors
        if (result.hasErrors()){
            model.addAttribute("notice", notice);
            return "notice";
        }


        // if no errors, make a REST API call to the notice publishing endpoint to publish the notice


        return "view2";
        
    }


}
