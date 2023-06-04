package com.flightDelay.flightdelayapi.arrivalDelay;

import com.flightDelay.flightdelayapi.statisticsFactors.enums.DelayCause;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ArrivalDelayDtoMapperImplTest {

    @Autowired
    private ArrivalDelayDtoMapperImpl mapper;


    @Test
    void getMapWithDelayCauses() {
        ArrivalDelay arrivalDelay = new ArrivalDelay();
        arrivalDelay.setDelayInMinutesCausedByAccident(12);
        Map<DelayCause, Integer> map = mapper.getMapWithDelayCauses(arrivalDelay);

        assertEquals(1, map.size());
        assertEquals(12, map.get(DelayCause.ACCIDENT));
    }

    @Test
    void getMapWithDelayCauses2() {
        ArrivalDelay arrivalDelay = new ArrivalDelay();
        arrivalDelay.setDelayInMinutesCausedByAccident(12);
        arrivalDelay.setDelayInMinutesCausedByDeicing(10);
        Map<DelayCause, Integer> map = mapper.getMapWithDelayCauses(arrivalDelay);

        assertEquals(2, map.size());
        assertEquals(12, map.get(DelayCause.ACCIDENT));
        assertEquals(10, map.get(DelayCause.DEICING));
    }
}