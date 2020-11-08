/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jerryio.borsys.tag;

import com.jerryio.borsys.bean.Equipment;
import com.jerryio.borsys.enums.ListingStatus;
import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author jerrylum
 */
public class EquipmentBox extends SimpleTagSupport {
    
    private boolean isStudent;
    private Equipment equipment;

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public boolean getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(boolean isStudent) {
        this.isStudent = isStudent;
    }
    
    public void doTag() {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            
            Equipment eq = getEquipment();
            
            out.print(
                "<div class=\"card mb-3 shadow-sm\">" +
                "    <div class=\"card-body\">" +
                    (getIsStudent() ?
                        "<p>ID: " + eq.getId() + "</p>" +
                        "<form style=\"display: inline\" action=\"EquipmentController\" method=\"POST\">" +
                        "    <input type=\"hidden\" name=\"action\" value=\"update\" />" +
                        "    <input type=\"hidden\" name=\"id\" value=\"" + eq.getId() + "\" />" +
                        "    <input type=\"text\" name=\"name\" class=\"form-control form-control-sm mb-2\" placeholder=\"Name\" value=\"" + eq.getName()+ "\" required/>" +
                        "    <textarea type=\"text\" name=\"description\" class=\"form-control form-control-sm mb-2\" placeholder=\"Description\" required>" + eq.getDescription() + "</textarea>" +
                        "    Status: " + eq.getStatus() + "<br/>" +
                        "    <input type=\"checkbox\" name=\"listing\" class=\"form-control form-control-sm mb-2\" " +
                        "           value=\"true\"" + (eq.getListing() == ListingStatus.ENABLE ? "checked" : "") + "/>" +
                        "    <label for=\"listing\"> Listing</label><br>" +
                        "    <br/>" +
                        "    <button class=\"btn btn-primary btn-inline btn-sm\" type=\"submit\">Update</button>" +
                        "</form>" + 
                        "<form style=\"display: inline\" action=\"UserController\" method=\"POST\">" +
                        "    <input type=\"hidden\" name=\"action\" value=\"delete\" />" +
                        "    <input type=\"hidden\" name=\"id\" value=\"" + eq.getId() + "\" />" +
                        "    <button class=\"btn btn-danger btn-inline btn-sm\" type=\"submit\">Delete</button>" +
                        "</form>"
                        : 
                        eq.getId()
                    ) +
                "    </div>" +
                "</div>");
        } catch (IOException e) {
        }
    }
}
