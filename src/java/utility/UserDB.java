/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import business.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import org.owasp.esapi.ESAPI;
import swaps.Game;

/**
 *
 * @author amart201
 */
public class UserDB {
    
    //this method adds a user with the provided values as the user attributes. Note: That the userId is not provided as this should be auto incremented to ensure each user ID is unique.
    public void addUser(String firstName, String lastName, String email, String address1, String address2, String city, String state, String zipcode, String country){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAddress1(address1);
        user.setAddress2(address2);
        user.setCity(city);
        user.setState(state);
        user.setZipcode(zipcode);
        user.setCountry(country);
        this.addUser(user);
    }
    
    //this method adds a user with the attribute values from the provided User Bean
    public void addUser(User user){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO users (password, firstName, lastName, email, address1, address2, city, state, zipcode, country) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getAddress1());
            ps.setString(6, user.getAddress2());
            ps.setString(7, user.getCity());
            ps.setString(8, user.getState());
            ps.setString(9, user.getZipcode());
            ps.setString(10, user.getCountry());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
            
    //getAllUsers() - this method returns an ArrayList<User> of all the users in the User table
    public ArrayList<User> getAllUsers(){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> userList = new ArrayList<>();
        ResultSetMetaData metaData = null;

        String query = "SELECT * FROM users ";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            metaData = rs.getMetaData();
            User user = null;
            while (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("userID"));
                user.setFirstName(ESAPI.encoder().encodeForHTML(rs.getString("firstName")));
                user.setLastName(ESAPI.encoder().encodeForHTML(rs.getString("lastName")));
                user.setEmail(rs.getString("email"));
                user.setPassword(ESAPI.encoder().encodeForHTML(rs.getString("password")));
                user.setAddress1(ESAPI.encoder().encodeForHTML(rs.getString("address1")));
                user.setAddress2(ESAPI.encoder().encodeForHTML(rs.getString("address2")));
                user.setCity(ESAPI.encoder().encodeForHTML(rs.getString("city")));
                user.setState(ESAPI.encoder().encodeForHTML(rs.getString("state")));
                user.setZipcode(ESAPI.encoder().encodeForHTML(rs.getString("zipcode")));
                user.setCountry(ESAPI.encoder().encodeForHTML(rs.getString("country")));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return userList;
    }
            
    //getUser(String userID) - this method returns a User Bean for the provided user ID
    public User getUser(String userID){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM users "
                + "WHERE userID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, userID);
            rs = ps.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("userID"));
                user.setFirstName(ESAPI.encoder().encodeForHTML(rs.getString("firstName")));
                user.setLastName(ESAPI.encoder().encodeForHTML(rs.getString("lastName")));
                user.setEmail(rs.getString("email"));
                user.setPassword(ESAPI.encoder().encodeForHTML(rs.getString("password")));
                user.setAddress1(ESAPI.encoder().encodeForHTML(rs.getString("address1")));
                user.setAddress2(ESAPI.encoder().encodeForHTML(rs.getString("address2")));
                user.setCity(ESAPI.encoder().encodeForHTML(rs.getString("city")));
                user.setState(ESAPI.encoder().encodeForHTML(rs.getString("state")));
                user.setZipcode(ESAPI.encoder().encodeForHTML(rs.getString("zipcode")));
                user.setCountry(ESAPI.encoder().encodeForHTML(rs.getString("country")));
            }
            return user;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public UserProfile getUserProfile(int userId) {
        OfferDB offers = new OfferDB();
        UserProfile profile = new UserProfile();
        profile.setUserId(userId);
        profile.setUserItems(offers.getUserOffers(userId));
        return profile;
    }
    
    public void UpdateUserRating(int userID, int rating) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE users "
                + "SET rating = ? "
                + "WHERE userID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, rating);
            ps.setInt(2, userID);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}