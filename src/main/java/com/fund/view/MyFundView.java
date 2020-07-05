package com.fund.view;

import com.fund.config.AbstractFxView;
import com.fund.config.FXMLViewAndController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by jiangfei on 2020/7/5.
 */
@Slf4j
@Component
@FXMLViewAndController(value = "/template/MyFundView.fxml", title = "我的")
public class MyFundView extends AbstractFxView {
    @Override
    public void initialize() {

    }
}
