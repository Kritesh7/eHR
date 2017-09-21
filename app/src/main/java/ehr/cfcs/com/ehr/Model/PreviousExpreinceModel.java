package ehr.cfcs.com.ehr.Model;

/**
 * Created by Admin on 21-09-2017.
 */

public class PreviousExpreinceModel
{

    public String companyName;
    public String joiningDate;
    public String jobDescription;
    public String jobPeriode;
    public String designation;

    public PreviousExpreinceModel(String companyName, String joiningDate, String jobDescription, String jobPeriode, String designation) {
        this.companyName = companyName;
        this.joiningDate = joiningDate;
        this.jobDescription = jobDescription;
        this.jobPeriode = jobPeriode;
        this.designation = designation;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getJobPeriode() {
        return jobPeriode;
    }

    public String getDesignation() {
        return designation;
    }
}
