/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jerryio.borsys.servlet;

import com.jerryio.borsys.Util;
import com.jerryio.borsys.bean.Equipment;
import com.jerryio.borsys.db.EquipmentDB;
import com.jerryio.borsys.enums.AvailabilityStatus;
import com.jerryio.borsys.enums.ListingStatus;
import com.jerryio.borsys.factory.ObjectDBFactory;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "EquipmentController", urlPatterns = {"/EquipmentController"})
public class EquipmentController extends HttpServlet {

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

        if ("add".equals(action)) {
            doAddEq(request, response);
            return;
        } else if ("update".equals(action)) {
            doUpdateEq(request, response);
            return;
        } else if ("delete".equals(action)) {
            doDeleteEq(request, response);
            return;
        }
        
        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }
        
    private void doAddEq(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String listing = request.getParameter("listing");
        
        EquipmentDB db = ObjectDBFactory.getEquipmentDB();
        
        Equipment e = db.add(name, description, AvailabilityStatus.FREE, "true".equals(listing) ? ListingStatus.ENABLE : ListingStatus.DISABLE);
        
        request.setAttribute("error", e == null);
        Util.forward(getServletContext(), request, response, "/inventory.jsp");
    }

    private void doUpdateEq(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String listing = request.getParameter("listing");
        
        EquipmentDB db = ObjectDBFactory.getEquipmentDB();
        Equipment eq = db.getEquipment(Integer.parseInt(id));
        eq.setName(name);
        eq.setDescription(description);
        eq.setListing("true".equals(listing) ? ListingStatus.ENABLE : ListingStatus.DISABLE);
        
        request.setAttribute("error", db.update(eq));
        Util.forward(getServletContext(), request, response, "/inventory.jsp");
    }
            
    private void doDeleteEq(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        
        EquipmentDB db = ObjectDBFactory.getEquipmentDB();
        
        request.setAttribute("error", db.delete(Integer.parseInt(id)));
        Util.forward(getServletContext(), request, response, "/inventory.jsp");
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
