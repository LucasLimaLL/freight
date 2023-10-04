package br.com.lucaslima.freight.application.ports.in;

import br.com.lucaslima.freight.application.domain.Configuration;

public interface GetConfigurationUseCase {
    Configuration get(String id);
}
