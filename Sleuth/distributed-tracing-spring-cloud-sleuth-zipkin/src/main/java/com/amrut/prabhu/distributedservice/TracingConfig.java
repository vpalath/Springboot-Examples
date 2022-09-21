package com.amrut.prabhu.distributedservice;

import brave.baggage.BaggageField;
import brave.baggage.BaggagePropagationConfig;
import brave.baggage.BaggagePropagationCustomizer;
import brave.baggage.CorrelationScopeConfig;
import brave.baggage.CorrelationScopeCustomizer;
import brave.context.slf4j.MDCScopeDecorator;
import brave.propagation.CurrentTraceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracingConfig {

    private static final String REQUEST_ID = "request-Id";

    private final BaggageField baggageField = BaggageField.create(REQUEST_ID);
//
//    @Bean
//    BaggagePropagationCustomizer baggagePropagationCustomizer() {
//        return builder -> builder.add(BaggagePropagationConfig.SingleBaggageField.
//                remote(baggageField));
//    }
//
//    @Bean
//    CorrelationScopeCustomizer correlationScopeCustomizer() {
//        return builder -> builder.add(CorrelationScopeConfig.SingleCorrelationField.create(baggageField));
//    }


    @Bean
    BaggageField requestIdField() {
        return BaggageField.create(REQUEST_ID);
    }

    @Bean
    CurrentTraceContext.ScopeDecorator mdcScopeDecorator() {
        return MDCScopeDecorator.newBuilder()
                .clear()
                .add(CorrelationScopeConfig.SingleCorrelationField.newBuilder(requestIdField())
                        .flushOnUpdate()
                        .build())
                .build();
    }

}
