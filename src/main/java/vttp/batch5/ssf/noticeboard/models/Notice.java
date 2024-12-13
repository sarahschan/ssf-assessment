package vttp.batch5.ssf.noticeboard.models;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Notice {
 
        @NotBlank(message = "Notice title is mandatory")
        @Size(min = 3, max = 128, message = "Notice title must be between 3 and 128 characters")
    private String title;

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Please enter a valid email address")
    private String poster;

        @NotNull(message = "Post date is mandatory")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @Future(message = "Post date must be a date in the future")
    private LocalDate postDate;

        @NotNull(message = "At least 1 category must be submitted")
    private String categories;

        @NotBlank(message = "You must include a message")
    private String text;


    public Notice() {
    }

    public Notice(String title, String poster, LocalDate postDate, String categories, String text) {
        this.title = title;
        this.poster = poster;
        this.postDate = postDate;
        this.categories = categories;
        this.text = text;
    }


    @Override
    public String toString() {
        return title + "," + poster + "," + postDate + "," + categories + "," + text;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
