package org.reggiemcdonald.api.controller;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.reggiemcdonald.api.model.api.NumberImageApiModel;
import org.reggiemcdonald.api.model.request.NumberImagePutRequestModel;
import org.reggiemcdonald.api.model.request.NumberImageRequestModel;
import org.reggiemcdonald.exception.NumberImageNotFoundException;
import org.reggiemcdonald.persistence.entity.NumberImageEntity;
import org.reggiemcdonald.persistence.repo.NumberImageRepository;
import org.reggiemcdonald.service.NeuralNetService;
import org.reggiemcdonald.service.NeuralNetServiceTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NumberImageControllerTest {

    @Mock
    private NumberImageRepository repository;

    @Mock
    private NeuralNetService service;

    @InjectMocks
    private NumberImageController controller;

    private long id = 100;
    private int label = 3;

    @Before
    public void runBefore() {
        MockitoAnnotations.initMocks(this);
    }

    private NumberImageEntity newEntity() {
        Random r = new Random();
        NumberImageEntity entity =
                spy(new NumberImageEntity(1L, label, label, new double[28][28]));
        when(entity.getId())
                .thenReturn(id);
        return entity;
    }

    private NumberImageRequestModel newPostRequestModel() {
        NumberImageRequestModel model = spy(new NumberImageRequestModel());
        when(model.getImage()).thenReturn(new double[28][28]);
        when(model.getExpectedLabel()).thenReturn(1);
        return model;
    }

    private NumberImagePutRequestModel newPutRequestModel(long id) {
        NumberImagePutRequestModel model = spy(new NumberImagePutRequestModel());
        when(model.getImage()).thenReturn(new double[28][28]);
        when(model.getExpectedLabel()).thenReturn(1);
        when(model.getId()).thenReturn(id);
        return model;
    }

    @Test
    public void testGetNumberImages_SomeFound() {
        List<NumberImageEntity> entities =
                Lists.newArrayList(newEntity(), newEntity(), newEntity());
        when(repository.findAllBySessionId(1L))
                .thenReturn(entities);
        ResponseEntity<List<NumberImageApiModel>> response =
                controller.getNumberImages(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<NumberImageApiModel> models = response.getBody();
        assertThat(models).hasSize(3);
        List<NumberImageApiModel> expected = entities
                .stream()
                .map(NumberImageApiModel::new)
                .collect(Collectors.toList());
        assertThat(models).isEqualTo(expected);
    }

    @Test
    public void testGetNumberImages_NoneFound() {
        when(repository.findAllBySessionId(anyLong()))
                .thenReturn(new ArrayList<>());
        ResponseEntity<List<NumberImageApiModel>> response =
                controller.getNumberImages(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(0);
    }

    @Test
    public void testGetNumberImage_Found() throws Exception {
        NumberImageEntity entity = newEntity();
        when(repository.findById(1L))
                .thenReturn(Optional.of(entity));
        ResponseEntity<NumberImageApiModel> response =
                controller.getNumberImage(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isEqualTo(new NumberImageApiModel(entity));
    }

    @Test(expected = NumberImageNotFoundException.class)
    public void testGetNumberImage_NotFound() throws Exception {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.empty());
        controller.getNumberImage(1L);
    }

    @Test
    public void testPostNumberImage() throws Exception {
        NumberImageRequestModel model = newPostRequestModel();
        when(service.classify(any(double[][].class)))
                .thenReturn(CompletableFuture.completedFuture(1));
        when(repository.save(any(NumberImageEntity.class)))
                .then((Answer<NumberImageEntity>) invocationOnMock -> {
                    NumberImageEntity entity =
                            invocationOnMock.getArgument(0);
                    entity = spy(entity);
                    when(entity.getId()).thenReturn(1L);
                    return entity;
                });
        controller.postNumberImage(model);
        ArgumentCaptor<NumberImageEntity> captor =
                ArgumentCaptor.forClass(NumberImageEntity.class);
        verify(service).classify(model.getImage());
        verify(repository).save(captor.capture());
        NumberImageEntity entity = captor.getValue();
        assertThat(entity.getSessionId()).isEqualTo(0L);
        assertThat(entity.getExpectedLabel())
                .isEqualTo(model.getExpectedLabel());
        assertThat(entity.getLabel()).isEqualTo(1);
        assertThat(entity.getImageWeights())
                .isSameInstanceAs(model.getImage());
    }

    @Test
    public void testPutNumberImage_Success() throws Exception {
        NumberImageEntity entity = newEntity();
        NumberImagePutRequestModel model = newPutRequestModel(entity.getId());
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        when(service.classify(any(double[][].class)))
                .thenReturn(CompletableFuture.completedFuture(9));
        assertThat(entity.getLabel()).isEqualTo(label);

        ResponseEntity<NumberImageApiModel> response =
                controller.putNumberImage(model);
        verify(repository).save(entity);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        NumberImageApiModel apiModel = response.getBody();
        assertThat(entity.getLabel()).isEqualTo(9);
        assertNotNull(apiModel.getExpectedLabel());
        assertThat(apiModel.getExpectedLabel()).isEqualTo(entity.getExpectedLabel());
        assertThat(apiModel.getLabel()).isEqualTo(9);
        assertThat(apiModel.getImageWeights()).isSameInstanceAs(model.getImage());
    }

    @Test(expected = NumberImageNotFoundException.class)
    public void testPutNumberImage_NumberImageNotFound() throws Exception {
        NumberImagePutRequestModel model = newPutRequestModel(id);
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        controller.putNumberImage(model);
    }
}
