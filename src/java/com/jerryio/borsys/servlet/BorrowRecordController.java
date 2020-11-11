/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jerryio.borsys.servlet;

import com.jerryio.borsys.Util;
import com.jerryio.borsys.bean.BorrowRecord;
import com.jerryio.borsys.bean.Equipment;
import com.jerryio.borsys.bean.User;
import com.jerryio.borsys.db.BorrowItemDB;
import com.jerryio.borsys.db.BorrowRecordDB;
import com.jerryio.borsys.db.EquipmentDB;
import com.jerryio.borsys.enums.AvailabilityStatus;
import com.jerryio.borsys.enums.BorrowType;
import com.jerryio.borsys.enums.ListingStatus;
import com.jerryio.borsys.enums.RequestStatus;
import com.jerryio.borsys.factory.ObjectDBFactory;
import static com.jerryio.borsys.test.BorrowTest.db;
import static com.jerryio.borsys.test.BorrowTest.db2;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jerrylum
 */
@WebServlet(name = "BorrowRecordController", urlPatterns = {"/BorrowRecordController"})
public class BorrowRecordController extends HttpServlet {

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
            Util.redirect(request, response, "/login.jsp");
            return;
        }
                
        if ("checkout".equals(action)) {
            doConfirmCart(request, response);
            return;
        }
        
        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }
    
    private void doConfirmCart(HttpServletRequest request, HttpServletResponse response) {
        boolean error = false;
        
        try {
            ArrayList<Equipment> cart = (ArrayList<Equipment>)request.getSession().getAttribute("cart");
            
            if (cart == null || cart.isEmpty()) {
                error = true;
                return;
            }
            
            BorrowRecordDB db = ObjectDBFactory.getBorrowRecordDB();
            BorrowItemDB db2 = ObjectDBFactory.getBorrowItemDB();
            EquipmentDB db3 = ObjectDBFactory.getEquipmentDB();
            Connection conn = Util.getConnection();
            User me = Util.getMe(request);
            
            ////////////////////////////////////////////////
            
            Date da = new Date(); // TODAY
            Date dt = new Date(da.getTime());
            Calendar c = Calendar.getInstance(); 
            c.setTime(dt); 
            c.add(Calendar.DATE, 7); // 1 week
            dt = c.getTime();
            
            Equipment check = cart.get(0);
            
            ////////////////////////////////////////////////
            
            conn.setAutoCommit(false);

            BorrowRecord br = db.add(me.getId(), RequestStatus.ASKED);
            
            for (Equipment eq : cart) {
                if (eq.getStatus() == AvailabilityStatus.USED) {              
                    error = true;
                    return;
                }
                
                eq.setStatus(AvailabilityStatus.USED);
                db2.add(br.getId(), eq.getId(), BorrowType.ASKED, da, dt);
                db3.update(eq);
            }

            conn.commit();
            conn.setAutoCommit(true);
            
            ////////////////////////////////////////////////
            
            ArrayList<Equipment> checksum = db3.queryAll();
            
            for (Equipment eq : checksum) {
                if (eq.getId() == check.getId()) {
                    error = eq.getStatus() != check.getStatus();
                    break;
                }                    
            }
            
            cart.clear();
            
        } catch (Exception ex) {
            try {
                Connection conn = Util.getConnection();
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (Exception ex2) {
                
            }            
        } finally {
            request.setAttribute("error", error);
            
            if (error)
                Util.redirect(request, response, "/discover.jsp");
            else
                Util.forward(getServletContext(), request, response, "/records.jsp");
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
