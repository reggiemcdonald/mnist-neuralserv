package org.reggiemcdonald.service;

import com.reggiemcdonald.neural.feedforward.net.Network;
import com.reggiemcdonald.neural.feedforward.res.ImageLoader;
import com.reggiemcdonald.neural.feedforward.res.NumberImage;
import org.reggiemcdonald.persistence.entity.TrainingSessionEntity;
import org.reggiemcdonald.persistence.repo.NumberImageRepository;
import org.reggiemcdonald.persistence.repo.TrainingSessionRepository;
import org.reggiemcdonald.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class NeuralNetService {

    protected static final String NERL_FILE = "nerl/network_state.nerl";
    private static final String TRAIN_IMAGES = "nerl/train_images.gz";
    private static final String TRAIN_LABELS = "nerl/train_labels.gz";
    private static final String TEST_IMAGES = "nerl/test_images.gz";
    private static final String TEST_LABELS = "nerl/test_labels.gz";
    private static final Logger logger = LoggerFactory.getLogger(NeuralNetService.class);

    private static final double PROPORTION_USER_TRAINING = 0.8;

    private Network network;

    private NumberImageRepository numberImageRepository;
    private TrainingSessionRepository trainingSessionRepository;

    @Autowired
    public NeuralNetService(NumberImageRepository _numberImageRepository, TrainingSessionRepository _trainingSessionRepository) {
        numberImageRepository = _numberImageRepository;
        trainingSessionRepository = _trainingSessionRepository;
    }

    @PostConstruct
    public void init() {
        try {
            logger.info(System.getProperty("user.dir"));
            network = Network.loadWithException(NERL_FILE);
            logger.info("Loaded network from file.");
        } catch (IOException e) {
            logger.warn("Loading network from file failed. Using naive network " + e.getMessage());
            network = new Network(new int [] { 784, 100, 10 });
        }
    }

    @Async
    public CompletableFuture<Integer> classify(double[][] imageWeights) {
        if (network == null)
            throw new RuntimeException();
        double[] output = network
                .input(imageWeights)
                .propagate()
                .output();
        int classification = network.result(output);
        return CompletableFuture.completedFuture(classification);
    }

    @Async
    public CompletableFuture<Boolean> train(int epochs, int batchSize, double eta, boolean verbose) throws IOException {
        logger.info("Began running training protocol");
        Network network;
        try {
            network = Network.loadWithException(NERL_FILE);
        } catch (IOException e) {
            network = new Network(new int [] { 784, 100, 10 });
            logger.error(e.getMessage());
        }
        logger.info("Loaded nerl file");
        List<NumberImage> trainingList = ImageLoader.load(Paths.get(TRAIN_IMAGES), Paths.get(TRAIN_LABELS));
        logger.info(String.format("Loaded %d images for training", trainingList.size()));
        List<NumberImage> testingList = ImageLoader.load(Paths.get(TEST_IMAGES), Paths.get(TEST_LABELS));
        logger.info(String.format("Loaded %d images for testing", testingList.size()));
        LinkedList<NumberImage> userData = new LinkedList<>();
        EntityUtils.toNumberImageList(numberImageRepository.findAll(), userData, (e) -> e.getExpectedLabel() != null);
        logger.info(String.format("Loaded %d images from database", userData.size()));

        // 80 : 20 Random Split
        Collections.shuffle(userData);
        int toAddToTrain = (int) (userData.size() * PROPORTION_USER_TRAINING);
        while (toAddToTrain --> 0)
            trainingList.add(userData.remove());

        network.learn(trainingList, epochs, batchSize, eta, verbose);
        int internalTestingSize = testingList.size();
        int externalTestingSize = userData.size();
        int correctInternal = network.test(testingList);
        int correctExternal = network.test(userData);
        Date trainingDate = new Date();
        TrainingSessionEntity trainingSessionEntity = new TrainingSessionEntity(
                internalTestingSize,
                externalTestingSize,
                correctInternal,
                correctExternal,
                trainingDate
        );
        trainingSessionRepository.save(trainingSessionEntity);
        Network.save(network, NERL_FILE);
        logger.info("Finished training session");
        return CompletableFuture.completedFuture(true);
    }
}
