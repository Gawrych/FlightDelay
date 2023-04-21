package com.flightDelay.flightdelayapi.region;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Override
    public void deleteAll() {
        regionRepository.deleteAll();
    }
}
