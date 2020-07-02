package com.fund.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jiangfei on 2020/6/30.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundDayRateModel extends RecursiveTreeObject<FundDayRateModel> {
    private Integer id;
    private Integer baseId;
    private String code;
    private String day;
    private Integer week;

    private Float unitValue;
    private Float grandValue;
    private Integer rateType;
    private Float rate;


    public StringProperty dayProperty() {
        return new SimpleStringProperty(this.day);
    }

    public StringProperty weekProperty() {
        if (this.week == 1) {
            return new SimpleStringProperty("星期一");
        } else if (this.week == 2) {
            return new SimpleStringProperty("星期二");
        } else if (this.week == 3) {
            return new SimpleStringProperty("星期三");
        } else if (this.week == 4) {
            return new SimpleStringProperty("星期四");
        } else if (this.week == 5) {
            return new SimpleStringProperty("星期五");
        } else if (this.week == 6) {
            return new SimpleStringProperty("星期六");
        } else {
            return new SimpleStringProperty("星期日");
        }
    }

    public StringProperty unitProperty() {
        return new SimpleStringProperty(String.valueOf(this.unitValue));
    }

    public StringProperty grandProperty() {
        return new SimpleStringProperty(String.valueOf(this.grandValue));
    }

    public StringProperty rateProperty() {
        String value = String.valueOf(this.rate) + "%";
        if (rateType == 1) {
            value = "+" + value;
        } else {
            value = "-" + value;
        }
        return new SimpleStringProperty(value);
    }
}
