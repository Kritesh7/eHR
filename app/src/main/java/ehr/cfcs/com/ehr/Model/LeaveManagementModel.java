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
    public String LeaveApplication_Id;
    public String Noofdays;


    public LeaveManagementModel(String leaveType, String startDate, String endDate, String appliedOn, String status,
                                String LeaveApplication_Id, String Noofdays) {
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.appliedOn = appliedOn;
        this.status = status;
        this.Noofdays = Noofdays;
        this.LeaveApplication_Id = LeaveApplication_Id;
    }

    public String getNoofdays() {
        return Noofdays;
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

    public String getLeaveApplication_Id() {
        return LeaveApplication_Id;
    }
}
