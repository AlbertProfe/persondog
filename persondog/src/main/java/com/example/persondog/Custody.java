package com.example.persondog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Custody {

    @Id
    private String id;

    @JsonIgnore
    @JoinColumn(name = "PERSON_FK")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Person person;

    @JsonIgnore
    @JoinColumn(name = "DOG_FK")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Dog dog;

    private Date startDate;
    private Date endDate;
    private String contract;
    private String status;
    private boolean isCurrent;
}
