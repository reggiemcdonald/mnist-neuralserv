package org.reggiemcdonald.api;

import com.reggiemcdonald.neural.feedforward.net.Network;
import com.reggiemcdonald.neural.feedforward.res.ImageLoader;
import com.reggiemcdonald.neural.feedforward.res.NumberImage;
import org.apache.log4j.Logger;

import java.nio.file.Paths;
import java.util.List;

public class TrainingRequest implements Runnable {
    protected static final String NERL_FILE = "/Users/reginaldmcdonald/Library/Application Support/reggiemcdonald/mnist-neuralserv/network-state.nerl";
    private static final String TRAIN_IMAGES = "/Users/reginaldmcdonald/Library/Application Support/reggiemcdonald/mnist-neuralserv/train_images.gz";
    private static final String TRAIN_LABELS = "/Users/reginaldmcdonald/Library/Application Support/reggiemcdonald/mnist-neuralserv/train_labels.gz";
    private static final String TEST_IMAGES = "/Users/reginaldmcdonald/Library/Application Support/reggiemcdonald/mnist-neuralserv/test_images.gz";
    private static final String TEST_LABELS = "/Users/reginaldmcdonald/Library/Application Support/reggiemcdonald/mnist-neuralserv/test_labels.gz";

    private Logger logger = Logger.getLogger(TrainingRequest.class);

    private Network network;
    private TrainerController controller;
    private final int epochs;
    private final int batchSize;
    private final double eta;
    private final boolean verbose;
    private final boolean callback;
    private final String uri;

    public TrainingRequest(Network _network,
                           TrainerController _controller,
                           int _epochs,
                           int _batchSize,
                           double _eta,
                           boolean _verbose,
                           boolean _callback,
                           String _uri) {
        network = _network;
        controller = _controller;
        epochs = _epochs;
        batchSize = _batchSize;
        eta = _eta;
        verbose = _verbose;
        callback = _callback;
        uri = _uri;
    }

    @Override
    public void run() {
        logger.info("Began running training protocol");
        List<NumberImage> trainingList = ImageLoader.load(Paths.get(TRAIN_IMAGES), Paths.get(TRAIN_LABELS));
        List<NumberImage> testingList = ImageLoader.load(Paths.get(TEST_IMAGES), Paths.get(TEST_LABELS));
        network.learn(trainingList,epochs, batchSize, eta, verbose);
        network.test(testingList);
        Network.save(network, NERL_FILE);
        logger.info("Training protocol has finished executing");
        if (callback)
            controller.onDoneRequest(uri);
        else
            controller.onDone();
    }
}
