package com.lambda.investing.algorithmic_trading.provider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InstrumentsProvider {

    protected final ApplicationContext ac;

    public InstrumentsProvider() {
        ac = new ClassPathXmlApplicationContext(new String[] { "classpath:instrument_beans.xml" });
    }


}
