package org.reggiemcdonald.service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.reggiemcdonald.neural.feedforward.net.Network;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reggiemcdonald.api.controller.ScalingController;
import org.reggiemcdonald.api.model.api.ScaleApiModel;
import org.reggiemcdonald.exception.NeuralNetServiceException;
import org.reggiemcdonald.persistence.repo.NumberImageRepository;
import org.reggiemcdonald.persistence.repo.TrainingSessionRepository;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class NeuralNetServiceTest {
    @Rule
    public TemporaryFolder tmp = new TemporaryFolder();

    @Mock
    private NumberImageRepository numberImageRepository;

    @Mock
    private TrainingSessionRepository trainingSessionRepository;

    @InjectMocks
    private NeuralNetService service;

    @Before
    public void runBefore() {
        MockitoAnnotations.initMocks(this);
        service.init();
    }

    @Test(expected = NeuralNetServiceException.class)
    public void classify_nullService() throws Exception {
        service.network = null;
        service.classify(new double[28][28]);
    }

    @Test
    public void classify_success() throws Exception {
        service.train(1, 10, 3, false);
        ScalingController scaler = new ScalingController(new ScalingService());
        InputStream inputStream =
                Files.newInputStream(Paths.get("src/test/java/org/reggiemcdonald/test.png"));
        ResponseEntity<ScaleApiModel> entity =
                scaler.scaleImage(new MockMultipartFile("test", inputStream));
        ScaleApiModel model = entity.getBody();
        Integer label = service
                .classify(model.getImage())
                .get();
        assertNotNull(label);
        assertEquals((Integer) 5, label);
    }

    @Test
    public void testTrain() throws Exception {
        Logger logger = (Logger) LoggerFactory.getLogger(NeuralNetService.class);
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
        service.network = mock(Network.class);
        boolean done =
                service.train(0, 10, 3., true).get();
        assertTrue(done);
        List<ILoggingEvent> events = listAppender.list;
        assertThat(events.size()).isGreaterThan(1);
        List<String> messages = events.stream()
                .map(ILoggingEvent::getMessage).collect(Collectors.toList());
        assertThat(messages).contains("Began running training protocol");
        assertThat(messages).contains("Finished training session");
    }
}
