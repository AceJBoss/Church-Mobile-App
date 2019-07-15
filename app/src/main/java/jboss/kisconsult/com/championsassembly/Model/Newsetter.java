package jboss.kisconsult.com.championsassembly.Model;

import java.io.Serializable;

/**
 * Created by JBoss on 7/20/2016.
 */
public class Newsetter implements Serializable {
    private String namr, details, date, image;


    public Newsetter() {

    }
    public String getNamr() {
        return namr;
    }

    public void setNamr(String namr) {
        this.namr = namr;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
