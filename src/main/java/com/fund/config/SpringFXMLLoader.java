package com.fund.config;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ResourceBundle;

@Component
public class SpringFXMLLoader {
    private final ApplicationContext context;

    @Autowired
    public SpringFXMLLoader(ApplicationContext context) {
        this.context = context;
    }

    /**
     * 加载 FXML
     *
     * @param fxmlPath
     * @return
     * @throws IOException
     */
    public Parent load(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader.load();
    }

    /**
     * Controller 没有通过Spring管理
     *
     * @param fxmlPath
     * @return
     * @throws IOException
     */
    public Parent loadNoSpring(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        return loader.load();
    }

    /**
     * 加载 FXML
     *
     * @param fxmlPath
     * @param resourceBundle
     * @return
     * @throws IOException
     */
    public Parent load(String fxmlPath, ResourceBundle resourceBundle) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setResources(resourceBundle);
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader.load();
    }

    /**
     * Controller 没有通过Spring管理
     *
     * @param fxmlPath
     * @return
     * @throws IOException
     */
    public Parent loadNoSpring(String fxmlPath, ResourceBundle resourceBundle) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlPath));
        loader.setResources(resourceBundle);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        return loader.load();
    }

    /**
     * URL location = getClass().getResource("/template/MainView.fxml");
     FXMLLoader fxmlLoader = new FXMLLoader();
     fxmlLoader.setControllerFactory(applicationContext::getBean);
     fxmlLoader.setLocation(location);
     //fxmlLoader.setController(SpringUtils.getBean(MainController.class));
     //fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
     */
}
