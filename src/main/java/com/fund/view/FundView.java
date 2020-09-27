package com.fund.view;

import com.fund.client.FundClient;
import com.fund.client.model.EastFundModel;
import com.fund.config.AbstractFxView;
import com.fund.config.FXMLViewAndController;
import com.fund.config.SpringUtils;
import com.fund.model.FundBaseModel;
import com.fund.model.FundDayRateModel;
import com.fund.service.FundBaseService;
import com.fund.service.FundDayRateService;
import com.fund.util.DefaultThreadFactory;
import com.fund.view.setting.TestWebView;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * 基金
 * Created by jiangfei on 2020/6/27.
 */
@Slf4j
//@Component
@FXMLViewAndController(value = "/template/FundView.fxml", title = "基金")
public class FundView extends AbstractFxView {

    @FXML
    private JFXTreeTableView<FundBaseModel> fundTreeTable;
    @FXML
    private JFXTreeTableColumn<FundBaseModel, String> fundTreeTableColumnName;
    @FXML
    private JFXTreeTableColumn<FundBaseModel, String> fundTreeTableColumnCode;
    @FXML
    private JFXTreeTableColumn<FundBaseModel, String> fundTreeTableColumnType;
    @FXML
    private JFXTreeTableColumn<FundBaseModel, String> fundTreeTableColumnRisk;
    @FXML
    private JFXTreeTableColumn<FundBaseModel, String> fundTreeTableColumnLevel;
    @FXML
    private JFXTreeTableColumn<FundBaseModel, String> fundTreeTableColumnOperation;
    @FXML
    private JFXTreeTableColumn<FundBaseModel, String> fundTreeTableColumnRate;

    @FXML
    private JFXButton btnSearch;
    @FXML
    private JFXSpinner spinnerInfo;
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnTest;

    @Autowired
    private FundBaseService fundBaseService;
    @Autowired
    private FundDayRateService fundDayRateService;
    @Autowired
    private FundClient fundClient;

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    //数据
    private ObservableList<FundBaseModel> dummyData = FXCollections.observableArrayList();

    @Override
    public void initialize() {
        setupCellValueFactory(fundTreeTableColumnName, FundBaseModel::nameProperty);
        setupCellValueFactory(fundTreeTableColumnCode, FundBaseModel::codeProperty);
        setupCellValueFactory(fundTreeTableColumnType, FundBaseModel::typeProperty);
        setupCellValueFactory(fundTreeTableColumnRisk, FundBaseModel::riskLevelProperty);
        setupCellValueFactory(fundTreeTableColumnLevel, FundBaseModel::rateLevelProperty);
        setupCellValueFactory(fundTreeTableColumnRate, FundBaseModel::dayRateProperty);

        fundTreeTableColumnRisk.setCellFactory((TreeTableColumn<FundBaseModel, String> param) -> {
            JFXTreeTableCell<FundBaseModel, String> cell = new JFXTreeTableCell<FundBaseModel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Label label = new Label(item);
                        label.setTextFill(Paint.valueOf("#FFA69A"));
                        setGraphic(label);
                    }
                }
            };
            return cell;
        });

        fundTreeTableColumnOperation.setCellFactory((TreeTableColumn<FundBaseModel, String> param) -> {
            JFXTreeTableCell<FundBaseModel, String> cell = new JFXTreeTableCell<FundBaseModel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox hBox = new HBox();
                        hBox.setSpacing(10);
                        hBox.setAlignment(Pos.TOP_CENTER);
                        JFXButton jfxButton = new JFXButton("详情");
                        jfxButton.setButtonType(JFXButton.ButtonType.RAISED);
                        jfxButton.setOnAction((ActionEvent event) -> {
                            btnInfo(getTreeTableView().getTreeItem(getIndex()).getValue());
                        });

                        JFXButton buyButton = new JFXButton("购买");
                        buyButton.setButtonType(JFXButton.ButtonType.RAISED);
                        buyButton.setOnAction((ActionEvent event) -> {
                            buyAction(getTreeTableView().getTreeItem(getIndex()).getValue());
                        });

                        hBox.getChildren().addAll(jfxButton, buyButton);

                        setGraphic(hBox);
                    }
                }
            };
            return cell;
        });

        this.fundTreeTable.setRoot(new RecursiveTreeItem<>(dummyData, RecursiveTreeObject::getChildren));
        this.fundTreeTable.setShowRoot(false);

        this.btnSearch.setOnAction(this::btnSearchAction);
        this.btnAdd.setOnAction(this::btnAddAction);

        this.btnTest.setOnAction(this::btnTest);

        btnSearchAction(null);
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<FundBaseModel, T> column, Function<FundBaseModel, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<FundBaseModel, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    private void btnSearchAction(ActionEvent event) {
        this.spinnerInfo.setVisible(true);
        this.fundTreeTable.setDisable(true);
        //不知道是否是这个控件的bug。后面提bug
        this.fundTreeTable.getRoot().getChildren().clear();
        this.dummyData.clear();

        DefaultThreadFactory.runLater(() -> {
            List<FundBaseModel> funds = fundBaseService.query();
            dummyData.addAll(funds);
            this.spinnerInfo.setVisible(false);
            this.fundTreeTable.setDisable(false);
        });
    }

    private void btnAddAction(ActionEvent event) {
        FundEditView editView = new FundEditView();
        capableBeanFactory.autowireBean(editView);
        editView.showViewAndWait(Modality.WINDOW_MODAL);

        FundBaseModel baseModel = editView.result();
        if (baseModel != null) {
            asyncFundRate(baseModel);
        }
    }

    protected void btnInfo(FundBaseModel model) {
        FundRateView fundRateView = new FundRateView(model);
        capableBeanFactory.autowireBean(fundRateView);
        fundRateView.showView(Modality.WINDOW_MODAL);
    }

    protected void buyAction(FundBaseModel model) {
        FundBuyView buyView = new FundBuyView(model);
        capableBeanFactory.autowireBean(buyView);
        buyView.setCloseEvent((WindowEvent event) -> {
            if (buyView.getFlag()) {
                SpringUtils.getBean(MainView.class).switchContent(MyFundView.class);
            }
        });
        buyView.showViewAndWait(Modality.WINDOW_MODAL);
    }

    protected void btnTest(ActionEvent event) {
        TestWebView testWebView = new TestWebView();
        testWebView.showView(Modality.WINDOW_MODAL);
    }

    /**
     * 同步涨幅
     *
     * @param baseModel
     */
    private void asyncFundRate(FundBaseModel baseModel) {

        this.spinnerInfo.setVisible(true);
        this.fundTreeTable.setDisable(true);
        //不知道是否是这个控件的bug。后面提bug
        this.fundTreeTable.getRoot().getChildren().clear();
        this.dummyData.clear();

        DefaultThreadFactory.runLater(() -> {
            int baseId = baseModel.getId();
            String code = baseModel.getCode();

            List<EastFundModel> eastFundModels = fundClient.findFundHistory(code);

            if (eastFundModels != null && eastFundModels.size() > 0) {
                eastFundModels.stream().forEach(model ->
                        fundDayRateService.createFundDayRate(baseId, code, model)
                );
            }

            List<FundBaseModel> funds = fundBaseService.query();
            dummyData.addAll(funds);
            this.spinnerInfo.setVisible(false);
            this.fundTreeTable.setDisable(false);
        });
    }
}
