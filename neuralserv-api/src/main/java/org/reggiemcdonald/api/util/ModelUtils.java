package org.reggiemcdonald.api.util;

import org.reggiemcdonald.api.model.api.NumberImageApiModel;
import org.reggiemcdonald.api.model.api.TrainingSessionApiModel;
import org.reggiemcdonald.persistence.entity.NumberImageEntity;
import org.reggiemcdonald.persistence.entity.TrainingSessionEntity;

import java.util.LinkedList;
import java.util.List;

public class ModelUtils {

    public static List<TrainingSessionApiModel> toTrainingSessionModelList(Iterable<TrainingSessionEntity> entities) {
        List<TrainingSessionApiModel> models = new LinkedList<>();
        for (TrainingSessionEntity entity : entities)
            models.add(new TrainingSessionApiModel(entity));
        return models;
    }

    public static List<NumberImageApiModel> toNumberImageModelList(Iterable<NumberImageEntity> entities) {
        List<NumberImageApiModel> models = new LinkedList<>();
        for (NumberImageEntity entity : entities)
            models.add(new NumberImageApiModel(entity));
        return models;
    }

}
