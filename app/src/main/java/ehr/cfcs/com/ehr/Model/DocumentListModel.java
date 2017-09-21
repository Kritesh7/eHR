package ehr.cfcs.com.ehr.Model;

/**
 * Created by Admin on 18-09-2017.
 */

public class DocumentListModel
{

    public String employName;
    public String zoneName;
    public String qunatity;
    public String rquestDate;
    public String idleClosersDate;
    public String folllowUpDate;
    public String status;

    public DocumentListModel(String employName, String zoneName, String qunatity, String rquestDate,
                             String idleClosersDate, String folllowUpDate, String status) {
        this.employName = employName;
        this.zoneName = zoneName;
        this.qunatity = qunatity;
        this.rquestDate = rquestDate;
        this.idleClosersDate = idleClosersDate;
        this.folllowUpDate = folllowUpDate;
        this.status = status;
    }

    public String getEmployName() {
        return employName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public String getQunatity() {
        return qunatity;
    }

    public String getRquestDate() {
        return rquestDate;
    }

    public String getIdleClosersDate() {
        return idleClosersDate;
    }

    public String getFolllowUpDate() {
        return folllowUpDate;
    }

    public String getStatus() {
        return status;
    }
}
