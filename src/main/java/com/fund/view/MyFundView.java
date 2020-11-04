package com.fund.view;

import cn.hutool.core.util.NumberUtil;
import com.fund.config.AbstractFxView;
import com.fund.config.FXMLViewAndController;
import com.fund.model.FundBuyRecordModel;
import com.fund.model.FundDayRateModel;
import com.fund.model.FundRecordProfitModel;
import com.fund.model.FundSumRecordModel;
import com.fund.service.FundBuyRecordService;
import com.fund.service.FundDayRateService;
import com.fund.service.FundRecordProfitService;
import com.fund.util.DateTimeUtil;
import com.fund.util.DefaultThreadFactory;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * Created by jiangfei on 2020/7/5.
 */
@Slf4j
@Component
@FXMLViewAndController(value = "/template/MyFundView.fxml", title = "我的")
public class MyFundView extends AbstractFxView {

    @FXML
    private JFXTreeTableView<FundBuyRecordModel> recordTable;

    @FXML
    private JFXTreeTableColumn<FundBuyRecordModel, String> fundTreeTableColumnName;
    @FXML
    private JFXTreeTableColumn<FundBuyRecordModel, String> fundTreeTableColumnCode;
    @FXML
    private JFXTreeTableColumn<FundBuyRecordModel, String> fundTreeTableColumnBuyMoney;
    @FXML
    private JFXTreeTableColumn<FundBuyRecordModel, String> fundTreeTableColumnBuyDay;

    @FXML
    private JFXTreeTableColumn<FundBuyRecordModel, String> fundTreeTableColumnConfirmMoney;
    @FXML
    private JFXTreeTableColumn<FundBuyRecordModel, String> fundTreeTableColumnConfirmPortion;
    @FXML
    private JFXTreeTableColumn<FundBuyRecordModel, String> fundTreeTableColumnConfirmUnit;
    @FXML
    private JFXTreeTableColumn<FundBuyRecordModel, String> fundTreeTableColumnConfirmDay;

    @FXML
    private JFXTreeTableColumn<FundBuyRecordModel, String> fundTreeTableColumnCurrentMoney;
    @FXML
    private JFXTreeTableColumn<FundBuyRecordModel, String> fundTreeTableColumnCurrentProfit;
    @FXML
    private JFXTreeTableColumn<FundBuyRecordModel, String> columnOperation;
    @FXML
    private Label totalMoney;
    @FXML
    private Label currentMoney;
    @FXML
    private Label currentProfit;

    @FXML
    private JFXButton btnSearch;
    @FXML
    private JFXSpinner spinnerInfo;

    @Autowired
    private FundBuyRecordService fundBuyRecordService;
    @Autowired
    private FundRecordProfitService fundRecordProfitService;
    @Autowired
    private FundDayRateService fundDayRateService;

    private ObservableList<FundBuyRecordModel> dummyData = FXCollections.observableArrayList();

    @Override
    public void initialize() {
        setupCellValueFactory(fundTreeTableColumnName, FundBuyRecordModel::fundNameProperty);
        setupCellValueFactory(fundTreeTableColumnCode, FundBuyRecordModel::fundCodeProperty);

        setupCellValueFactory(fundTreeTableColumnBuyMoney, FundBuyRecordModel::buyMoneyProperty);
        setupCellValueFactory(fundTreeTableColumnBuyDay, FundBuyRecordModel::buyDateTimeProperty);

        setupCellValueFactory(fundTreeTableColumnConfirmMoney, FundBuyRecordModel::confirmMoneyProperty);
        setupCellValueFactory(fundTreeTableColumnConfirmPortion, FundBuyRecordModel::confirmPortionProperty);
        setupCellValueFactory(fundTreeTableColumnConfirmUnit, FundBuyRecordModel::confirmUnitProperty);
        setupCellValueFactory(fundTreeTableColumnConfirmDay, FundBuyRecordModel::confirmDayProperty);

        setupCellValueFactory(fundTreeTableColumnCurrentMoney, FundBuyRecordModel::currentMoneyProperty);
        setupCellValueFactory(fundTreeTableColumnCurrentProfit, FundBuyRecordModel::currentProfitValueProperty);

        columnOperation.setCellFactory((TreeTableColumn<FundBuyRecordModel, String> param) -> {
            JFXTreeTableCell<FundBuyRecordModel, String> cell = new JFXTreeTableCell<FundBuyRecordModel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox hBox = new HBox();
                        hBox.setSpacing(10);
                        hBox.setAlignment(Pos.TOP_CENTER);

                        JFXButton jfxButton = new JFXButton("刷新收益");
                        jfxButton.setButtonType(JFXButton.ButtonType.RAISED);
                        jfxButton.setOnAction((ActionEvent event) -> {
                            refreshProfit(getTreeTableView().getTreeItem(getIndex()).getValue());
                        });

                        hBox.getChildren().addAll(jfxButton);
                        setGraphic(hBox);
                    }
                }
            };
            return cell;
        });

        this.recordTable.setRoot(new RecursiveTreeItem<>(dummyData, RecursiveTreeObject::getChildren));
        this.recordTable.setShowRoot(false);

        this.btnSearch.setOnAction(this::refreshAction);

        this.refreshAction(null);
    }

    /**
     * 刷新
     *
     * @param event
     */
    protected void refreshAction(ActionEvent event) {
        this.spinnerInfo.setVisible(true);
        this.recordTable.setDisable(true);
        //清除数据
        this.recordTable.getRoot().getChildren().clear();
        this.dummyData.clear();

        DefaultThreadFactory.runLater(() -> {
            List<FundBuyRecordModel> data = fundBuyRecordService.queryAll();
            dummyData.addAll(data);

            FundSumRecordModel sumRecordModel = fundBuyRecordService.querySumRecord();
            if (sumRecordModel != null) {
                Platform.runLater(() -> {
                    this.totalMoney.setText(String.valueOf(sumRecordModel.getTotalBuyMoney()));
                    this.currentMoney.setText(String.valueOf(sumRecordModel.getTotalCurrentMoney()));
                    this.currentProfit.setText(String.valueOf(sumRecordModel.getTotalProfit()));
                });
            }

            this.spinnerInfo.setVisible(false);
            this.recordTable.setDisable(false);
        });
    }

    /**
     * 刷新收益
     *
     * @param recordModel
     */
    protected void refreshProfit(FundBuyRecordModel recordModel) {
        this.spinnerInfo.setVisible(true);
        DefaultThreadFactory.runLater(() -> {
            int recordId = recordModel.getId();
            String code = recordModel.getFundCode();
            String day = recordModel.getConfirmDay();
            float portion = recordModel.getConfirmPortion();

            FundRecordProfitModel last = fundRecordProfitService.queryLastRecord(recordId);
            if (last != null) {
                day = DateTimeUtil.addDay(last.getProfitDay(), 1);
            }

            float currentMoney = recordModel.getCurrentMoney();
            float currentProfit = recordModel.getCurrentProfitValue();

            List<FundDayRateModel> rateModels = fundDayRateService.queryByCodeAndDay(code, day);
            for (FundDayRateModel rateModel : rateModels) {
                String date = rateModel.getDay();
                int week = rateModel.getWeek();
                int type = rateModel.getRateType();
                float rate = rateModel.getRate();

                double tRate = type == 1 ? (1 + NumberUtil.div(rate,100)) : (1 - NumberUtil.div(rate,100));
                float profitValue = (float) NumberUtil.mul(portion,
                        NumberUtil.sub(rateModel.getUnitValue().floatValue(),
                                NumberUtil.div(rateModel.getUnitValue().floatValue(), tRate)));

                //float profitValue = (float) NumberUtil.mul(NumberUtil.div(currentMoney, 100.00), rate);
                currentMoney += profitValue;
                currentProfit += profitValue;
                fundRecordProfitService.createRecordProfit(recordId, date, week, type, rate, profitValue);
            }

            fundBuyRecordService.updateCurrent(recordId, currentMoney, currentProfit);

            this.spinnerInfo.setVisible(false);
            refreshAction(null);
        });
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<FundBuyRecordModel, T> column,
                                           Function<FundBuyRecordModel, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<FundBuyRecordModel, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }
}
