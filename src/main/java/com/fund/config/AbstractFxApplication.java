package com.fund.config;

import com.jfoenix.assets.JFoenixResources;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import com.sun.javafx.application.LauncherImpl;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import static com.fund.config.AbstractFxView.getScreenSizeByScale;
import static com.sun.javafx.application.LauncherImpl.launchApplication;

/**
 * Created by jiangfei on 2020/6/21.
 */
@Slf4j
public abstract class AbstractFxApplication extends Application {

    private static Class<? extends Application> appClass;
    private static String[] args = new String[0];
    private static Application application;
    private static Class<? extends AbstractFxView> mainView;
    protected static ConfigurableApplicationContext applicationContext;

    public static final URL BLUE_CAMPING = AbstractFxApplication.class.getResource("/images/accommodation_camping.glow.0092DA.32.png");
    public static final URL BLACK_FIRE = AbstractFxApplication.class.getResource("/images/amenity_firestation.p.000000.32.png");

    public static final URL BLACK_MAIL = AbstractFxApplication.class.getResource("/images/amenity_post_box.p.000000.32.png");
    public static final URL GREEN_MAIL = AbstractFxApplication.class.getResource("/images/amenity_post_box.p.39AC39.32.png");


    public static final URL BLACK_BUS = AbstractFxApplication.class.getResource("/images/transport_bus_station.p.000000.32.png");
    public static final URL LT_GRAY_BUS = AbstractFxApplication.class.getResource("/images/transport_bus_station.p.999999.32.png");

    public static final URL BLACK_TRAIN = AbstractFxApplication.class.getResource("/images/transport_train_station.p.000000.32.png");
    public static final URL GREEN_TRAIN = AbstractFxApplication.class.getResource("/images/transport_train_station.p.39AC39.32.png");
    public static final URL LT_GRAY_TRAIN = AbstractFxApplication.class.getResource("/images/transport_train_station.p.666666.32.png");

    private final SystemTray tray = SystemTray.getSystemTray();

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void run(final Class<? extends Application> appClass, final String[] args,
                           final Class<? extends AbstractFxView> mainView) {

        run(appClass, args, mainView, null);
    }

    public static void run(final Class<? extends Application> appClass, final String[] args,
                           final Class<? extends AbstractFxView> mainView,
                           final Class<? extends Preloader> preloaderClass) {
        AbstractFxApplication.appClass = appClass;
        AbstractFxApplication.args = args;
        AbstractFxApplication.mainView = mainView;

        if (preloaderClass != null) {
            launchApplication(appClass, preloaderClass, args);
        } else {
            launchApplication(appClass, args);
        }
    }

    public AbstractFxApplication() {

        AbstractFxApplication.application = this;
    }

    public synchronized static void notifyLoader(double progress, String detail) {

        LauncherImpl.notifyPreloader(AbstractFxApplication.application, new MessagePreloaderNotification(progress, detail));
    }

    /**
     * init
     *
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        //异步加载启动spring
        CompletableFuture.supplyAsync(() -> applicationContext = SpringApplication.run(appClass, args))
                .whenComplete((ctx, exception) -> {
                    countDownLatch.countDown();
                });
        //等待加载完毕
        countDownLatch.await();
    }

    /**
     * 启动
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        AbstractFxView mainView = SpringUtils.getBean(AbstractFxApplication.mainView);

        JFXDecorator decorator = new JFXDecorator(stage, mainView.getView(), false, true, true);
        decorator.setCustomMaximize(true);
        decorator.setGraphic(new SVGGlyph(""));

        double[] screenSize = getScreenSizeByScale(0.74, 0.8);
        Scene scene = new Scene(decorator, screenSize[0], screenSize[1]);
        scene.getStylesheets().addAll(JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm(),
                JFoenixResources.load("css/jfoenix-design.css").toExternalForm(),
                getClass().getResource("/css/jfoenix-main-demo.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle(mainView.getDefaultTitle());
        stage.setOnCloseRequest(this::applicationClose);
        stage.show();

        PopupMenu pm = new PopupMenu();

        BufferedImage image = ImageIO.read(LT_GRAY_BUS);

        MenuItem mi = new MenuItem("exit");
        //右键菜单监听
        mi.addActionListener(e -> {
            //点击右键菜单退出程序
            System.exit(0);
        });
        pm.add(mi);

        TrayIcon trayIcon = new TrayIcon(image, "自动备份工具", pm);

        trayIcon.setToolTip("自动备份工具");
        tray.add(trayIcon);

        Platform.setImplicitExit(false);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (applicationContext != null) {
            applicationContext.stop();
        }
        Platform.exit();
        System.exit(0);
    }

    protected void applicationClose(WindowEvent event) {
//        try {
//            stop();
//        } catch (Exception e) {
//        }
    }
}
