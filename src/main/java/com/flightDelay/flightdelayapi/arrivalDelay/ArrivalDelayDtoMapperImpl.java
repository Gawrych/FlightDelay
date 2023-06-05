package com.flightDelay.flightdelayapi.arrivalDelay;

import com.flightDelay.flightdelayapi.statisticsFactors.enums.DelayCause;
import org.mapstruct.Mapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public class ArrivalDelayDtoMapperImpl implements ArrivalDelayDtoMapper {

    @Override
    public List<ArrivalDelayDto> mapFromList(List<ArrivalDelay> arrivalDelays) {
        return arrivalDelays.stream().map(this::map).toList();
    }

    @Override
    public ArrivalDelayDto map(ArrivalDelay arrivalDelay) {
        return ArrivalDelayDto.builder()
                .date(arrivalDelay.getDate())
                .numberOfArrivals(arrivalDelay.getNumberOfArrivals())
                .minutesOfAirportDelay(arrivalDelay.getMinutesOfAirportDelay())
                .numberOfDelayedArrivals(arrivalDelay.getNumberOfDelayedArrivals() + arrivalDelay.getNumberOfDelayedArrivalsAbove15Minutes())
                .delays(getMapWithDelayCauses(arrivalDelay))
                .build();
    }

    public Map<DelayCause, Integer> getMapWithDelayCauses(ArrivalDelay arrivalDelay) {
        Map<DelayCause, Integer> delayCauses = Arrays.stream(DelayCause.values())
                .collect(Collectors.toMap(
                        Function.identity(),
                        cause -> cause.getCauseReference().apply(arrivalDelay)
                ));

        return filterNonZeroValues(delayCauses);
    }

    private Map<DelayCause, Integer> filterNonZeroValues(Map<DelayCause, Integer> delayCauses) {
        return delayCauses.entrySet()
                .stream()
                .filter(delayCauseIntegerEntry -> delayCauseIntegerEntry.getValue() != 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
