/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;
import swaps.Game;
import utility.FeedbackDB;
import utility.ItemDB;
import utility.OfferDB;
import static utility.PasswordUtil.hashAndSaltPassword;
import utility.UserDB;

/**
 *
 * @author amart201
 */
public class ProfileController extends HttpServlet {

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
        
        ItemDB items = new ItemDB();
        UserDB newUser = new UserDB();
        OfferDB offers = new OfferDB();
        ArrayList<User> users = newUser.getAllUsers();
        String url = "/myItems.jsp";
        String action = null;
        if (request.getParameter("action") != null) {
            try {
            action = ESAPI.validator().getValidInput("action", request.getParameter("action"),"SafeString", 200, false);
        } catch (ValidationException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IntrusionException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        UserProfile currentProfile = null;
        
        
        /**code for register action.
         * if the action from the request is register and all the information is valid
         * it should hash and salt the users password and then add the new user to the database.
         */
        if (validateAction(action) && action.equals("register")) {
            String firstname = null;
            String lastname = null;
            String email = null;
            String password = null;
            String address1 = null;
            String address2 = null;
            String city = null;
            String state = null;
            String zipcode = "";
            String country = null;
                    //checking for xss attacks
                    if (request.getParameter("firstname") != null && request.getParameter("lastname") != null && request.getParameter("email") != null
                            && request.getParameter("password") != null && request.getParameter("address1") != null
                            && request.getParameter("address2") != null && request.getParameter("city") != null
                            && request.getParameter("state") != null && request.getParameter("country") != null
                            && request.getParameter("zipcode") != null) {
                        try {
                            firstname = ESAPI.validator().getValidInput("firstname", request.getParameter("firstname"),"SafeString", 200, false);
                            lastname = ESAPI.validator().getValidInput("lastname", request.getParameter("lastname"),"SafeString", 200, false);
                            email = ESAPI.validator().getValidInput("email", request.getParameter("email"),"Email", 200, false);
                            password = ESAPI.validator().getValidInput("password", request.getParameter("password"),"SafeString", 200, false);
                            address1 = ESAPI.validator().getValidInput("address1", request.getParameter("address1"),"SafeString", 200, false);
                            if (request.getParameter("address2").isEmpty()) {
                                address2 = "";
                            }
                            else {
                                address2 = ESAPI.validator().getValidInput("address2", request.getParameter("address2"),"SafeString", 200, false);

                            }
                            city = ESAPI.validator().getValidInput("city", request.getParameter("city"),"SafeString", 200, false);
                            state = ESAPI.validator().getValidInput("state", request.getParameter("state"),"SafeString", 200, false);
                            zipcode = ESAPI.validator().getValidInput("zipcode", request.getParameter("zipcode"),"SafeString", 200, false);
                            country = ESAPI.validator().getValidInput("country", request.getParameter("country"),"SafeString", 200, false);
                        } catch (ValidationException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IntrusionException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
            try {
                password = hashAndSaltPassword(password);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
            User user = new User(0, password, firstname, lastname, email, address1, address2, city, state, zipcode, country);
            newUser.addUser(user);
            url = "/login.jsp";
        }
        
        /**code for signin action
         * if action from the request is signin and all information is valid
         * it should retrieve the user and information from the database so they
         * can use the site.
         */
        if (validateAction(action) && action.equals("signin")) {
            if (request.getSession().getAttribute("theUser") != null) {
                    url = "/myItems.jsp";
                }
                else {
                    String username = null;
                    if (request.getParameter("username") != null) {
                        try {
                            username = ESAPI.validator().getValidInput("username", request.getParameter("username"),"Email", 200, false);
                        } catch (ValidationException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IntrusionException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (username != null && validateUsername(username)) {
                        String password = null;
                        if (request.getParameter("password") != null) {
                            try {
                                password = ESAPI.validator().getValidInput("password", request.getParameter("password"),"SafeString", 200, false);
                            } catch (ValidationException ex) {
                                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IntrusionException ex) {
                                Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (password != null && validatePassword(password)) {
                            if (validateUsernamePassword(username, password) != null) {
                                User user = validateUsernamePassword(username, password);
                                currentProfile = newUser.getUserProfile(user.getUserId());
                                request.getSession().setAttribute("theUser", user);
                                request.getSession().setAttribute("currentProfile", currentProfile);
                                url = "/myItems.jsp";
                            }
                            else {
                                url = "/login.jsp";
                            }
                        }
                        else {
                            url = "/login.jsp";
                        }
                    }
                    else {
                        url = "/login.jsp";
                    }
                }
                getServletContext().getRequestDispatcher(url).forward(request, response);
        }
            
        /**
         * code for managing items in your profile and your potential swaps
         */
        if (request.getSession().getAttribute("theUser") instanceof User) {
            if (validateAction(action)) {
                //code for updating a user's item or swap
                if (action.equals("update")) {
                    String theItem = null;
                    if (request.getParameter("theItem") != null) {
                        try {
                            theItem = ESAPI.validator().getValidInput("theItem", request.getParameter("theItem"),"SafeString", 200, false);
                        } catch (ValidationException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IntrusionException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                        if (validateItem(theItem) && validateIntentionalAction(request, theItem)) {
                            UserProfile profile = (UserProfile) request.getSession().getAttribute("currentProfile");
                            UserItem item = new UserItem();
                            for (int i = 0; i < profile.getUserItems().size(); i++) {
                                if (profile.getUserItems().get(i).getUserItem().getCode().equals(theItem)) {
                                    item = profile.getUserItems().get(i);
                                }
                            }
                            if ("p".equals(item.getStatus()) || "o".equals(item.getStatus())) {
                                request.setAttribute("swapItem", item);
                                url = "/mySwaps.jsp";
                            }
                            if ("a".equals(item.getStatus()) || "s".equals(item.getStatus())) {
                                request.setAttribute("gameItem", item.getUserItem());
                                url = "/item.jsp";
                            }
                        }
                }
                //code for accepting, rejecting, or withdrawing from a swap
                if (action.equals("accept") || action.equals("withdraw") || action.equals("reject")) {
                    String item = null;
                    if (request.getParameter("theItem") != null) {
                        try {
                            item = ESAPI.validator().getValidInput("theItem", request.getParameter("theItem"),"SafeString", 200, false);
                        } catch (ValidationException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IntrusionException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    int itemIndex = 0;
                    UserItem itemBean = new UserItem();
                    UserProfile profile = (UserProfile) request.getSession().getAttribute("currentProfile");
                    if (validateItem(item) && validateIntentionalAction(request, item) && (checkProfileItem(profile, item, "p") || checkProfileItem(profile, item, "o"))) {
                        for (int x = 0; x < profile.getUserItems().size(); x++) {
                            if (profile.getUserItems().get(x).getUserItem().getCode().equals(item)) {
                                itemBean = profile.getUserItems().get(x);
                                itemIndex = x;
                            }
                        }
                        if (action.equals("reject") || action.equals("withdraw")) {
                            itemBean.setStatus("a");
                        }
                        if (action.equals("accept")) {
                            itemBean.setStatus("s");
                        }
                    }
                    profile.getUserItems().set(itemIndex, itemBean);
                    request.getSession().setAttribute("currentProfile", profile);
                    url = "/myItems.jsp";
                }
                //code for deleteing an item from the users profile
                if (action.equals("delete")) {
                    String item = null;
                    if (request.getParameter("theItem") != null) {
                        try {
                            item = ESAPI.validator().getValidInput("theItem", request.getParameter("theItem"),"SafeString", 200, false);
                        } catch (ValidationException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IntrusionException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    UserProfile profile = (UserProfile) request.getSession().getAttribute("currentProfile");
                    if (validateItem(item) && validateIntentionalAction(request, item) && checkProfileItem(profile, item)) {
                        profile.removeUserItem(item);
                    }
                    request.getSession().setAttribute("currentProfile", profile);
                    url = "/myItems.jsp";
                }
                //code for allowing a user to make an offer
                if (action.equals("offer")) {
                    String item = null;
                    if (request.getParameter("theItem") != null) {
                        try {
                            item = ESAPI.validator().getValidInput("theItem", request.getParameter("theItem"),"SafeString", 200, false);
                        } catch (ValidationException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IntrusionException ex) {
                            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    String message = "";
                    ArrayList<UserItem> availableItems = new ArrayList<>();
                    UserProfile profile = (UserProfile) request.getSession().getAttribute("currentProfile");
                    if (validateItem(item) && validateIntentionalAction(request, item)) {
                        for (int x = 0; x < profile.getUserItems().size(); x++) {
                            if ("a".equals(profile.getUserItems().get(x).getStatus())) {
                                availableItems.add(profile.getUserItems().get(x));
                            }
                        }
                        if (availableItems.isEmpty()) {
                            message = "Sorry, you do not have any available items for swapping. Please add more items to start again!";
                            request.setAttribute("message", message);
                            url = "/CatalogController?itemCode=" + item + "";
                        }
                        else {
                            Game swapItem = items.getItem(item);
                            request.setAttribute("gameItem", swapItem);
                            request.setAttribute("availableItems", availableItems);
                            url = "/swap.jsp";
                        }
                    }
                }
                //code for the user to create a swap offer
                if (action.equals("confirmSwap")) {
                    String userItem = request.getParameter("userItem");
                    String swapItem = request.getParameter("swapItem");
                    UserProfile profile = (UserProfile) request.getSession().getAttribute("currentProfile");
                    if (validateItem(userItem) && validateItem(swapItem)) {
                        int offerIndex = 0;
                        for (int x = 0; x < profile.getUserItems().size(); x++) {
                            if (userItem.equals(profile.getUserItems().get(x).getUserItem().getCode())) {
                                offerIndex = profile.getUserItems().get(x).getOfferID();
                            }
                        }
                        offers.updateOffer(offerIndex, swapItem, "o");
                        profile.setUserItems(offers.getUserOffers(offerIndex));
                        request.getSession().setAttribute("currentProfile", profile);
                    }
                }
                
                //code for a user to rate an item
                if (action.equals("rateitem")) {
                    User user = (User) request.getSession().getAttribute("theUser");
                    int id = user.getUserId();
                    String item = request.getParameter("gameItem");
                    if (request.getParameter("rating") == null) {
                        
                    }
                    else {
                        int rating = Integer.parseInt(request.getParameter("rating"));
                        FeedbackDB feedback = new FeedbackDB();
                        feedback.addItemFeedback(item, id, rating);
                        rating = feedback.getItemFeedback(item);
                        items.updateItem(item, rating);
                        UserProfile profile = (UserProfile) request.getSession().getAttribute("currentProfile");
                        profile.setUserItems(offers.getUserOffers(profile.getUserId()));
                        request.getSession().setAttribute("currentProfile", profile);
                    }
                    url = "/CatalogController?itemCode=" + item + "";
                }
                
                //code for a user to rate a user that you have swapped with
                if (action.equals("rateuser")) {
                    User user = (User) request.getSession().getAttribute("theUser");
                    int id = user.getUserId();
                    int offerID = Integer.parseInt(request.getParameter("offerID"));
                    int swapperID = Integer.parseInt(request.getParameter("swapperID"));
                    if (request.getParameter("rating") == null) {
                        
                    }
                    else {
                        int rating = Integer.parseInt(request.getParameter("rating"));
                        FeedbackDB feedback = new FeedbackDB();
                        feedback.addOfferFeedback(offerID, id, swapperID, rating);
                        rating = feedback.getOfferFeedback(swapperID);
                        newUser.UpdateUserRating(swapperID, rating);
                        offers.updateOffer(swapperID, rating);
                        UserProfile profile = (UserProfile) request.getSession().getAttribute("currentProfile");
                        profile.setUserItems(offers.getUserOffers(profile.getUserId()));
                        request.getSession().setAttribute("currentProfile", profile);
                    }
                    url = "/mySwaps.jsp";
                }
                
                //code for a user to sign out
                if (action.equals("signout")) {
                    request.getSession().invalidate();
                    url = "/CatalogController";
                }
            }
            getServletContext().getRequestDispatcher(url).forward(request, response);
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    
    //helper method for request action validation
    public boolean validateAction(String action) {
        boolean response = false;
        if (action != null) {
            if (action.equals("update") || action.equals("accept") || action.equals("reject")
                    || action.equals("withdraw") || action.equals("offer") || action.equals("delete")
                    || action.equals("signout") || action.equals("signin") || action.equals("register")
                    || action.equals("confirmSwap") || action.equals("rateitem") || action.equals("rateuser")) {
                response = true;
            }
        }
        return response;
    }
    
    //helper method for item validation
    public boolean validateItem(String item) {
        ItemDB items = new ItemDB();
        boolean response = false;
        if (item != null && item.length() == 7 && Character.isDigit(item.charAt(6)) && items.getItem(item)!=null) {
            response = true;
        }
        return response;
    }
    
    //helper method for action validation
    public boolean validateIntentionalAction(HttpServletRequest request, String item) {
        boolean response = false;
        String[] itemArray = request.getParameterValues("itemList");
        for (String itemArray1 : itemArray) {
            if (itemArray1.equals(item)) {
                response = true;
            }
        }
        return response;
    }
    
    //helper method that checks to see if the item is in a user's profile and has proper status
    public boolean checkProfileItem(UserProfile profile, String item, String status) {
        boolean response = false;
        for (int i = 0; i < profile.getUserItems().size(); i++) {
            if (profile.getUserItems().get(i).getUserItem().getCode().equals(item) && profile.getUserItems().get(i).getStatus().equals(status)) {
                response = true;
            }
        }
        return response;
    }
    
    //helper method that checks to see if the item is in the user's profile
    public boolean checkProfileItem(UserProfile profile, String item) {
        boolean response = false;
        for (int i = 0; i < profile.getUserItems().size(); i++) {
            if (profile.getUserItems().get(i).getUserItem().getCode().equals(item)) {
                response = true;
            }
        }
        return response;
    }
    
    //helper method to make sure username is valid
    public boolean validateUsername(String username) {
        boolean response = false;
        UserDB userDB = new UserDB();
        ArrayList<User> userList = userDB.getAllUsers();
        for (int i = 0; i < userList.size(); i++) {
            if (username.equals(userList.get(i).getEmail())) {
                response = true;
            }
        }
        return response;
    }
    
    //helper method to make sure password is valid
    public boolean validatePassword(String password) {
        boolean response = false;
        UserDB userDB = new UserDB();
        ArrayList<User> userList = userDB.getAllUsers();
        for (int i = 0; i < userList.size(); i++) {
            if (password.equals(userList.get(i).getPassword())) {
                response = true;
            }
        }
        return response;
    }
    
    //helper method to make sure password belongs to username
    public User validateUsernamePassword(String username, String password) {
        User user = null;
        UserDB userDB = new UserDB();
        ArrayList<User> userList = userDB.getAllUsers();
        for (int i = 0; i < userList.size(); i++) {
            if (username.equals(userList.get(i).getEmail()) && password.equals(userList.get(i).getPassword())) {
                user = userList.get(i);
            }
        }
        return user;
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
