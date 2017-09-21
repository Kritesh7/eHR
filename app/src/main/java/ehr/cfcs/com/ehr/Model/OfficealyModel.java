package ehr.cfcs.com.ehr.Model;

/**
 * Created by Admin on 20-09-2017.
 */

public class OfficealyModel
{
    public String documentType;
    public String noOfDocuments;
    public String issuesDate;
    public String expiryDate;
    public String placeOfIssues;

    public OfficealyModel(String documentType, String noOfDocuments, String issuesDate, String expiryDate, String placeOfIssues) {
        this.documentType = documentType;
        this.noOfDocuments = noOfDocuments;
        this.issuesDate = issuesDate;
        this.expiryDate = expiryDate;
        this.placeOfIssues = placeOfIssues;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getNoOfDocuments() {
        return noOfDocuments;
    }

    public String getIssuesDate() {
        return issuesDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getPlaceOfIssues() {
        return placeOfIssues;
    }
}
