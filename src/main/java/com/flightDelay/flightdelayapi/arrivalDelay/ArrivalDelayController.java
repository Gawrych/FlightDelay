package com.flightDelay.flightdelayapi.arrivalDelay;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delays")
public class ArrivalDelayController {

    private final ArrivalDelayRepository arrivalDelayRepository;

    private final ArrivalDelayServiceImpl arrivalDelayService;

    @GetMapping(path = "/all")
    public List<ArrivalDelay> getDelaysInJson() {
        return arrivalDelayRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> addNewArrivalDelaysReport(@RequestBody String newDataInJsonString) {
        arrivalDelayService.addToDatabase(newDataInJsonString);
        return ResponseEntity.ok("Resource delay updated");
    }

    @DeleteMapping
    public String removeAllTraffic() {
        arrivalDelayRepository.deleteAll();
        return "DELETED";
    }
}
