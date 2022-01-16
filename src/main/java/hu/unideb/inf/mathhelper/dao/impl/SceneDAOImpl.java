package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.controller.Controller;
import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.dao.SceneDAO;
import hu.unideb.inf.mathhelper.exception.SceneNotFoundException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class SceneDAOImpl implements SceneDAO {

    private Controller controller;
    private final String locationOFResourceBundle;

    @Autowired
    public SceneDAOImpl(LocationDAO locationDAO) {
        locationOFResourceBundle = locationDAO.getTextFolderPath();
    }

    @Override
    public Scene loadScene(String path, String fileName) throws SceneNotFoundException{
        File file = checkFileExistence(path);
        return new Scene(load(file, fileName));
    }

    @Override
    public Controller getController() {
        return controller;
    }

    private File checkFileExistence(String path) throws SceneNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            throw new SceneNotFoundException("No scene fxml file found in the given path: " + path);
        } else {
            return file;
        }
    }


    private Parent load(File file, String fileName) {
        try {
            ResourceBundle resource = ResourceBundle.getBundle(locationOFResourceBundle + "." + fileName, new Locale("hu","HU"));
            FXMLLoader loader = new FXMLLoader(file.toURI().toURL(),resource);
            loader.load();
            Parent parent = loader.getRoot();
            controller = loader.getController();
            return parent;
        } catch (IOException e) {
            //TODO: Handle error
            e.printStackTrace();
        }
        return new GridPane();
    }
}