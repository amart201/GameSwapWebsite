/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import business.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.owasp.esapi.ESAPI;

/**
 *
 * @author amart
 */
public class FeedbackDB {
    /*this method adds a feedback that user with userID1 is giving for user with userID2. 
    Both users have completed a swap. Using the offerID we can verify and link this to an offer. 
    The corresponding database table would use the offerID and userID1 as primary keys.*/
    public void addOfferFeedback(int offerID, int userID1, int userID2, int rating){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO offerFeedback (offerID, userID1, userID2, rating) "
                + "VALUES (?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, offerID);
            ps.setInt(2, userID1);
            ps.setInt(3, userID2);
            ps.setInt(4, rating);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public int getOfferFeedback(int userID2){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT rating FROM offerFeedback "
                + "WHERE userID2 = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userID2);
            rs = ps.executeQuery();
            int rating = 0;
            int count = 0;
            while (rs.next()) {
                ++count;
                rating = (rating + rs.getInt("rating"))/count;
                
            }
            return rating;
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
        
    /*this method adds a feedback that user with userID is giving for item with itemCode. 
    The corresponding database table would use the userID and itemCode as primary keys.*/
    public void addItemFeedback(String itemCode, int userID, int rating){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO itemFeedback (itemCode, userID, rating) "
                + "VALUES (?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, itemCode);
            ps.setInt(2, userID);
            ps.setInt(3, rating);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public int getItemFeedback(String itemCode){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT rating FROM itemFeedback "
                + "WHERE itemCode = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, itemCode);
            rs = ps.executeQuery();
            int rating = 0;
            int count = 0;
            while (rs.next()) {
                ++count;
                rating = (rating + rs.getInt("rating"))/count;
                
            }
            return rating;
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}
