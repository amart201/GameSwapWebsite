/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import utility.ConnectionPool;
import utility.DBUtil;

/**
 *
 * @author amart201
 */
public class UserProfile implements Serializable{
    
    private int userId;
    private int userRating;
    private ArrayList<UserItem> userItems;

    public UserProfile() {
        userId = 0;
        userRating = 0;
        userItems = new ArrayList<>();
    }

    public UserProfile(int userId, int userRating, ArrayList<UserItem> userItems) {
        this.userId = userId;
        this.userRating = userRating;
        this.userItems = userItems;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public ArrayList<UserItem> getUserItems() {
        return userItems;
    }

    public void setUserItems(ArrayList<UserItem> userItems) {
        this.userItems = userItems;
    }
    
    public void removeUserItem(String item) {
        for (int i = 0; i < userItems.size(); i++) {
            if (userItems.get(i).getUserItem().getCode().equals(item)) {
                userItems.remove(i);
            }
        }
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM offers "
                + "WHERE itemCodeOwn = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, item);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public void emptyProfile() {
        userItems.clear();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM offers "
                + "WHERE userID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, this.userId);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}
