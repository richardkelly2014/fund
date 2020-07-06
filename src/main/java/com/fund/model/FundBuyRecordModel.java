package com.fund.model;

import com.fund.util.DateTimeUtil;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

/**
 * Created by jiangfei on 2020/7/4.
 */
@Data
public class FundBuyRecordModel extends RecursiveTreeObject<FundBuyRecordModel> {

    private Integer id;
    private String fundName;
    private String fundCode;

    private Float buyMoney;
    private String buyDateTime;

    private Float currentMoney;
    private Float currentProfitValue;

    private Float confirmMoney;
    private Float confirmPortion;
    private Float confirmUnit;
    private String confirmDay;

    private Integer status;

    public StringProperty fundNameProperty() {
        return new SimpleStringProperty(this.fundName);
    }

    public StringProperty fundCodeProperty() {
        return new SimpleStringProperty(this.fundCode);
    }

    public StringProperty buyMoneyProperty() {
        return new SimpleStringProperty(String.valueOf(buyMoney));
    }

    public StringProperty buyDateTimeProperty() {
        return new SimpleStringProperty(buyDateTime);
    }

    public StringProperty currentMoneyProperty() {
        return new SimpleStringProperty(String.valueOf(currentMoney));
    }

    public StringProperty currentProfitValueProperty() {
        return new SimpleStringProperty(String.valueOf(currentProfitValue));
    }

    public StringProperty confirmMoneyProperty() {
        return new SimpleStringProperty(String.valueOf(confirmMoney));
    }

    public StringProperty confirmPortionProperty() {
        return new SimpleStringProperty(String.valueOf(confirmPortion));
    }

    public StringProperty confirmUnitProperty() {
        return new SimpleStringProperty(String.valueOf(confirmUnit));
    }

    public StringProperty confirmDayProperty() {
        int day = DateTimeUtil.differDay(confirmDay);
        return new SimpleStringProperty(confirmDay + "(" + day + ")");
    }
}
