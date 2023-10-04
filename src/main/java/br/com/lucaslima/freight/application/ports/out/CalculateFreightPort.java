package br.com.lucaslima.freight.application.ports.out;

import br.com.lucaslima.freight.application.domain.Configuration;
import br.com.lucaslima.freight.application.domain.Freight;

public interface CalculateFreightPort {

    Freight calculate(Configuration configuration, Freight freight);
}
