package ehr.cfcs.com.ehr.Model;

/**
 * Created by Admin on 18-09-2017.
 */

public class AssestDetailsModel
{
    public String assetsName;
    public String brandName;
    public String issuesDate;
    public String expectedReturnDate;
    public String issuesReason;
    public String Remark;

    public AssestDetailsModel(String assetsName, String brandName, String issuesDate,
                              String expectedReturnDate, String issuesReason, String remark) {
        this.assetsName = assetsName;
        this.brandName = brandName;
        this.issuesDate = issuesDate;
        this.expectedReturnDate = expectedReturnDate;
        this.issuesReason = issuesReason;
        Remark = remark;
    }

    public String getAssetsName() {
        return assetsName;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getIssuesDate() {
        return issuesDate;
    }

    public String getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public String getIssuesReason() {
        return issuesReason;
    }

    public String getRemark() {
        return Remark;
    }
}
