package ehr.cfcs.com.ehr.Model;

/**
 * Created by Admin on 21-09-2017.
 */

public class EducationModel
{
    public String qualification;
    public String descipline;
    public String passingDate;
    public String institute;
    public String courseType;
    public String highestDegree;

    public EducationModel(String qualification, String descipline, String passingDate,
                          String institute, String courseType, String highestDegree) {
        this.qualification = qualification;
        this.descipline = descipline;
        this.passingDate = passingDate;
        this.institute = institute;
        this.courseType = courseType;
        this.highestDegree = highestDegree;
    }

    public String getQualification() {
        return qualification;
    }

    public String getDescipline() {
        return descipline;
    }

    public String getPassingDate() {
        return passingDate;
    }

    public String getInstitute() {
        return institute;
    }

    public String getCourseType() {
        return courseType;
    }

    public String getHighestDegree() {
        return highestDegree;
    }
}
