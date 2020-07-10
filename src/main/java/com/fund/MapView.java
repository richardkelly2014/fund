package com.fund;

import com.fund.config.AbstractFxView;
import com.fund.config.FXMLViewAndController;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FXMLViewAndController(value = "/MapView.fxml", title = "地图")
public class MapView extends AbstractFxView {

    @FXML
    private WebView webView;

    WebEngine webEngine;

    @Override
    public void initialize() {

        webEngine = this.webView.getEngine();
        webEngine.setJavaScriptEnabled(true);

        webEngine.load("http://www.baidu.com");
    }
}
