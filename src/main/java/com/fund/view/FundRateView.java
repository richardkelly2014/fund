package com.fund.view;

import com.fund.client.FundClient;
import com.fund.client.model.EastFundModel;
import com.fund.config.AbstractFxView;
import com.fund.config.FXMLViewAndController;
import com.fund.model.FundBaseModel;
import com.fund.model.FundDayRateModel;
import com.fund.service.FundDayRateService;
import com.fund.util.DefaultThreadFactory;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.paint.Paint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Function;

/**
 * Created by jiangfei on 2020/6/30.
 */
@Slf4j
@FXMLViewAndController(value = "/template/FundRateView.fxml")
public class FundRateView extends AbstractFxView {

    @FXML
    private JFXTreeTableView<FundDayRateModel> fundRateTreeTable;
    @FXML
    private JFXTreeTableColumn<FundDayRateModel, String> fundTreeTableColumnDay;
    @FXML
    private JFXTreeTableColumn<FundDayRateModel, String> fundTreeTableColumnUnit;
    @FXML
    private JFXTreeTableColumn<FundDayRateModel, String> fundTreeTableColumnGrand;
    @FXML
    private JFXTreeTableColumn<FundDayRateModel, String> fundTreeTableColumnRate;
    @FXML
    private JFXButton btnSearch;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXSpinner spinnerInfo;

    @Autowired
    private FundDayRateService fundDayRateService;
    @Autowired
    private FundClient fundClient;

    private FundBaseModel model;
    //数据
    private ObservableList<FundDayRateModel> dummyData = FXCollections.observableArrayList();

    public FundRateView(FundBaseModel model) {
        this.model = model;
    }

    @Override
    public void initialize() {
        setupCellValueFactory(fundTreeTableColumnDay, FundDayRateModel::dayProperty);
        setupCellValueFactory(fundTreeTableColumnUnit, FundDayRateModel::unitProperty);
        setupCellValueFactory(fundTreeTableColumnGrand, FundDayRateModel::grandProperty);
        setupCellValueFactory(fundTreeTableColumnRate, FundDayRateModel::rateProperty);

        fundTreeTableColumnRate.setCellFactory((TreeTableColumn<FundDayRateModel, String> param) -> {
            JFXTreeTableCell<FundDayRateModel, String> cell = new JFXTreeTableCell<FundDayRateModel, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Label label = new Label(item);
                        if (item.startsWith("+")) {
                            label.setTextFill(Paint.valueOf("#FF2042"));
                        } else {
                            label.setTextFill(Paint.valueOf("#15FF95"));
                        }
                        setGraphic(label);
                    }
                }
            };
            return cell;
        });

        this.fundRateTreeTable.setRoot(new RecursiveTreeItem<>(dummyData, RecursiveTreeObject::getChildren));
        this.fundRateTreeTable.setShowRoot(false);

        this.btnSearch.setOnAction(this::btnSearchAction);
        this.btnAdd.setOnAction(this::btnAddSyncAction);

        btnSearchAction(null);
    }

    /**
     * 搜索基金每日涨幅数据
     *
     * @param event
     */
    protected void btnSearchAction(ActionEvent event) {
        this.spinnerInfo.setVisible(true);
        this.fundRateTreeTable.setDisable(true);
        //清除数据
        this.fundRateTreeTable.getRoot().getChildren().clear();
        this.dummyData.clear();

        DefaultThreadFactory.runLater(() -> {
            List<FundDayRateModel> data = fundDayRateService.queryByBaseId(this.model.getId());
            dummyData.addAll(data);
            this.spinnerInfo.setVisible(false);
            this.fundRateTreeTable.setDisable(false);
        });
    }

    protected void btnAddSyncAction(ActionEvent event) {
        this.spinnerInfo.setVisible(true);
        DefaultThreadFactory.runLater(() -> {

            int baseId = this.model.getId();
            String code = this.model.getCode();

            FundDayRateModel dayRateModel = fundDayRateService.queryLastFundDayRate(baseId);
            List<EastFundModel> eastFundModels;
            if (dayRateModel != null) {
                eastFundModels = fundClient.findFundHistory(code, dayRateModel.getDay());
            } else {
                eastFundModels = fundClient.findFundHistory(code);
            }

            if (eastFundModels != null && eastFundModels.size() > 0) {
                eastFundModels.stream().forEach(model -> {
                    int id = fundDayRateService.createFundDayRate(baseId, code, model);
                    log.info("{},{},{},{}", baseId, code, model.getDay(), id);
                });
            }
            this.spinnerInfo.setVisible(false);

            this.btnSearchAction(event);
        });

    }

    @Override
    protected String getDefaultTitle() {
        return "基金: " + this.model.getName() + " (" + this.model.getCode() + ")";
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<FundDayRateModel, T> column, Function<FundDayRateModel, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<FundDayRateModel, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }
}
