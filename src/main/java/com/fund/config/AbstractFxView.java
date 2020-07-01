package com.fund.config;

import com.jfoenix.assets.JFoenixResources;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * 试图和Controller结合
 */
public abstract class AbstractFxView implements Initializable {

    private final URL resource;
    private final FXMLViewAndController annotation;
    private FXMLLoader fxmlLoader;

    private Stage stage;
    private Modality currentStageModality;

    private EventHandler<WindowEvent> closeEvent;

    public AbstractFxView() {
        annotation = getFXMLAnnotation();
        resource = getURLResource(annotation);
    }

    public void setCloseEvent(EventHandler<WindowEvent> event) {
        this.closeEvent = event;
    }

    public void close() {
        if (this.stage != null) {
            this.stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            //this.stage.close();
        }
    }

    public void showView(Window window, Modality modality) {
        if (stage == null || currentStageModality != modality || !Objects.equals(stage.getOwner(), window)) {
            stage = createStage(modality);
            stage.initOwner(window);
        }
        stage.show();
    }

    public void showView(Modality modality) {
        if (stage == null || currentStageModality != modality) {
            stage = createStage(modality);
        }
        stage.show();
    }

    public void showViewAndWait(Window window, Modality modality) {
        if (stage == null || currentStageModality != modality || !Objects.equals(stage.getOwner(), window)) {
            stage = createStage(modality);
            stage.initOwner(window);
        }
        stage.showAndWait();
    }

    public void showViewAndWait(Modality modality) {
        if (stage == null || currentStageModality != modality) {
            stage = createStage(modality);
        }
        stage.showAndWait();
    }

    /**
     * 创建 窗口
     *
     * @param modality
     * @return
     */
    private Stage createStage(Modality modality) {
        currentStageModality = modality;
        Stage stage = new Stage();

        stage.initModality(modality);
        stage.initStyle(getDefaultStyle());
        stage.setTitle(getDefaultTitle());

        JFXDecorator decorator = new JFXDecorator(stage, getView(), false, true, true);
        decorator.setCustomMaximize(true);

        double[] screenSize = getScreenSizeByScale(0.6, 0.64);
        Scene scene = new Scene(decorator, screenSize[0], screenSize[1]);
        scene.getStylesheets().addAll(JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm(),
                JFoenixResources.load("css/jfoenix-design.css").toExternalForm(),
                getClass().getResource("/css/jfoenix-main-demo.css").toExternalForm());

        stage.setScene(scene);
        stage.setOnCloseRequest(this::closeRequest);
        return stage;
    }

    /**
     * 创建 场景
     *
     * @return
     */
    public Parent getView() {

        ensureFxmlLoaderInitialized();

        final Parent parent = fxmlLoader.getRoot();
        return parent;
    }

    /**
     * init Fxml
     */
    private void ensureFxmlLoaderInitialized() {
        if (fxmlLoader != null) {
            return;
        }
        fxmlLoader = loadSynchronously(resource);
    }

    private FXMLLoader loadSynchronously(final URL resource) throws IllegalStateException {

        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(resource);
        loader.setController(this);
        try {
            loader.load();
        } catch (final IOException | IllegalStateException e) {
            throw new IllegalStateException("Cannot load " + getClass().getSimpleName().toLowerCase(), e);
        }

        return loader;
    }

    protected String getDefaultTitle() {
        return annotation.title();
    }

    protected StageStyle getDefaultStyle() {
        final String style = annotation.stageStyle();
        return StageStyle.valueOf(style.toUpperCase());
    }

    private URL getURLResource(final FXMLViewAndController annotation) {
        if (annotation != null && !annotation.value().equals("")) {
            return getClass().getResource(annotation.value());
        } else {
            throw new NullPointerException("value null");
        }
    }

    private FXMLViewAndController getFXMLAnnotation() {
        final Class<? extends AbstractFxView> theClass = this.getClass();
        final FXMLViewAndController annotation = theClass.getAnnotation(FXMLViewAndController.class);
        return annotation;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //初始化
        initialize();
    }

    /**
     * controller 初始化
     */
    public abstract void initialize();

    private void closeRequest(WindowEvent event) {
        if (closeEvent != null) {
            closeEvent.handle(event);
        }
    }

    static double[] getScreenSizeByScale(double width, double height) {
        double screenWidth = 800;
        double screenHeight = 600;
        try {
            Rectangle2D bounds = Screen.getScreens().get(0).getBounds();
            screenWidth = bounds.getWidth() * width;
            screenHeight = bounds.getHeight() * height;
        } catch (Exception e) {
        }

        return new double[]{screenWidth, screenHeight};
    }
}
