package org.reggiemcdonald.api;

import com.reggiemcdonald.neural.feedforward.net.Network;
import org.reggiemcdonald.api.model.TrainerControllerRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.*;

@RestController
@RequestMapping("/trainer")
public class TrainerController {

    @Autowired
    private RestTemplate restTemplate;

    private ExecutorService executorService;
    private RejectedExecutionHandler handler;

    @PostConstruct
    public void initialize() {
        handler = new RejectedTrainingRequestHandler();
        executorService =
                new ThreadPoolExecutor(1, 10, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), handler);
    }

    @RequestMapping(value = "/train", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> train(@Valid @RequestBody TrainerControllerRequestModel model) {
        Network network;
        Integer epochs = model.getEpochs();
        Integer batchSize = model.getBatchSize();
        Double eta = model.getEta();
        Boolean verbose = model.getVerbose();
        Boolean callback = model.getCallback();
        String uri = model.getUrl();

        try {
            network = Network.loadWithException(TrainingRequest.NERL_FILE);
        } catch (IOException e) {
            network = new Network(new int[] {784, 100, 10});
        }

        TrainingRequest request =
                new TrainingRequest(network, this, epochs, batchSize, eta, verbose, callback, uri);
        executorService.submit(request);
        return ResponseEntity.status(200).body("Began training network...");
    }

    public void onDone() {
        System.out.println("DONE TRAINING");
    }

    public void onDoneRequest(String uri) {
        try {
            String response = restTemplate.postForObject(uri, null, String.class, new HashMap<>());
            System.out.println(response);
        } catch (HttpClientErrorException e) {
            System.out.println(e.getMessage());
        }
    }
}
