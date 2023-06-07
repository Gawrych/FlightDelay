package com.flightDelay.flightdelayapi.statisticsFactors.instruction;

import com.flightDelay.flightdelayapi.traffic.TrafficDto;
import com.flightDelay.flightdelayapi.traffic.TrafficDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.BinaryOperator;

@Component
@RequiredArgsConstructor
public class TrafficRemappingImpl implements BinaryOperator<TrafficDto> {

    private final TrafficDtoMapper mapper;

    @Override
    public TrafficDto apply(TrafficDto oldRecord, TrafficDto newRecord) {
        int mergedDeparturesTraffic = oldRecord.getDepartures() + newRecord.getDepartures();
        int mergedArrivalsTraffic = oldRecord.getArrivals() + newRecord.getArrivals();
        int mergedTotalTraffic = oldRecord.getTotal() + newRecord.getTotal();

        return mapper.mapFrom(newRecord.getDate(), mergedDeparturesTraffic, mergedArrivalsTraffic, mergedTotalTraffic);
    }
}