package com.fei.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fei.domain.Region;
import com.fei.repository.RegionRepository;
import com.fei.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Region.
 */
@RestController
@RequestMapping("/api")
public class RegionResource {

    private final Logger log = LoggerFactory.getLogger(RegionResource.class);

    @Inject
    private RegionRepository regionRepository;

    /**
     * POST  /regions -> Create a new region.
     */
    @RequestMapping(value = "/regions",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Region> createRegion(@RequestBody Region region) throws URISyntaxException {
        log.debug("REST request to save Region : {}", region);
        if (region.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new region cannot already have an ID").body(null);
        }
        Region result = regionRepository.save(region);
        return ResponseEntity.created(new URI("/api/regions/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("region", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /regions -> Updates an existing region.
     */
    @RequestMapping(value = "/regions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Region> updateRegion(@RequestBody Region region) throws URISyntaxException {
        log.debug("REST request to update Region : {}", region);
        if (region.getId() == null) {
            return createRegion(region);
        }
        Region result = regionRepository.save(region);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("region", region.getId().toString()))
                .body(result);
    }

    /**
     * GET  /regions -> get all the regions.
     */
    @RequestMapping(value = "/regions",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Region> getAllRegions() {
        log.debug("REST request to get all Regions");
        return regionRepository.findAll();
    }

    /**
     * GET  /regions/:id -> get the "id" region.
     */
    @RequestMapping(value = "/regions/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Region> getRegion(@PathVariable Long id) {
        log.debug("REST request to get Region : {}", id);
        return Optional.ofNullable(regionRepository.findOne(id))
            .map(region -> new ResponseEntity<>(
                region,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /regions/:id -> delete the "id" region.
     */
    @RequestMapping(value = "/regions/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        log.debug("REST request to delete Region : {}", id);
        regionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("region", id.toString())).build();
    }
}
