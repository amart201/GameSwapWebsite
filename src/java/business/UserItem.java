/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import swaps.Game;
import utility.OfferDB;

/**
 *
 * @author amart201
 */
public class UserItem implements Serializable{
    
    private Game userItem;
    private int swapperID;
    private int rating;
    private String status;
    private Game swapItem;
    private int swapItemRating;
    private int swapperRating;
    private int offerID;

    public UserItem() {
        userItem = new Game();
        swapperID = 0;
        rating = 0;
        status = "";
        swapItem = new Game();
        swapItemRating = -1;
        swapperRating = 0;
        offerID = 0;
    }

    public int getOfferID() {
        return offerID;
    }

    public void setOfferID(int offerID) {
        this.offerID = offerID;
    }

    public UserItem(Game userItem,int swapperID, int rating, String status, Game swapItem, int swapItemRating, int swapperRating) {
        this.userItem = userItem;
        this.swapperID = swapperID;
        this.rating = rating;
        this.status = status;
        this.swapItem = swapItem;
        this.swapItemRating = swapItemRating;
        this.swapperRating = swapperRating;
    }

    public Game getUserItem() {
        return userItem;
    }

    public void setUserItem(Game userItem) {
        this.userItem = userItem;
    }

    public int getSwapperID() {
        return swapperID;
    }

    public void setSwapperID(int swapperID) {
        this.swapperID = swapperID;
    }
    
    

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        OfferDB offers = new OfferDB();
        offers.updateOffer(this.offerID, status);
    }

    public Game getSwapItem() {
        return swapItem;
    }

    public void setSwapItem(Game swapItem) {
        this.swapItem = swapItem;
    }

    public int getSwapItemRating() {
        return swapItemRating;
    }

    public void setSwapItemRating(int swapItemRating) {
        this.swapItemRating = swapItemRating;
    }

    public int getSwapperRating() {
        return swapperRating;
    }

    public void setSwapperRating(int swapperID) {
        this.swapperRating = swapperID;
    }
    
    
}
