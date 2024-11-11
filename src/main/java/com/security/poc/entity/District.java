package com.security.poc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "districts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class District implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "cities_id", referencedColumnName = "id", nullable = false)
    private City city;
}
