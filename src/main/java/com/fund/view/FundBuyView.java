package com.fund.view;

import cn.hutool.core.util.NumberUtil;
import com.fund.config.AbstractFxView;
import com.fund.config.FXMLViewAndController;
import com.fund.model.FundBaseModel;
import com.fund.model.FundBuyRecordModel;
import com.fund.service.FundBuyRecordService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

/**
 * Created by jiangfei on 2020/7/4.
 */
@Slf4j
@FXMLViewAndController(value = "/template/FundBuyView.fxml", title = "基金购买")
public class FundBuyView extends AbstractFxView {

    @FXML
    private JFXTextField buyMoney;
    @FXML
    private JFXDatePicker buyDate;
    @FXML
    private JFXTextField confirmMoney;
    @FXML
    private JFXTextField confirmPortion;
    @FXML
    private JFXTextField confirmUnit;
    @FXML
    private JFXDatePicker confirmDate;

    @FXML
    private JFXButton btnAdd;

    private boolean flag = false;

    @Autowired
    private FundBuyRecordService fundBuyRecordService;

    private FundBaseModel model;

    public FundBuyView(FundBaseModel model) {
        this.model = model;
    }

    @Override
    public void initialize() {

        this.btnAdd.setOnAction(this::btnAddAction);
    }

    private void btnAddAction(ActionEvent event) {
        FundBuyRecordModel recordModel = new FundBuyRecordModel();
        recordModel.setFundName(this.model.getName());
        recordModel.setFundCode(this.model.getCode());

        String strBuyMoney = this.buyMoney.getText();
        LocalDate localBuyDate = this.buyDate.getValue();

        String strConfirmMoney = this.confirmMoney.getText();
        String strConfirmPortion = this.confirmPortion.getText();
        String strConfirmUnit = this.confirmUnit.getText();
        LocalDate localConfirmDate = this.confirmDate.getValue();


        recordModel.setBuyMoney(NumberUtil.mul(strBuyMoney, "100").intValue());
        recordModel.setBuyDateTime(localBuyDate.toString());

        recordModel.setConfirmMoney(NumberUtil.mul(strConfirmMoney, "100").intValue());
        recordModel.setConfirmPortion(NumberUtil.mul(strConfirmPortion, "100").intValue());
        recordModel.setConfirmUnit(NumberUtil.mul(strConfirmUnit, "10000").intValue());
        recordModel.setConfirmDay(localConfirmDate.toString());

        int value = fundBuyRecordService.createBuyRecord(recordModel);
        if (value > 0) {
            this.flag = true;
        }
        this.close();
    }

    public boolean getFlag() {
        return this.flag;
    }
}
