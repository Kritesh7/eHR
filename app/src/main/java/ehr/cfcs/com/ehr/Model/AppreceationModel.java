package ehr.cfcs.com.ehr.Model;

/**
 * Created by Admin on 25-09-2017.
 */

public class AppreceationModel
{
    public String appreceationDate;
    public String appreceationDetails;

    public AppreceationModel(String appreceationDate, String appreceationDetails) {
        this.appreceationDate = appreceationDate;
        this.appreceationDetails = appreceationDetails;
    }

    public String getAppreceationDate() {
        return appreceationDate;
    }

    public String getAppreceationDetails() {
        return appreceationDetails;
    }
}
