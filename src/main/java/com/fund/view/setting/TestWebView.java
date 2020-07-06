package com.fund.view.setting;

import com.alibaba.druid.support.json.JSONUtils;
import com.fund.config.AbstractFxView;
import com.fund.config.FXMLViewAndController;
import com.fund.util.DefaultThreadFactory;
import com.google.common.collect.Lists;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.concurrent.Worker.State;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@FXMLViewAndController(value = "/template/setting/TestWebView.fxml", title = "浏览器")
public class TestWebView extends AbstractFxView {

    @FXML
    private WebView webView;

    /**
     * 用于与Javascript引擎通信。
     */
    private JSObject javascriptConnector;

    @Override
    public void initialize() {
        WebEngine webEngine = this.webView.getEngine();
        webEngine.setJavaScriptEnabled(true);

        // 加载指示器
        webEngine.getLoadWorker().stateProperty().addListener((ObservableValue<? extends State> observable, State oldValue, State newValue) -> {
            if (newValue == State.SUCCEEDED) {
                // 获取Javascript连接器对象。
                javascriptConnector = (JSObject) webEngine.executeScript("getJsConnector()");
            }
        });

        webEngine.load(getClass().getResource("/html/line.html").toExternalForm());

        DefaultThreadFactory.runLater(() -> {
            while (javascriptConnector == null) {
                DefaultThreadFactory.sleep();
            }
            if (javascriptConnector != null) {
                List<String> name = Lists.newArrayList("11", "22", "333");
                List<Integer> value = Lists.newArrayList(1, 2, 3);
                Map<String, Object> map = new ConcurrentHashMap<>();
                map.put("d", name);
                map.put("c", value);
                Platform.runLater(() -> {
                    log.info("{}", javascriptConnector.call("showResult", JSONUtils.toJSONString(map)));
                });
            }
        });
    }
}
