package com.flightDelay.flightdelayapi.region;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.shared.exception.resource.RegionNotFoundException;
import com.flightDelay.flightdelayapi.shared.mapper.EntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    private final EntityMapper entityMapper;

    private final ObjectMapper objectMapper;

    @Override
    public boolean existsByIsoCode(String isoCode) {
        return regionRepository.existsByIsoCode(isoCode);
    }

    @Override
    public Region findByIsoCode(String isoCode) {
        return regionRepository.findByIsoCode(isoCode).orElseThrow(() -> new RegionNotFoundException(isoCode));
    }

    @Override
    @Transactional
    public List<Region> updateFromJson(String newDataInJson) {
        return entityMapper
                .jsonArrayToList(newDataInJson, Region.class, objectMapper)
                .stream()
                .filter(this::save).toList();
    }

    @Override
    public boolean save(Region region) {
        String regionIsoCode = region.getIsoCode();

        if (regionRepository.existsByIsoCode(regionIsoCode)) {
            log.info("Region with iso code: {} already exists", regionIsoCode);

            return false;
        }

        regionRepository.save(region);

        log.info("New Region with iso code: {} has been created", regionIsoCode);

        return true;
    }
}
