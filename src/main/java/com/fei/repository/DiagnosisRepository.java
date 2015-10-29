package com.fei.repository;

import com.fei.domain.Diagnosis;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Diagnosis entity.
 */
public interface DiagnosisRepository extends JpaRepository<Diagnosis,Long> {

}
