/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jerryio.borsys.servlet;

import com.jerryio.borsys.Util;
import com.jerryio.borsys.bean.User;
import com.jerryio.borsys.db.UserDB;
import com.jerryio.borsys.factory.ObjectDBFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jerrylum
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

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
        String action = request.getParameter("action");

        if (Util.getMe(request) == null) {
            if ("login".equals(action)) {
                doLogin(request, response);
                return;
            }
        } else {
            if ("logout".equals(action)) {
                doLogout(request, response);
                return;
            }
        }
        
        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }
    
    private void doLogin(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        
        UserDB db = ObjectDBFactory.getUserDB();
        
        for (User u : db.getAllUsers()) {
            if ((u.getId() + "").equals(id)) {
                if (u.getPwd().equals(pwd) == false) {
                    break;
                }

                HttpSession s = request.getSession();
                s.setAttribute("me", u);
                
                Util.redirect(request, response, "/index.jsp");
                return;
            }
        }
        
        request.setAttribute("error", true);
        Util.forward(getServletContext(), request, response, "/login.jsp");
    }
    
    private void doLogout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            session.removeAttribute("me");
            session.invalidate();
        }
        
        Util.forward(getServletContext(), request, response, "/index.jsp");
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