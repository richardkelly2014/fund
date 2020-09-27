package com.fund.view;

import com.fund.config.AbstractFxView;
import com.fund.config.FXMLViewAndController;
import com.fund.config.SpringUtils;
import com.fund.view.setting.NaviSettingView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Created by jiangfei on 2020/6/25.
 */
@Slf4j
//@Component
@FXMLViewAndController(value = "/template/MainView.fxml", title = "基金")
public class MainView extends AbstractFxView {

    @FXML
    private BorderPane mainPane;
    //右侧导航
    @FXML
    private StackPane titleBurgerContainer;
    @FXML
    private JFXHamburger titleBurger;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXButton btnFund;
    @FXML
    private JFXButton btnPlan;


    @Override
    public void initialize() {
        drawer.setOnDrawerOpening(e -> {
            final Transition animation = titleBurger.getAnimation();
            animation.setRate(1);
            animation.play();
        });
        drawer.setOnDrawerClosing(e -> {
            final Transition animation = titleBurger.getAnimation();
            animation.setRate(-1);
            animation.play();
        });
        titleBurgerContainer.setOnMouseClicked(e -> {
            if (drawer.isClosed() || drawer.isClosing()) {
                drawer.open();
            } else {
                drawer.close();
            }
        });

        this.btnFund.setOnAction((ActionEvent event) -> {
            drawer.setContent(SpringUtils.getBean(FundView.class).getView());
        });

        this.btnPlan.setOnAction((ActionEvent event) -> {
            drawer.setContent(SpringUtils.getBean(MyFundView.class).getView());
        });

        drawer.setContent(SpringUtils.getBean(FundView.class).getView());
    }

    /**
     * 切换
     *
     * @param clazz
     */
    public void switchContent(Class<? extends AbstractFxView> clazz) {
        drawer.setContent(SpringUtils.getBean(clazz).getView());
    }

}
