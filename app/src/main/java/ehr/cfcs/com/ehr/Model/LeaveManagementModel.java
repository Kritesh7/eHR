package ehr.cfcs.com.ehr.Model;

/**
 * Created by Admin on 18-09-2017.
 */

public class LeaveManagementModel
{
    public String leaveType;
    public String startDate;
    public String endDate;
    public String appliedOn;
    public String status;


    public LeaveManagementModel(String leaveType, String startDate, String endDate, String appliedOn, String status) {
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.appliedOn = appliedOn;
        this.status = status;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getAppliedOn() {
        return appliedOn;
    }

    public String getStatus() {
        return status;
    }
}
