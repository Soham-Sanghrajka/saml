package com.security.poc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "countries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "iso3", length = 3)
    private String iso3;

    @Column(name = "numeric_code", length = 3)
    private String numericCode;

    @Column(name = "iso2", length = 2)
    private String iso2;

    @Column(name = "phonecode", length = 255)
    private String phonecode;

    @Column(name = "capital", length = 255)
    private String capital;

    @Column(name = "currency", length = 255)
    private String currency;

    @Column(name = "currency_name", length = 255)
    private String currencyName;

    @Column(name = "currency_symbol", length = 255)
    private String currencySymbol;

    @Column(name = "tld", length = 255)
    private String tld;

    @Column(name = "native", length = 255)
    private String nativeName;

    @Column(name = "region", length = 255)
    private String region;

    @ManyToOne
    @JoinColumn(name = "region_id",referencedColumnName = "id")
    private Region regionEntity;

    @Column(name = "subregion", length = 255)
    private String subregion;

    @ManyToOne
    @JoinColumn(name = "subregion_id",referencedColumnName = "id")
    private Subregion subregionEntity;

    @Column(name = "nationality", length = 255)
    private String nationality;

    @Lob
    @Column(name = "timezones")
    private String timezones;

    @Lob
    @Column(name = "translations")
    private String translations;

    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "emoji", length = 191)
    private String emoji;

    @Column(name = "emojiU", length = 191)
    private String emojiU;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Column(name = "flag", nullable = false)
    private boolean flag;

    @Column(name = "wikiDataId", length = 255)
    private String wikiDataId;

    @OneToMany(mappedBy = "country" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Collection<State> states;

    @OneToMany(mappedBy = "country" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Collection<City> cities;

}
