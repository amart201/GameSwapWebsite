/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swaps;

import java.io.Serializable;

/**
 *
 * @author amart201
 */
public class Game implements Serializable{
    
    private String code;
    private String name;
    private String console;
    private String description;
    private int rating;
    private String imageURL;
    
    public Game() {
        code = "";
        name = "";
        console = "";
        description = "";
        rating = 0;
        imageURL = "";
    }

    public Game(String code, String name, String console, String description, int rating, String imageURL) {
        this.code = code;
        this.name = name;
        this.console = console;
        this.description = description;
        this.rating = rating;
        this.imageURL = imageURL;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    
    
}
