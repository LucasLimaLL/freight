package br.com.lucaslima.freight.application.ports.in;

import br.com.lucaslima.freight.application.domain.Configuration;
import br.com.lucaslima.freight.application.domain.Freight;

import java.util.List;

public interface CalculateFreightUseCase {

    List<Freight> caculate(Freight freight, int secondsToWait);
}
