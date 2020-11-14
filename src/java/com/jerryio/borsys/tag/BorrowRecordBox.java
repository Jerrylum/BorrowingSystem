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
import java.util.Calendar;
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
                    if ((now.getTime() - item.getTo().getTime()) > 86400000) {
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
    
    
    private String getEditableItemList(BorrowRecord record) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final Date now = new Date();

        RequestStatus status = record.getStatus();
        
        String buf = "";
        for (BorrowItem item : record.getItemList()) {
            if (item.getStatus() == BorrowType.ASKED) {
                buf += "<div class=\"row\">"
                        + "<div class=\"col-sm-6\" title=\"item id: " + item.getEquipmentId() + "\">" 
                        + item.getEquipment().getName() + "</div>"
                        + "<a class=\"col-sm-3 text-primary\" "
                        + "   href=\"BorrowRecordController?action=item2ready&record=" + item.getRecordId() + "&eq=" + item.getEquipmentId() + "\">> Prepared</a>"
                     + "</div>";
            } else if (item.getStatus() == BorrowType.READY2PICKUP) {
                int noOfDays = 7; //i.e 1 week
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(now);            
                calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
                Date toDate = calendar.getTime();

                buf += "<div class=\"row\">"
                        + "<div class=\"col-sm-6\" title=\"item id: " + item.getEquipmentId() + "\">" 
                        + item.getEquipment().getName() + "</div>"
                        + "<form action=\"BorrowRecordController\" "
                        + "      method=\"POST\" "
                        + "      class=\"col-sm-6 row\" "
                        + "      style=\"padding-right: 0px;\">"
                        + "        <input type=\"hidden\" name=\"action\" value=\"item2using\" />"
                        + "        <input type=\"hidden\" name=\"record\" value=\"" + item.getRecordId() + "\" />"
                        + "        <input type=\"hidden\" name=\"eq\" value=\"" + item.getEquipmentId() + "\" />"
                        + "        <a type=\"submit\" "
                        + "           class=\"col-sm-5 text-primary\" "
                        + "           onclick=\"this.closest('form').submit();return false;\">> Gave</a>"
                        + "        <input type=\"date\" "
                        + "               class=\"col-sm-7\" "
                        + "               style=\"font-size: 12px; padding: 0 2px;\" "
                        + "               name=\"to\" "
                        + "               min=\"" + sdf.format(now) + "\""
                        + "               value=\"" + sdf.format(toDate) + "\" />"
                        + "</form>"
                     + "</div>";
            } else if (item.getStatus() == BorrowType.USING) {                
                String dueColor = item.isOverdue() ? "text-danger" : "";
            
                buf += "<div class=\"row\">"
                        + "<div class=\"col-sm-6\" title=\"item id: " + item.getEquipmentId() + "\">" 
                        + item.getEquipment().getName() + "</div>"
                        + "<a class=\"col-sm-3 text-primary\" "
                        + "   href=\"BorrowRecordController?action=item2return&record=" + item.getRecordId() + "&eq=" + item.getEquipmentId() + "\">> Returned</a>"
                        + "<div class=\"col-sm-3 " + dueColor + "\" title=\"from " + 
                            sdf.format(item.getFrom()) + " to " +
                            sdf.format(item.getTo()) + "\">" +
                            sdf.format(item.getTo()) + "</div>"
                     + "</div>";
            } else if (item.getStatus() == BorrowType.RETURNED) {
                buf += "<div class=\"row\">"
                        + "<div class=\"col-sm-6\" title=\"item id: " + item.getEquipmentId() + "\">" 
                        + item.getEquipment().getName() + "</div>"
                        + "<div class=\"col-sm-3 text-muted\" " + item.getEquipmentId() + ">returned</div>"
                        + "<div class=\"col-sm-3\" title=\"gave on " + 
                            sdf.format(item.getFrom()) + " returned on " +
                            sdf.format(item.getTo()) + "\">" +
                            sdf.format(item.getTo()) + "</div>"
                     + "</div>";
            }
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
                "        <p class=\"text-muted mt-0\">Record ID: " + re.getId() + " by " +
                "            <span title=\"User ID: " + re.getUserId() + "\">" + re.getUser().getName() + "</span>" +
                "        </p>" +
                buf +
                "        <br/>" +
                "        <a class=\"btn btn-success btn-inline btn-sm\" " +
                "           href=\"BorrowRecordController?action=accept&id=" + re.getId() + "\">Accept</a>" +
                "        <a class=\"btn btn-danger btn-inline btn-sm\" " +
                "           href=\"BorrowRecordController?action=decline&id=" + re.getId() + "\">Decline</a>" +
                "    </div>" +                        
                "</div>");
        } else if (status == RequestStatus.CONFIRM) {
            String buf = getEditableItemList(re);

            out.print(
                "<div class=\"card mb-3 shadow-sm\">" +
                "    <div class=\"card-body\">" +
                "        <p class=\"lead " + statusColor + " my-0\">" + status + "</p>" +
                "        <p class=\"text-muted mt-0\">Record ID: " + re.getId() + " by " +
                "            <span title=\"User ID: " + re.getUserId() + "\">" + re.getUser().getName() + "</span>" +
                "        </p>" +
                buf +
                "    </div>" +
                "</div>");
        } else if (status == RequestStatus.DECLINE) {
            String buf = getReadOnlyItemList(re);

            out.print(
                "<div class=\"card mb-3 shadow-sm\">" +
                "    <div class=\"card-body\">" +
                "        <p class=\"lead " + statusColor + " my-0\">" + status + "</p>" +
                "        <p class=\"text-muted mt-0\">Record ID: " + re.getId() + " by " +
                "            <span title=\"User ID: " + re.getUserId() + "\">" + re.getUser().getName() + "</span>" +
                "        </p>" +
                buf +
                "    </div>" +
                "</div>");
        }

    }
}