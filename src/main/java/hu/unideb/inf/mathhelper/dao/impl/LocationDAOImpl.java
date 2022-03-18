package hu.unideb.inf.mathhelper.dao.impl;

import hu.unideb.inf.mathhelper.dao.LocationDAO;
import hu.unideb.inf.mathhelper.model.Location;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

import java.net.URL;
import java.util.Objects;

public class LocationDAOImpl implements LocationDAO {

    private static final String LOCATION_FILE_PATH = Objects.requireNonNull(LocationDAOImpl.class.
            getClassLoader().getResource("settings/locations.properties")).getPath();

    private Location location;

    @Override
    public URL getQuestionFolderPath() {
        if (location == null) {
            load();
        }
        return getURLFromPath(location.getQuestionFolder());
    }

    @Override
    public String getQuestionPictureFilePath(String fileName) {
        if (location == null) {
            load();
        }
        String folderName = fileName.substring(0,fileName.indexOf("_",5));
        return getURLFromPath(location.getPicturesFolder() + "/" + folderName + "/" + fileName).toString();
    }

    @Override
    public String getUiPictureFilePath(String fileName) {
        if (location == null) {
            load();
        }
        return getURLFromPath(location.getUiPicturesFolder() + "/" + fileName).toString();
    }

    @Override
    public URL getLevelSystemFilePath() {
        if (location == null) {
            load();
        }
        return getURLFromPath(location.getLevelsFile());
    }

    @Override
    public URL getSceneFilePath(String fileName) {
        if (location == null) {
            load();
        }
        return getURLFromPath(location.getScenesFolder() + "/" + fileName);
    }

    @Override
    public String getPaneFilePath(String fileName) {
        if (location == null) {
            load();
        }
        return getURLFromPath(location.getPanesFolder() + "/" + fileName).getPath();
    }

    @Override
    public String getTextFilePath(String fileName) {
        if (location == null) {
            load();
        }
        return location.getTextFolder() + "/" + fileName;
    }

    @Override
    public URL getDefaultSettingsFilePath() {
        if (location == null) {
            load();
        }
        return getURLFromPath(location.getDefaultSettingsFile());
    }

    @Override
    public String getSettingsFilePath() {
        if (location == null) {
            load();
        }
        return location.getSettingsFile();
    }

    @Override
    public URL getCategoryFilePath() {
        if (location == null) {
            load();
        }
        return getURLFromPath(location.getCategoryFile());
    }

    @Override
    public String getProfilePictureFilePath(String name) {
        if (location == null) {
            load();
        }
        return location.getProfilePictureFolder() + "/" + name;
    }

    @Override
    public String getProfilePictureFolderPath() {
        if (location == null) {
            load();
        }
        return location.getProfilePictureFolder() + "/";
    }

    @Override
    public String getSampleQuestionPaneFilePath() {
        if (location == null) {
            load();
        }
        return getURLFromPath(location.getSampleQuestionFile()).getPath();
    }

    @Override
    public URL getBossesSystemFilePath() {
        if (location == null) {
            load();
        }
        return getURLFromPath(location.getBossesFile());
    }

    private void load() {
        CompositeConfiguration config = new CompositeConfiguration();
        config.addConfiguration(new SystemConfiguration());
        try {
            config.addConfiguration(new PropertiesConfiguration(LOCATION_FILE_PATH));
            location = new Location.Builder()
                    .withLevelsFile(config.getString("location.level"))
                    .withPicturesFolder(config.getString("location.pictures_folder"))
                    .withQuestionFolder(config.getString("location.questions_folder"))
                    .withPanesFolder(config.getString("location.ui.panes_folder"))
                    .withScenesFolder(config.getString("location.ui.scenes_folder"))
                    .withTextFolder(config.getString("location.ui.text_folder"))
                    .withUiPicturesFolder(config.getString("location.ui.pictures_folder"))
                    .withCategoryFilePath(config.getString("location.category"))
                    .withSampleQuestionFile(config.getString("location.ui.sample_question_file"))
                    .withDefaultSettingsFile(config.getString("location.settings.default_settings"))
                    .withSettingsFile(config.getString("location.settings.settings"))
                    .withProfilePictureFolder(config.getString("location.profile_picture_folder"))
                    .withBossesFile(config.getString("location.boss"))
                    .build();
        } catch (ConfigurationException e) {
            //TODO: Handle error
            e.printStackTrace();
        }
    }

    private URL getURLFromPath(String path) {
        return Objects.requireNonNull(LocationDAOImpl.class.
                getClassLoader().getResource(path));
    }

}
