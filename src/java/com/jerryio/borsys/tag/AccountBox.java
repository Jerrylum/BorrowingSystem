/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jerryio.borsys.tag;

import com.jerryio.borsys.bean.User;
import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author jerrylum
 */
public class AccountBox extends SimpleTagSupport {
    
    private User user;
    
    public void setUser(User u) {
        user = u;
    }
    
    public User getUser() {
        return user;
    }
    
    public void doTag() {
        try {
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
            
            String role = user.getRole().toString();
            
            out.print(
                    "<div class=\"card mb-3 shadow-sm\">" +
                    "    <div class=\"card-body\">" +
                    "        <p>ID: " + user.getId() + "</p>" +
                    "        <form style=\"display: inline\" action=\"UserController\" method=\"POST\">" +
                    "            <input type=\"hidden\" name=\"action\" value=\"update\" />" +
                    "            <input type=\"hidden\" name=\"id\" value=\"" + user.getId() + "\" />" +
                    "            <input type=\"text\" name=\"name\" class=\"form-control form-control-sm mb-2\" placeholder=\"Username\" value=\"" + user.getName()+ "\" required/>" +
                    "            <input type=\"password\" name=\"pwd\" class=\"form-control form-control-sm mb-2\" placeholder=\"New password\" value=\"\"/>" +
                    "            <select name=\"type\" class=\"form-control form-control-sm col-4\" required/>" +
                    "            <option value=\"STUDENT\" " + ("STUDENT".equals(role) ? "selected" : "") + ">Student</option>" +
                    "            <option value=\"TECHNICIAN\" " + ("TECHNICIAN".equals(role) ? "selected" : "") + ">Technician</option>" +
                    "            <option value=\"ADMIN\" " + ("ADMIN".equals(role) ? "selected" : "") + ">Admin</option>" +
                    "            </select>" +
                    "            <br/>" +
                    "            <button class=\"btn btn-primary btn-inline btn-sm\" type=\"submit\">Update</button>" +
                    "        </form>" +
                    "        <form style=\"display: inline\" action=\"UserController\" method=\"POST\">" +
                    "            <input type=\"hidden\" name=\"action\" value=\"delete\" />" +
                    "            <input type=\"hidden\" name=\"id\" value=\"" + user.getId() + "\" />" +
                    "            <button class=\"btn btn-danger btn-inline btn-sm\" type=\"submit\">Delete</button>" +
                    "        </form>" +
                    "    </div>" +
                    "</div>");
        } catch (IOException e) {
        }
    }
}