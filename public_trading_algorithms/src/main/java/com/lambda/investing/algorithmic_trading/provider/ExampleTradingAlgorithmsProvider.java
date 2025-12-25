package com.lambda.investing.algorithmic_trading.provider;

import com.lambda.investing.algorithmic_trading.Algorithm;
import com.lambda.investing.algorithmic_trading.AlgorithmConnectorConfiguration;
import com.lambda.investing.algorithmic_trading.ExampleAlgo;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class ExampleTradingAlgorithmsProvider implements AlgorithmProvider {

    private enum AlgorithmType {
        EXAMPLE_ALGO("ExampleAlgo");

        private final String prefix;

        AlgorithmType(String prefix) {
            this.prefix = prefix;
        }

        private static AlgorithmType fromString(String algorithmName) {
            for (AlgorithmType type : values()) {
                if (algorithmName.startsWith(type.prefix)) {
                    return type;
                }
            }
            return null;
        }

        public Algorithm createAlgorithm(AlgorithmConnectorConfiguration config, String algorithmName, Map<String, Object> parameters) {
            System.out.println(prefix + " backtest " + algorithmName);
            switch (this) {
                case EXAMPLE_ALGO:
                    return new ExampleAlgo(config, algorithmName, parameters);
                default:
                    throw new IllegalArgumentException("Unexpected value: " + this);
            }

        }
    }



    @PostConstruct
    public void init() {
        AlgorithmCreationUtils.getInstance().addProvider(this);
        System.out.println("ExampleTradingAlgorithmsProvider loaded");
    }

    @Override
    public Algorithm createAlgorithm(AlgorithmConnectorConfiguration config, String algorithmName, Map<String, Object> parameters) {
        AlgorithmType type = AlgorithmType.fromString(algorithmName);
        if (type != null && supports(algorithmName)) {
            return type.createAlgorithm(config, algorithmName, parameters);
        }
        return null;
    }

    @Override
    public boolean supports(String algorithmName) {
        AlgorithmType type = AlgorithmType.fromString(algorithmName);
        return type != null;
    }


}