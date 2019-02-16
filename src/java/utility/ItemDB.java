/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

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
public class ItemDB {
    
    //(creates an item with the provided values and calls addItem(Item item)
    public void addItem(String itemCode, String itemName, String console, String description, int rating, String imageURL){
        Game game = new Game();
        game.setCode(itemCode);
        game.setName(itemName);
        game.setConsole(console);
        game.setDescription(description);
        game.setRating(rating);
        game.setImageURL(imageURL);
        this.addItem(game);
    }
    
    //method adds an item with the attribute values from the provided Item Bean to the database
    public void addItem(Game game){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO games (code, name, console, description, rating, imageURL) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, game.getCode());
            ps.setString(2, game.getName());
            ps.setString(3, game.getConsole());
            ps.setString(4, game.getDescription());
            ps.setInt(5, game.getRating());
            ps.setString(6, game.getImageURL());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    //getAllItems() - this method returns an ArrayList<Item> of all the items in the items table from the database
    public ArrayList<Game> getAllItems(){
        ArrayList<Game> games = new ArrayList<Game>();
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData metaData = null;

        String query = "SELECT * FROM games ";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            metaData = rs.getMetaData();
            Game game = null;
            while (rs.next()) {
                game = new Game();
                game.setCode(ESAPI.encoder().encodeForHTML(rs.getString("code")));
                game.setName(rs.getString("name"));
                game.setConsole(ESAPI.encoder().encodeForHTML(rs.getString("console")));
                game.setDescription(ESAPI.encoder().encodeForHTML(rs.getString("description")));
                game.setRating(rs.getInt("rating"));
                game.setImageURL(ESAPI.encoder().encodeForHTML(rs.getString("imageURL")));
                games.add(game);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return games;
    }

    //getItem(int itemCode) - this method returns a Item Bean for the provided item code
    public Game getItem(String itemCode){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM games "
                + "WHERE code = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, itemCode);
            rs = ps.executeQuery();
            Game game = new Game();
            if (rs.next()) {
                game.setCode(ESAPI.encoder().encodeForHTML(rs.getString("code")));
                game.setName(rs.getString("name"));
                game.setConsole(ESAPI.encoder().encodeForHTML(rs.getString("console")));
                game.setDescription(ESAPI.encoder().encodeForHTML(rs.getString("description")));
                game.setRating(rs.getInt("rating"));
                game.setImageURL(rs.getString("imageURL"));
            }
            return game;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public void updateItem(String itemCode, int rating){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE games "
                + "SET rating = ? "
                + "WHERE code = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, rating);
            ps.setString(2, itemCode);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}
