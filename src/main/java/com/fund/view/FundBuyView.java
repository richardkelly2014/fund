package com.fund.view;

import com.fund.config.AbstractFxView;
import com.fund.config.FXMLViewAndController;
import com.fund.model.FundBaseModel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jiangfei on 2020/7/4.
 */
@Slf4j
@FXMLViewAndController(value = "/template/FundBuyView.fxml", title = "基金购买")
public class FundBuyView extends AbstractFxView {

    private FundBaseModel model;

    public FundBuyView(FundBaseModel model) {
        this.model = model;
    }


    @Override
    public void initialize() {

    }
}
