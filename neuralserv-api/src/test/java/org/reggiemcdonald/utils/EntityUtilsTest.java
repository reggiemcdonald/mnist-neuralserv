package org.reggiemcdonald.utils;

import com.reggiemcdonald.neural.feedforward.res.NumberImage;
import org.junit.Test;
import org.reggiemcdonald.persistence.entity.NumberImageEntity;
import org.reggiemcdonald.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;

public class EntityUtilsTest {

    @Test
    public void testToNumberImageList_allPred() {
        ArrayList<NumberImageEntity> entities = new ArrayList<>();
        int label1 = 1;
        int label2 = 2;
        entities.add(new NumberImageEntity(label1));
        entities.add(new NumberImageEntity(label2));
        List<NumberImage> numberImages =
                EntityUtils.toNumberImageList(entities, new ArrayList<>(), (a) -> true);
        assertThat(numberImages).hasSize(2);
        assertEquals(label1, numberImages.get(0).label);
        assertEquals(label2, numberImages.get(1).label);
    }

    @Test
    public void testToNumberImageList_filteredSome() {
        ArrayList<NumberImageEntity> entities = new ArrayList<>();
        entities.add(new NumberImageEntity(1));
        entities.add(new NumberImageEntity(2));
        entities.add(new NumberImageEntity(3));
        List<NumberImage> numberImages =
                EntityUtils.toNumberImageList(entities, new ArrayList<>(), (a) -> a.getLabel() > 1);
        assertThat(numberImages).hasSize(2);
        assertEquals(2, numberImages.get(0).label);
        assertEquals(3, numberImages.get(1).label);
    }

    @Test
    public void testToNumberImageList_filteredAll() {
        ArrayList<NumberImageEntity> entities = new ArrayList<>();
        entities.add(new NumberImageEntity(1));
        entities.add(new NumberImageEntity(2));
        entities.add(new NumberImageEntity(3));
        List<NumberImage> numberImages =
                EntityUtils.toNumberImageList(entities, new ArrayList<>(), (a) -> a.getLabel() > 3);
        assertThat(numberImages).hasSize(0);
    }

    @Test
    public void testToNumberImageList_empty() {
        List<NumberImage> numberImages =
                EntityUtils.toNumberImageList(new ArrayList<>(), new ArrayList<>(), (a) -> true);
        assertThat(numberImages).hasSize(0);
    }
}
