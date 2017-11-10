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
    public String LeaveAvail;

    public LeaveSummarryModel(String leaveType, String leaveYear, String entitilement,
                              String carryOver, String approved, String balance, String LeaveAvail) {
        this.leaveType = leaveType;
        this.leaveYear = leaveYear;
        this.entitilement = entitilement;
        this.carryOver = carryOver;
        this.approved = approved;
        this.balance = balance;
        this.LeaveAvail = LeaveAvail;
    }

    public String getLeaveAvail() {
        return LeaveAvail;
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
