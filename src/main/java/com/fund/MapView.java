package com.fund;

import com.fund.config.AbstractFxView;
import com.fund.config.FXMLViewAndController;
import com.fund.service.BlueSsqBusinessService;
import com.fund.service.SsqService;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.html.HTMLDocument;

@Slf4j
@Component
@FXMLViewAndController(value = "template/MapView.fxml", title = "地图")
public class MapView extends AbstractFxView {

    @FXML
    private WebView webView;
    @FXML
    private TextField inputUrl;

    WebEngine webEngine;

    @Autowired
    private SsqService ssqService;
    @Autowired
    private BlueSsqBusinessService blueSsqBusinessService;

    @Override
    public void initialize() {
        webEngine = this.webView.getEngine();

        // 加载指示器
        webEngine.getLoadWorker().stateProperty().addListener((ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                // 获取Javascript连接器对象。
//                HTMLInputElement element = (HTMLInputElement) webEngine.getDocument().getElementById("kw");
//                element.setValue("1111");
//
//                HTMLInputElement su = (HTMLInputElement)webEngine.getDocument().getElementById("su");
//                su.click();

                HTMLDocument htmlDocument = (HTMLDocument) webEngine.getDocument();


                log.info("222{}", htmlDocument.getElementsByTagName("font"));
            }
        });


        inputUrl.setOnKeyPressed(this::inputUrlEvent);

    }

    protected void inputUrlEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String url = this.inputUrl.getText();
//            if (!url.startsWith("http://")) {
//                url = "http://" + url;
//            }
            webEngine.load(url);
        }
//        if (event.getCode() == KeyCode.ENTER) {
//            DefaultThreadFactory.runLater(() -> {
//                //ssqService.sync();
//                blueSsqBusinessService.learn();
//            });
//        }

    }
}
