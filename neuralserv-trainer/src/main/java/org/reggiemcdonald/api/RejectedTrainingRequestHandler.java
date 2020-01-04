package org.reggiemcdonald.api;

import org.apache.log4j.Logger;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class RejectedTrainingRequestHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        // TODO: Improve handling
        Logger.getLogger(RejectedTrainingRequestHandler.class).warn("Failed to execute training request");
    }
}
