package com.flightDelay.flightdelayapi.arrivalDelay;

import java.util.List;

public interface ArrivalDelayDtoMapper {

    List<ArrivalDelayDto> mapFromList(List<ArrivalDelay> arrivalDelays);

    ArrivalDelayDto map(ArrivalDelay arrivalDelay);
}
