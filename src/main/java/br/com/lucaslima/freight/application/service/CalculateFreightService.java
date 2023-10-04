package br.com.lucaslima.freight.application.service;

import br.com.lucaslima.freight.application.domain.Configuration;
import br.com.lucaslima.freight.application.domain.Freight;
import br.com.lucaslima.freight.application.ports.in.CalculateFreightUseCase;
import br.com.lucaslima.freight.application.ports.in.GetConfigurationUseCase;
import br.com.lucaslima.freight.application.ports.out.CalculateFreightPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CalculateFreightService implements CalculateFreightUseCase {

    private final List<CalculateFreightPort> partnersPorts;

    private final GetConfigurationUseCase getConfigurationUseCase;
    private final Executor executor;

    public CalculateFreightService(List<CalculateFreightPort> partnersPorts, GetConfigurationUseCase getConfigurationUseCase) {
        this.partnersPorts = partnersPorts;
        this.getConfigurationUseCase = getConfigurationUseCase;
        this.executor = Executors.newFixedThreadPool(this.partnersPorts.size());
    }

    @Override
    public List<Freight> caculate(Freight freight, int secondsToWait) {

        var configuration = this.getConfigurationUseCase.get("");

        var freights = this.partnersPorts
                .stream()
                .map(port -> calculateFreightInPartners(port, configuration, freight, secondsToWait))
                .toArray(CompletableFuture[]::new);


        return null;
    }

    public CompletableFuture<Freight> calculateFreightInPartners(CalculateFreightPort port, Configuration configuration, Freight freight, int timeoutInSeconds) {
        CompletableFuture<Freight> freightCompletableFuture = CompletableFuture.supplyAsync(
                () -> port.calculate(configuration, freight),
                executor
        );

        return freightCompletableFuture
                .orTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                .exceptionally(ex -> null);
    }


}
