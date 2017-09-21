package ehr.cfcs.com.ehr.Model;

/**
 * Created by Admin on 20-09-2017.
 */

public class DependentModel
{
    public String name;
    public String dob;
    public String gender;
    public String relationship;

    public DependentModel(String name, String dob, String gender, String relationship) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.relationship = relationship;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getRelationship() {
        return relationship;
    }
}
