package com.bohemian.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Version;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "items")
public class ItemDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "item_generator", sequenceName = "item_seq")
    @JsonIgnore
    private Long id;

    private String value;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @JsonIgnore
    private Date createAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @JsonIgnore
    private Date modifiedAt;

    @Version
    @JsonIgnore
    private Long version;

    public ItemDAO () {
        this.createAt = new Date();
        this.modifiedAt = new Date();
    }

    @PreUpdate
    public void setModifiedAt() {
        this.modifiedAt = new Date();
    }

}
