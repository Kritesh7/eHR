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

    public MedicalAnssuranceModel(String policyType, String policyNumber, String policyDuration,
                                  String policyName, String policyInsured) {
        this.policyType = policyType;
        this.policyNumber = policyNumber;
        this.policyDuration = policyDuration;
        this.policyName = policyName;
        this.policyInsured = policyInsured;
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
