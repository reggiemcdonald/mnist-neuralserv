package org.reggiemcdonald.util;

import com.reggiemcdonald.neural.feedforward.res.NumberImage;
import org.reggiemcdonald.api.model.api.NumberImageApiModel;
import org.reggiemcdonald.api.model.api.TrainingSessionApiModel;
import org.reggiemcdonald.persistence.entity.NumberImageEntity;
import org.reggiemcdonald.persistence.entity.TrainingSessionEntity;
import sun.awt.image.ImageWatched;

import javax.swing.text.html.parser.Entity;
import java.util.*;
import java.util.function.Predicate;

public class EntityUtils {

    private EntityUtils() {}

    public static List<NumberImage> toNumberImageList(Iterable<NumberImageEntity> entities, List<NumberImage> numberImages, Predicate<NumberImageEntity> pred) {
        for (NumberImageEntity entity : entities) {
            if (pred.test(entity)) {
                numberImages.add(new NumberImage(entity.getImageWeights(), entity.getLabel()));
            }
        }
        return numberImages;
    }
}
