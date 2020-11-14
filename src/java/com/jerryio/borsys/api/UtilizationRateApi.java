/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jerryio.borsys.api;

import com.jerryio.borsys.bean.BorrowItem;
import com.jerryio.borsys.bean.Equipment;
import com.jerryio.borsys.enums.BorrowType;
import com.jerryio.borsys.factory.ObjectDBFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jerrylum
 */
@WebServlet(name = "UtilizationRateApi", urlPatterns = {"/api/UtilizationRate"})
public class UtilizationRateApi extends HttpServlet {

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
        

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            Date from = sdf.parse(request.getParameter("from"));
            Date to = sdf.parse(request.getParameter("to"));
            long rFrom = from.getTime();
            long rTo = to.getTime();
            
            HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
            ArrayList<BorrowItem> items = ObjectDBFactory.getBorrowItemDB().getAllBorrowItems();
            
            ////////////////////////////////////////////////
            
            for (BorrowItem item : items) {
                boolean ok =
                        (                        
                            (from.compareTo(item.getFrom()) <= 0 && to.compareTo(item.getFrom()) >= 0) || 
                            (to.compareTo(item.getTo()) >= 0 && from.compareTo(item.getTo()) <= 0)
                        ) &&
                        (
                            item.getStatus() == BorrowType.RETURNED
                        );
                
                if (!ok) continue;
                
                int now = result.getOrDefault(item.getEquipmentId(), 0);
                long iFrom = item.getFrom().getTime();
                long iTo = item.getTo().getTime();
                
                now += ((Math.min(iTo, rTo) - Math.max(iFrom, rFrom)) / 86400000) + 1;
                
                result.put(item.getEquipmentId(), now);
            }
            
            long totalday = ((rTo - rFrom) / 86400000) + 1;
            
            
            ////////////////////////////////////////////////
            
            
            JsonArrayBuilder main = Json.createArrayBuilder();
            
            JsonArrayBuilder header = Json.createArrayBuilder();
            header.add("Equipment");
            header.add("Utilization Rate");
            main.add(header);

            ArrayList<Equipment> equipments = ObjectDBFactory.getEquipmentDB().getAllEquipments();
            for (Equipment eq : equipments) {
                int idx = eq.getId();
                
                int count = result.getOrDefault(idx, 0);
//                if (count == 0) continue;
                
                JsonArrayBuilder content = Json.createArrayBuilder();
                content.add(eq.getName() + ":" + eq.getId());
                content.add( ((int)((float)count/totalday * 1000 )) / 10.0 );
                main.add(content);
            }
            
            JsonWriter jsonWriter = Json.createWriter(out);
            jsonWriter.writeArray(main.build());
        } catch (ParseException ex) {
            
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
