package com.fund.view.setting;

import com.fund.config.AbstractFxView;
import com.fund.config.FXMLViewAndController;
import com.fund.model.FundNaviModel;
import com.fund.service.FundNaviService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jiangfei on 2020/6/26.
 */
@Slf4j
//@Component
@FXMLViewAndController(value = "/template/setting/NaviSettingView.fxml", title = "导航设置")
public class NaviSettingView extends AbstractFxView {

    @FXML
    private MenuItem naviAdd;
    @FXML
    private TreeTableView<FundNaviModel> naviTable;
    @Autowired
    private FundNaviService fundNaviService;

    @Override
    public void initialize() {
        initTreeTableView();
    }

    private void initTreeTableView() {
        TreeTableColumn<FundNaviModel, Integer> id = new TreeTableColumn<>("编号");
        id.setCellValueFactory(new TreeItemPropertyValueFactory<FundNaviModel, Integer>("id"));

        TreeTableColumn<FundNaviModel, String> name = new TreeTableColumn<>("名称");
        name.setCellValueFactory(new TreeItemPropertyValueFactory<FundNaviModel, String>("name"));

        TreeTableColumn<FundNaviModel, String> code = new TreeTableColumn<>("编码");
        code.setCellValueFactory(new TreeItemPropertyValueFactory<FundNaviModel, String>("code"));

        TreeTableColumn<FundNaviModel, String> type = new TreeTableColumn<>("类型");
        type.setCellValueFactory(new TreeItemPropertyValueFactory<FundNaviModel, String>("type"));
        type.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FundNaviModel, String>,
                ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FundNaviModel, String> param) {
                TreeItem<FundNaviModel> treeItem = param.getValue();
                FundNaviModel model = treeItem.getValue();
                if (model.getType() == 1) {
                    return new SimpleStringProperty("菜单");
                } else {
                    return new SimpleStringProperty("页面");
                }
            }
        });

        TreeTableColumn<FundNaviModel, String> view = new TreeTableColumn<>("导航页面");
        view.setCellValueFactory(new TreeItemPropertyValueFactory<FundNaviModel, String>("view"));

        TreeTableColumn<FundNaviModel, String> show = new TreeTableColumn<>("显示");
        show.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FundNaviModel, String>,
                ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FundNaviModel, String> param) {
                TreeItem<FundNaviModel> treeItem = param.getValue();
                FundNaviModel model = treeItem.getValue();
                if (model.getShow() == 1) {
                    return new SimpleStringProperty("是");
                } else {
                    return new SimpleStringProperty("否");
                }
            }
        });
        TreeTableColumn<FundNaviModel, Void> operation = new TreeTableColumn<>("操作");
        operation.setCellFactory(new Callback<TreeTableColumn<FundNaviModel, Void>,
                TreeTableCell<FundNaviModel, Void>>() {
            @Override
            public TreeTableCell<FundNaviModel, Void> call(TreeTableColumn<FundNaviModel, Void> param) {
                final TreeTableCell<FundNaviModel, Void> cell = new TreeTableCell<FundNaviModel, Void>() {
                    private final Button btn = new Button("添加");

                    {
                        btn.setOnAction((ActionEvent event) -> {

                            log.info("{}", getTreeTableView().getTreeItem(getIndex()));
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        //log.info("{}", getTreeTableView().getTreeItem(getIndex()));
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        });

        this.naviTable.getColumns().addAll(id, name, code, type, view, show, operation);
        this.naviTable.setShowRoot(false);

        TreeItem<FundNaviModel> root = new TreeItem<>(FundNaviModel.builder().build());
        this.naviTable.setRoot(root);


        List<FundNaviModel> list = fundNaviService.selectNavi(0);
        list.stream().forEach(fundNaviModel ->
                root.getChildren().add(new TreeItem<FundNaviModel>(fundNaviModel)));


        this.naviTable.setOnMouseClicked(this::naviTableClick);
    }

    private void naviTableClick(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();

        if (node instanceof Text || (node instanceof TreeTableCell && ((TreeTableCell) node).getText() != null)) {
            TreeItem<FundNaviModel> treeItem = this.naviTable.getSelectionModel().getSelectedItem();
            treeItem.setExpanded(!treeItem.isExpanded());

            FundNaviModel model = treeItem.getValue();
            if (model.getType() == 1 && model.getParentId() == 0) {
                int id = model.getId();
                treeItem.getChildren().clear();
                List<FundNaviModel> list = fundNaviService.selectNavi(id);
                list.stream().forEach(fundNaviModel ->
                        treeItem.getChildren().add(new TreeItem<FundNaviModel>(fundNaviModel)));

            }
        }
    }
}
