package ehr.cfcs.com.ehr.Model;

import java.io.Serializable;

/**
 * Created by Admin on 21-09-2017.
 */

public class BookMeaPrevisionModel implements Serializable {

    public String itemName;
    public String ItemID;
    public String MaxQuantity;
    public String remark;
    public String checkValue;

    private boolean toKill;
    ///getters/setters
    public boolean isToKill() {
        return toKill;
    }
    public void setToKill(boolean toKill) {
        this.toKill = toKill;
    }


    public BookMeaPrevisionModel(String itemName,String ItemID,String MaxQuantity,String remark,String checkValue) {
        this.itemName = itemName;
        this.ItemID = ItemID;
        this.MaxQuantity = MaxQuantity;
        this.remark = remark;
        this.checkValue = checkValue;
    }



    public String getRemark() {
        return remark;
    }

    public String getCheckValue() {
        return checkValue;
    }

    public String getItemID() {
        return ItemID;
    }

    public String getMaxQuantity() {
        return MaxQuantity;
    }

    public void setMaxQuantity(String maxQuantity) {
        MaxQuantity = maxQuantity;
    }

    public String getItemName() {
        return itemName;
    }
}
