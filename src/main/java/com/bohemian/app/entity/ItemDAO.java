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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    @SequenceGenerator(name = "item_seq", sequenceName = "item_seq", allocationSize = 1)
    @JsonIgnore
    private Long id;

    @Column(name = "userValue")
    private String userValue;

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
