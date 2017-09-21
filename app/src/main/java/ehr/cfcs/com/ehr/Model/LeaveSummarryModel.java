package ehr.cfcs.com.ehr.Model;

/**
 * Created by Admin on 21-09-2017.
 */

public class LeaveSummarryModel
{
    public String leaveType;
    public String leaveYear;
    public String entitilement;
    public String carryOver;
    public String approved;
    public String balance;

    public LeaveSummarryModel(String leaveType, String leaveYear, String entitilement,
                              String carryOver, String approved, String balance) {
        this.leaveType = leaveType;
        this.leaveYear = leaveYear;
        this.entitilement = entitilement;
        this.carryOver = carryOver;
        this.approved = approved;
        this.balance = balance;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public String getLeaveYear() {
        return leaveYear;
    }

    public String getEntitilement() {
        return entitilement;
    }

    public String getCarryOver() {
        return carryOver;
    }

    public String getApproved() {
        return approved;
    }

    public String getBalance() {
        return balance;
    }
}
