package com.fund;

import com.fund.client.StockClient;
import com.fund.config.AbstractFxView;
import com.fund.config.FXMLViewAndController;
import com.fund.dal.StockDailyMapper;
import com.fund.model.StockBasicModel;
import com.fund.model.StockDailyModel;
import com.fund.service.*;
import com.fund.util.DateTimeUtil;
import com.fund.util.DefaultThreadFactory;
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

import java.util.List;


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
    @Autowired
    private StockBasicService stockBasicService;

    @Autowired
    private StockDailyBusinessService stockDailyBusinessService;
    @Autowired
    private StockDailyService stockDailyService;
    @Autowired
    private StockDailyAnalysisService stockDailyAnalysisService;

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
            //webEngine.load(url);
            List<StockBasicModel> basicModels = stockBasicService.loadAllStock();
            if (basicModels != null && basicModels.size() > 0) {
                basicModels.stream().forEach(stockBasicModel -> {
                    String tsCode = stockBasicModel.getTsCode();
                    log.info("begin sync tsCode={}", tsCode);
                    stockDailyBusinessService.syncDate(tsCode, "20200101", "20200930");
                });
            }

            //stockDailyBusinessService.syncDate("000001.SZ", "20200101", "20200930");

//            List<StockDailyModel> allDailyModels = stockDailyService.loadAllDailyData();
//            allDailyModels.stream().forEach(stockDailyModel -> {
//                Integer id = stockDailyModel.getId();
//                String tradeDate = stockDailyModel.getTradeDate();
//
//                int[] result = DateTimeUtil.getYearMonthDayWeek(tradeDate, "yyyyMMdd");
//                int year = result[0];
//                int month = result[1];
//                int day = result[2];
//                int week = result[3];
//                int flag = stockDailyService.changeDate(id, year, month, day, week);
//                log.info("{},{},{},{},{}={}", id, year, month, day, week, flag);
//
//            });
            //log.info("{}", stockBasicService.loadAllStock());

            //stockDailyBusinessService.analysis("002625.SZ", "20200101", "20200930");

            //log.info("{}",stockDailyService.loadDailyLastByTradeDate("000001.SZ","20200914"));

            //stockDailyBusinessService.syncDate(url, "20200101", "20200930");

        }
//        if (event.getCode() == KeyCode.ENTER) {
//            DefaultThreadFactory.runLater(() -> {
//                //ssqService.sync();
//                blueSsqBusinessService.learn();
//            });
//        }

    }
}
