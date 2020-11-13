/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jerryio.borsys.tag;

import com.jerryio.borsys.bean.Equipment;
import com.jerryio.borsys.enums.AvailabilityStatus;
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
    private boolean canAddToCart = false;
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
    
    public boolean getCanAddToCart() {
        return canAddToCart;
    }

    public void setCanAddToCart(boolean canAddToCart) {
        this.canAddToCart = canAddToCart;
    }
    
    public void doTag() {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            
            Equipment eq = getEquipment();
            boolean canAdd = getCanAddToCart() && eq.getStatus() == AvailabilityStatus.FREE;
            
            out.print(
                "<div class=\"card mb-3 shadow-sm\">" +
                "    <div class=\"card-body\">" +
                    (getIsStudent() ?
                        "<p class=\"lead\">" + eq.getName()+ " <span class=\"text-muted\">(ID: " + eq.getId() + ")</span></p>" +
                        "<p class=\"md-1\">" + eq.getDescription()+ "</p>" +
                        "<form action=\"discover.jsp\" method=\"POST\">" +
                        "    <input type=\"hidden\" name=\"action\" value=\"add\" />" +
                        "    <input type=\"hidden\" name=\"id\" value=\"" + eq.getId() + "\" />" +
                        "    <button class=\"btn " + (
                            canAdd ? "btn-primary" : "btn-dark"
                        ) + " btn-inline btn-sm\" type=\"submit\"" + (
                            canAdd ? "" : "disabled title=\"Added\""
                        ) + ">Add to cart</button> " +
                        (eq.getStatus() == AvailabilityStatus.USED ? "<span class=\"top-right\">USED</span>" : "") +
                        "</form>"
                        : 
                        "<p>ID: " + eq.getId() + "</p>" +
                        "<form style=\"display: inline\" action=\"EquipmentController\" method=\"POST\">" +
                        "    <input type=\"hidden\" name=\"action\" value=\"update\" />" +
                        "    <input type=\"hidden\" name=\"id\" value=\"" + eq.getId() + "\" />" +
                        "    <input type=\"text\" name=\"name\" class=\"form-control form-control-sm mb-2\" placeholder=\"Name\" value=\"" + eq.getName()+ "\" required/>" +
                        "    <textarea name=\"description\" class=\"form-control form-control-sm mb-2\" placeholder=\"Description\" required>" + eq.getDescription() + "</textarea>" +
                        "    Status: " + eq.getStatus() + "<br/>" +
                        "    <div class=\"form-check\">" +
                        "        <input type=\"checkbox\" name=\"listing\" class=\"form-check-input\" " +
                        "               value=\"true\"" + (eq.getListing() == ListingStatus.ENABLE ? "checked" : "") + "/>" +
                        "        <label class=\"form-check-label\" for=\"listing\"> Listing</label><br>" +
                        "    </div>" +
                        "    <br/>" +
                        "    <button class=\"btn btn-primary btn-inline btn-sm\" type=\"submit\">Update</button>" +
                        "</form>" + 
                        "<form style=\"display: inline\" action=\"EquipmentController\" method=\"POST\">" +
                        "    <input type=\"hidden\" name=\"action\" value=\"delete\" />" +
                        "    <input type=\"hidden\" name=\"id\" value=\"" + eq.getId() + "\" />" +
                        "    <button class=\"btn btn-danger btn-inline btn-sm\" type=\"submit\">Delete</button>" +
                        "</form>"
                    ) +
                "    </div>" +
                "</div>");
        } catch (IOException e) {
        }
    }
}
