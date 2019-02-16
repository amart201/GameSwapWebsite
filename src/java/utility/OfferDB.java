/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import business.User;
import business.UserItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import org.owasp.esapi.ESAPI;

/**
 *
 * @author amart
 */
public class OfferDB {
    /*this method adds an offer to the database. 
    The userID here refers to the user that is making the offer and itemCodeOwn 
    is the itemCode that this user owns and itemCodeWant is the item code they would like to get.
    */
    public void addOffer(int userID, String itemCodeOwn, String itemCodeWant, String itemStatus){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO offers (userID, itemCodeOwn, itemCodeWant) "
                + "VALUES (?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userID);
            ps.setString(2, itemCodeOwn);
            ps.setString(3, itemCodeWant);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    //this method updates the status of the offer in the database.
    public void updateOffer(int offerID, String itemStatus){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE offers "
                + "SET status = ? "
                + "WHERE offerID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, itemStatus);
            ps.setInt(2, offerID);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public void updateOffer(int offerID, String swapItem, String itemStatus){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE offers "
                + "SET status = ? "
                + ", itemCodeWant = ? "
                + "WHERE offerID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, itemStatus);
            ps.setString(2, swapItem);
            ps.setInt(3, offerID);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public void updateOffer(int swapperID, int swapperRating){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE offers "
                + "SET swapperRating = ? "
                + "WHERE swapperID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, swapperRating);
            ps.setInt(2, swapperID);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public ArrayList<UserItem> getUserOffers(int userID) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserItem> itemList = new ArrayList<>();
        ItemDB itemDB = new ItemDB();
        ResultSetMetaData metaData = null;

        String query = "SELECT * FROM offers WHERE userID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            metaData = rs.getMetaData();
            UserItem userItem = null;
            while (rs.next()) {
                userItem = new UserItem();
                userItem.setOfferID(rs.getInt("offerID"));
                userItem.setSwapperID(rs.getInt("swapperID"));
                userItem.setUserItem(itemDB.getItem(ESAPI.encoder().encodeForHTML(rs.getString("itemCodeOwn"))));
                userItem.setSwapItem(itemDB.getItem(ESAPI.encoder().encodeForHTML(rs.getString("itemCodeWant"))));
                userItem.setSwapperRating(rs.getInt("swapperRating"));
                userItem.setStatus(ESAPI.encoder().encodeForHTML(rs.getString("status")));
                userItem.setRating(userItem.getUserItem().getRating());
                userItem.setSwapItemRating(userItem.getSwapItem().getRating());
                itemList.add(userItem);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return itemList;
    }
}
