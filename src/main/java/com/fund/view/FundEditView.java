package com.fund.view;

import com.fund.config.AbstractFxView;
import com.fund.config.FXMLViewAndController;
import com.fund.model.FundBaseModel;
import com.fund.service.FundBaseService;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by jiangfei on 2020/6/28.
 */
@Slf4j
@FXMLViewAndController(value = "/template/FundEditView.fxml", title = "基金操作")
public class FundEditView extends AbstractFxView {

    @FXML
    private StackPane root;

    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField code;
    @FXML
    private JFXComboBox<CMModel> type;
    @FXML
    private JFXComboBox<CMModel> riskLevel;
    @FXML
    private JFXComboBox<CMModel> rateLevel;
    @FXML
    private JFXButton btnAdd;

    private FundBaseModel editModel;
    @Autowired
    private FundBaseService fundBaseService;

    public FundEditView() {
    }

    public FundEditView(FundBaseModel model) {
        this.editModel = model;
    }

    @Override
    public void initialize() {
        initCM();
        if (editModel != null) {
            this.name.setText(editModel.getName());
            this.code.setText(editModel.getCode());
            this.code.setDisable(true);

            this.type.getSelectionModel().select(editModel.getType() - 1);
            this.riskLevel.getSelectionModel().select(editModel.getRiskLevel() - 1);
            this.rateLevel.getSelectionModel().select(editModel.getRateLevel() - 1);
        }
        this.btnAdd.setOnAction(this::btnAddAction);
    }

    private void initCM() {
        type.setItems(FXCollections.observableArrayList(
                CMModel.builder().id(1).name("指数").build(),
                CMModel.builder().id(2).name("混合").build()
        ));

        riskLevel.setItems(FXCollections.observableArrayList(
                CMModel.builder().id(1).name("低风险").build(),
                CMModel.builder().id(2).name("中低风险").build(),
                CMModel.builder().id(3).name("中风险").build(),
                CMModel.builder().id(4).name("中高风险").build(),
                CMModel.builder().id(5).name("高风险").build()
        ));

        rateLevel.setItems(FXCollections.observableArrayList(
                CMModel.builder().id(1).name("1星").build(),
                CMModel.builder().id(2).name("2星").build(),
                CMModel.builder().id(3).name("3星").build(),
                CMModel.builder().id(4).name("4星").build(),
                CMModel.builder().id(5).name("5星").build()
        ));
    }

    protected void btnAddAction(ActionEvent actionEvent) {
        if (editModel == null) {
            editModel = new FundBaseModel();
        }
        String name = this.name.getText();
        String code = this.code.getText();
        int type = this.type.getValue() != null ? this.type.getValue().getId() : 0;
        int riskLevel = this.riskLevel.getValue() != null ? this.riskLevel.getValue().getId() : 0;
        int rateLevel = this.rateLevel.getValue() != null ? this.rateLevel.getValue().getId() : 0;

        editModel.setName(name);
        editModel.setCode(code);
        editModel.setType(type);
        editModel.setRiskLevel(riskLevel);
        editModel.setRateLevel(rateLevel);
        fundBaseService.createFundBase(editModel);

        this.close();
    }

    public FundBaseModel result() {
        return this.editModel;
    }

    @Data
    @Builder
    private static class CMModel {
        private int id;
        private String name;

        @Override
        public String toString() {
            return this.name;
        }
    }
}
