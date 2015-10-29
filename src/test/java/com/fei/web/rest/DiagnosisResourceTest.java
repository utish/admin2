package com.fei.web.rest;

import com.fei.Application;
import com.fei.domain.Diagnosis;
import com.fei.repository.DiagnosisRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the DiagnosisResource REST controller.
 *
 * @see DiagnosisResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DiagnosisResourceTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private DiagnosisRepository diagnosisRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDiagnosisMockMvc;

    private Diagnosis diagnosis;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DiagnosisResource diagnosisResource = new DiagnosisResource();
        ReflectionTestUtils.setField(diagnosisResource, "diagnosisRepository", diagnosisRepository);
        this.restDiagnosisMockMvc = MockMvcBuilders.standaloneSetup(diagnosisResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        diagnosis = new Diagnosis();
        diagnosis.setCode(DEFAULT_CODE);
        diagnosis.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createDiagnosis() throws Exception {
        int databaseSizeBeforeCreate = diagnosisRepository.findAll().size();

        // Create the Diagnosis

        restDiagnosisMockMvc.perform(post("/api/diagnosiss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(diagnosis)))
                .andExpect(status().isCreated());

        // Validate the Diagnosis in the database
        List<Diagnosis> diagnosiss = diagnosisRepository.findAll();
        assertThat(diagnosiss).hasSize(databaseSizeBeforeCreate + 1);
        Diagnosis testDiagnosis = diagnosiss.get(diagnosiss.size() - 1);
        assertThat(testDiagnosis.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDiagnosis.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDiagnosiss() throws Exception {
        // Initialize the database
        diagnosisRepository.saveAndFlush(diagnosis);

        // Get all the diagnosiss
        restDiagnosisMockMvc.perform(get("/api/diagnosiss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(diagnosis.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getDiagnosis() throws Exception {
        // Initialize the database
        diagnosisRepository.saveAndFlush(diagnosis);

        // Get the diagnosis
        restDiagnosisMockMvc.perform(get("/api/diagnosiss/{id}", diagnosis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(diagnosis.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDiagnosis() throws Exception {
        // Get the diagnosis
        restDiagnosisMockMvc.perform(get("/api/diagnosiss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiagnosis() throws Exception {
        // Initialize the database
        diagnosisRepository.saveAndFlush(diagnosis);

		int databaseSizeBeforeUpdate = diagnosisRepository.findAll().size();

        // Update the diagnosis
        diagnosis.setCode(UPDATED_CODE);
        diagnosis.setDescription(UPDATED_DESCRIPTION);

        restDiagnosisMockMvc.perform(put("/api/diagnosiss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(diagnosis)))
                .andExpect(status().isOk());

        // Validate the Diagnosis in the database
        List<Diagnosis> diagnosiss = diagnosisRepository.findAll();
        assertThat(diagnosiss).hasSize(databaseSizeBeforeUpdate);
        Diagnosis testDiagnosis = diagnosiss.get(diagnosiss.size() - 1);
        assertThat(testDiagnosis.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDiagnosis.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteDiagnosis() throws Exception {
        // Initialize the database
        diagnosisRepository.saveAndFlush(diagnosis);

		int databaseSizeBeforeDelete = diagnosisRepository.findAll().size();

        // Get the diagnosis
        restDiagnosisMockMvc.perform(delete("/api/diagnosiss/{id}", diagnosis.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Diagnosis> diagnosiss = diagnosisRepository.findAll();
        assertThat(diagnosiss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
