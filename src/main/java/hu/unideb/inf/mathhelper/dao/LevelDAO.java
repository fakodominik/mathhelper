package hu.unideb.inf.mathhelper.dao;

import hu.unideb.inf.mathhelper.model.level.Level;

import java.util.List;

public interface LevelDAO {

    List<Level> getLevelSystem(String path);

}
