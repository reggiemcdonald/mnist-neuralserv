package org.reggiemcdonald.api.service;

import com.reggiemcdonald.neural.feedforward.net.Network;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class NeuralNetService {

    protected static final String NERL_FILE = "/Users/reginaldmcdonald/Library/Application Support/reggiemcdonald/mnist-neuralserv/network-state.nerl";

    Network network;

    @PostConstruct
    public void init() {
        try {
            network = Network.loadWithException(NERL_FILE);
        } catch (IOException e) {
            network = new Network(new int [] {728, 100, 10});
        }
    }

    @Async
    public CompletableFuture<Integer> classify(double[][] imageWeights) {
        double[] output = network
                .input(imageWeights)
                .propagate()
                .output();
        int classification = network.result(output);
        return CompletableFuture.completedFuture(classification);
    }
}
