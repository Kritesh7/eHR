package ehr.cfcs.com.ehr.Model;

/**
 * Created by Admin on 25-09-2017.
 */

public class WarningModel
{
    public String warningDate;
    public String warningDetails;
    public String warningTitle;

    public WarningModel(String warningDate, String warningDetails, String warningTitle) {
        this.warningDate = warningDate;
        this.warningDetails = warningDetails;
        this.warningTitle = warningTitle;
    }

    public String getWarningTitle() {
        return warningTitle;
    }

    public String getWarningDate() {
        return warningDate;
    }

    public String getWarningDetails() {
        return warningDetails;
    }
}
