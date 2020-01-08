package org.reggiemcdonald.api.service;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ScalingService {

    @Async
    public CompletableFuture<double[][]> scale(double[][] original) {
        // perform scaling
        // TODO: Implement this
        return CompletableFuture.completedFuture(original);
    }
}
