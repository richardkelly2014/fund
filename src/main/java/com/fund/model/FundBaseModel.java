package com.fund.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jiangfei on 2020/6/27.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundBaseModel extends RecursiveTreeObject<FundBaseModel> {
    private Integer id;
    private String name;
    private String code;
    private Integer riskLevel;
    private Integer type;
    private Integer rateLevel;

    public StringProperty nameProperty() {
        return new SimpleStringProperty(this.name);
    }

    public StringProperty codeProperty() {
        return new SimpleStringProperty(this.code);
    }

    public StringProperty typeProperty() {
        if (type == null) {
            return new SimpleStringProperty("未知");
        } else if (type == 1) {
            return new SimpleStringProperty("指数");
        } else if (type == 2) {
            return new SimpleStringProperty("混合");
        } else {
            return new SimpleStringProperty("未知");
        }
    }

    public StringProperty rateLevelProperty() {
        if (rateLevel == null) {
            return new SimpleStringProperty("未知");
        } else {
            return new SimpleStringProperty(rateLevel + "星");
        }
    }

    public StringProperty riskLevelProperty() {
        if (riskLevel == null) {
            return new SimpleStringProperty("未知");
        } else if (riskLevel == 1) {
            return new SimpleStringProperty("低风险");
        } else if (riskLevel == 2) {
            return new SimpleStringProperty("中低风险");
        } else if (riskLevel == 3) {
            return new SimpleStringProperty("中风险");
        } else if (riskLevel == 4) {
            return new SimpleStringProperty("中高风险");
        } else if (riskLevel == 5) {
            return new SimpleStringProperty("高风险");
        } else {
            return new SimpleStringProperty("未知");
        }

    }
}
