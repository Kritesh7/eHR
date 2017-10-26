package ehr.cfcs.com.ehr.Model;

/**
 * Created by Admin on 20-09-2017.
 */

public class MedicalAnssuranceModel
{
    public String policyType;
    public String policyNumber;
    public String policyDuration;
    public String policyName;
    public String policyInsured;
    public String policyBy;
    public String recordId;
    public String insuranceComp;
    public String startDate;
    public String endDate;

    public MedicalAnssuranceModel(String policyType, String policyNumber, String policyDuration, String policyName,
                                  String policyInsured, String policyBy, String recordId, String insuranceComp, String startDate,
                                  String endDate) {
        this.policyType = policyType;
        this.policyNumber = policyNumber;
        this.policyDuration = policyDuration;
        this.policyName = policyName;
        this.policyInsured = policyInsured;
        this.policyBy = policyBy;
        this.recordId = recordId;
        this.insuranceComp = insuranceComp;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getInsuranceComp() {
        return insuranceComp;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getPolicyBy() {
        return policyBy;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getPolicyType() {
        return policyType;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getPolicyDuration() {
        return policyDuration;
    }

    public String getPolicyName() {
        return policyName;
    }

    public String getPolicyInsured() {
        return policyInsured;
    }
}
