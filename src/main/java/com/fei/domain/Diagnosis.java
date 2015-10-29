package com.fei.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Diagnosis.
 */
@Entity
@Table(name = "diagnosis")
public class Diagnosis implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Column(name = "code")
    private String code;
    
    @Column(name = "description")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Diagnosis diagnosis = (Diagnosis) o;

        if ( ! Objects.equals(id, diagnosis.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
                "id=" + id +
                ", code='" + code + "'" +
                ", description='" + description + "'" +
                '}';
    }
}
