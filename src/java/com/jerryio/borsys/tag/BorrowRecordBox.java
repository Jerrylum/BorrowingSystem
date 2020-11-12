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
import java.util.ArrayList;
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
            if (getIsStudent()) printStudentVersion();
            else printTechnicianVersion();
        } catch (Exception e) {
        }
    }
    
    private String getRequestStatusColor(RequestStatus status) {
        String statusColor = "text-primary";
        if (status == RequestStatus.CONFIRM)
            statusColor = "text-success";
        else if (status == RequestStatus.DECLINE)
            statusColor = "text-danger";
        
        return statusColor;
    }
    
    private String getReadOnlyItemList(BorrowRecord record) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final Date now = new Date();

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
                    + "<div class=\"col-sm-6\" title=\"item id: " + item.getEquipmentId() + "\">" 
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
        
        return buf;
    }
    
    private void printStudentVersion() throws Exception {
        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();

        BorrowRecord re = getRecord();
        RequestStatus status = re.getStatus();

        String buf = getReadOnlyItemList(re);
        String statusColor = getRequestStatusColor(status);

        out.print(
            "<div class=\"card mb-3 shadow-sm\">" +
            "    <div class=\"card-body\">" +
            "        <p class=\"lead " + statusColor + " my-0\">" + status + "</p>" +
            "        <p class=\"text-muted mt-0\">Record ID: " + re.getId() + "</p>" +
            buf +
            "    </div>" +
            "</div>");
    }
    
    private void printTechnicianVersion() throws Exception {
        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();

        BorrowRecord re = getRecord();
        RequestStatus status = re.getStatus();
        
        String statusColor = getRequestStatusColor(status);
        
        
        if (status == RequestStatus.ASKED) {
            String buf = getReadOnlyItemList(re);
            
            out.print(
                "<div class=\"card mb-3 shadow-sm\">" +
                "    <div class=\"card-body\">" +
                "        <p class=\"lead " + statusColor + " my-0\">" + status + "</p>" +
                "        <p class=\"text-muted mt-0\">Record ID: " + re.getId() + " by " + re.getUser().getName() + "</p>" +
                buf +
                "        <br/>" +
                "        <a class=\"btn btn-success btn-inline btn-sm\" " +
                "           href=\"BorrowRecordConotroller?action=accept&id=" + re.getId() + "\">Accept</a>" +
                "        <a class=\"btn btn-danger btn-inline btn-sm\" " +
                "           href=\"BorrowRecordConotroller?action=decline&id=" + re.getId() + "\">Decline</a>" +
                "    </div>" +                        
                "</div>");
        } else if (status == RequestStatus.CONFIRM) {
            
        } else if (status == RequestStatus.DECLINE) {
            String buf = getReadOnlyItemList(re);

            out.print(
                "<div class=\"card mb-3 shadow-sm\">" +
                "    <div class=\"card-body\">" +
                "        <p class=\"lead " + statusColor + " my-0\">" + status + "</p>" +
                "        <p class=\"text-muted mt-0\">Record ID: " + re.getId() + "</p>" +
                buf +
                "    </div>" +
                "</div>");
        }

    }
}