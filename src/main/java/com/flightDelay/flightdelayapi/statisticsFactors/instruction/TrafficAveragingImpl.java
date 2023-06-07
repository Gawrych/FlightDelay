package com.flightDelay.flightdelayapi.statisticsFactors.instruction;

import com.flightDelay.flightdelayapi.traffic.TrafficDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TrafficAveragingImpl implements Function<TrafficDto, Double> {

    @Override
    public Double apply(TrafficDto trafficDto) {
        return (double) trafficDto.getTotal();
    }
}
