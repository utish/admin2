package com.fei.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fei.domain.Diagnosis;
import com.fei.repository.DiagnosisRepository;
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
 * REST controller for managing Diagnosis.
 */
@RestController
@RequestMapping("/api")
public class DiagnosisResource {

    private final Logger log = LoggerFactory.getLogger(DiagnosisResource.class);

    @Inject
    private DiagnosisRepository diagnosisRepository;

    /**
     * POST  /diagnosiss -> Create a new diagnosis.
     */
    @RequestMapping(value = "/diagnosiss",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Diagnosis> createDiagnosis(@RequestBody Diagnosis diagnosis) throws URISyntaxException {
        log.debug("REST request to save Diagnosis : {}", diagnosis);
        if (diagnosis.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new diagnosis cannot already have an ID").body(null);
        }
        Diagnosis result = diagnosisRepository.save(diagnosis);
        return ResponseEntity.created(new URI("/api/diagnosiss/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("diagnosis", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /diagnosiss -> Updates an existing diagnosis.
     */
    @RequestMapping(value = "/diagnosiss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Diagnosis> updateDiagnosis(@RequestBody Diagnosis diagnosis) throws URISyntaxException {
        log.debug("REST request to update Diagnosis : {}", diagnosis);
        if (diagnosis.getId() == null) {
            return createDiagnosis(diagnosis);
        }
        Diagnosis result = diagnosisRepository.save(diagnosis);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("diagnosis", diagnosis.getId().toString()))
                .body(result);
    }

    /**
     * GET  /diagnosiss -> get all the diagnosiss.
     */
    @RequestMapping(value = "/diagnosiss",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Diagnosis> getAllDiagnosiss() {
        log.debug("REST request to get all Diagnosiss");
        return diagnosisRepository.findAll();
    }

    /**
     * GET  /diagnosiss/:id -> get the "id" diagnosis.
     */
    @RequestMapping(value = "/diagnosiss/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Diagnosis> getDiagnosis(@PathVariable Long id) {
        log.debug("REST request to get Diagnosis : {}", id);
        return Optional.ofNullable(diagnosisRepository.findOne(id))
            .map(diagnosis -> new ResponseEntity<>(
                diagnosis,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /diagnosiss/:id -> delete the "id" diagnosis.
     */
    @RequestMapping(value = "/diagnosiss/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDiagnosis(@PathVariable Long id) {
        log.debug("REST request to delete Diagnosis : {}", id);
        diagnosisRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("diagnosis", id.toString())).build();
    }
}
