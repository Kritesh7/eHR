package ehr.cfcs.com.ehr.Model;

/**
 * Created by Admin on 25-09-2017.
 */

public class WarningModel
{
    public String warningDate;
    public String warningDetails;

    public WarningModel(String warningDate, String warningDetails) {
        this.warningDate = warningDate;
        this.warningDetails = warningDetails;
    }

    public String getWarningDate() {
        return warningDate;
    }

    public String getWarningDetails() {
        return warningDetails;
    }
}
