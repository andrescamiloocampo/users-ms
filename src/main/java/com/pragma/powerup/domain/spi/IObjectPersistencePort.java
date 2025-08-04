package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.ObjectModel;
import java.util.List;

public interface IObjectPersistencePort {
    void saveObject(ObjectModel objectModel);

    List<ObjectModel> getAllObjects();
}