/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jerryio.borsys.tag;

import com.jerryio.borsys.bean.BorrowItem;
import com.jerryio.borsys.bean.BorrowRecord;
import com.jerryio.borsys.enums.BorrowType;
import com.jerryio.borsys.enums.RequestStatus;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author jerrylum
 */
public class BorrowRecordBox extends SimpleTagSupport {
    
    private boolean isStudent;
    private BorrowRecord record;
    
    public void setRecord(BorrowRecord r) {
        record = r;
    }
    
    public BorrowRecord getRecord() {
        return record;
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
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            final Date now = new Date();
            
            BorrowRecord record = getRecord();
            RequestStatus status = record.getStatus();
            
            String buf = "";
            for (BorrowItem item : record.getItemList()) {
                String dueColor = "";
                String dueStatus = "";
                
                switch (item.getStatus()) {
                    case READY2PICKUP:
                        dueColor = "text-success";
                        dueStatus = "ready";
                        break;
                    case USING:
                        if (now.after(item.getTo())) {
                            dueColor = "text-danger";
                            dueStatus = "over due";
                        } else {
                            dueColor = "text-primary";
                            dueStatus = "using";
                        }
                        break;
                    case RETURNED:
                        dueColor = "text-muted";
                        dueStatus = "returned";
                        break;
                }
                
                buf += "<div class=\"row\">"
                        + "<div class=\"col-sm-6\" title=\"" + item.getEquipmentId() + "\">" 
                        + item.getEquipment().getName() + "</div>"
                        + "<div class=\"col-sm-3 " + dueColor + "\">" + dueStatus + "</div>"
                        + (
                            status == RequestStatus.CONFIRM && (
                                item.getStatus() == BorrowType.RETURNED ||
                                item.getStatus() == BorrowType.USING
                            )
                            ?   "<div class=\"col-sm-3\" title=\"from " + 
                                sdf.format(item.getFrom()) + " to " +
                                sdf.format(item.getTo()) + "\">" +
                                sdf.format(item.getTo()) + "</div>"
                            : ""
                        )
                        + "</div>";
            }
            
            
            String statusColor = "text-primary";
            if (status == RequestStatus.CONFIRM)
                statusColor = "text-success";
            else if (status == RequestStatus.DECLINE)
                statusColor = "text-danger";
            
            out.print(
                "<div class=\"card mb-3 shadow-sm\">" +
                "    <div class=\"card-body\">" +
                "        <p class=\"lead " + statusColor + " my-0\">" + status + "</p>" +
                "        <p class=\"text-muted mt-0\">Record ID: " + record.getId() + "</p>" +
                buf +
                "    </div>" +
                "</div>");
        } catch (IOException e) {
        }
    }
}