/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jerryio.borsys.tag;

import com.jerryio.borsys.bean.BorrowItem;
import com.jerryio.borsys.bean.BorrowRecord;
import com.jerryio.borsys.bean.Equipment;
import java.text.SimpleDateFormat;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author jerrylum
 */
public class BorrowItemBox extends SimpleTagSupport {
    
    private BorrowItem item;
    
    public void setItem(BorrowItem i) {
        item = i;
    }
    
    public BorrowItem getItem() {
        return item;
    }
    
    public void doTag() {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        
            PageContext pageContext = (PageContext) getJspContext();
            JspWriter out = pageContext.getOut();
                        
            Equipment eq = item.getEquipment();
            BorrowRecord record =  item.getRecord();
            
            String dateColor = item.isOverdue() ? "text-danger" : "";
            
            out.print("<div class=\"card mb-3 shadow-sm\">"
                + "    <div class=\"card-body\">"
                + "        <p>"
                + "            <span class=\"lead\">" + eq.getName() + "</span> "
                + "            <span class=\"text-muted\">id:" + eq.getId() + "</span><br/>"
                + "        </p>"
                + "        <span class=\"text-muted\">"
                        + "From " + sdf.format(item.getFrom()) + " "
                        + "to <span class=\"" + dateColor + "\">" + sdf.format(item.getTo()) + "</span><br/>"
                        + "Record ID: " + record.getId() + " by " + record.getUser().getName()
                + "        </span>"
                + "    </div>"
                + "</div>");
        } catch (Exception e) {
        }
    }
}