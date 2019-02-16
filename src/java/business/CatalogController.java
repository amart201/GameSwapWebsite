/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import swaps.Game;
import utility.ItemDB;
import utility.OfferDB;
import utility.UserDB;
import business.UserItem;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

/**
 *
 * @author amart201
 */
public class CatalogController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ItemDB gameDB = new ItemDB();
            OfferDB offers = new OfferDB();
            ArrayList<Game> gameList = new ArrayList<Game>();
            //code for loading catalog for a logged in user
            if (request.getSession().getAttribute("theUser") instanceof User) {
                User user = (User) request.getSession().getAttribute("theUser");
                UserDB userdb = new UserDB();
                ArrayList<UserItem> userItemList = offers.getUserOffers(user.getUserId());
                gameList = gameDB.getAllItems();
                ArrayList<Game> notUserList = new ArrayList<Game>();
                for (int i = 0; i < gameList.size(); i++){
                    boolean inThere = false;
                    String game1 = gameList.get(i).getCode();
                    for (int j = 0; j < userItemList.size(); j++){
                        String game2 = userItemList.get(j).getUserItem().getCode();
                        if (game2.equals(game1) || userItemList.get(j).getStatus().equals("s")){
                            inThere = true;
                        }
                    }
                    if (!inThere) {
                        notUserList.add(gameList.get(i));
                    }
                }
                gameList = notUserList;
            }
            //code for displaying catalog for general user
            else {
                gameList = gameDB.getAllItems();
            }
            String task = null;
            if (request.getParameter("itemCode") != null) {
                task = ESAPI.validator().getValidInput("itemCode", request.getParameter("itemCode"),"SafeString", 200, false);
            }
            if (task != null && task.length() == 7 && Character.isDigit(task.charAt(6)) && gameDB.getItem(task)!=null) {
                Game gameItem = new Game();
                gameItem = gameDB.getItem(task);
                request.setAttribute("gameItem", gameItem);
                this.getServletContext().getRequestDispatcher("/item.jsp").forward(request,response);
            }
            else {
                request.setAttribute("gameList", gameList);
                this.getServletContext().getRequestDispatcher("/categories.jsp").forward(request,response);
            }
        } catch (ValidationException ex) {
            Logger.getLogger(CatalogController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IntrusionException ex) {
            Logger.getLogger(CatalogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
