package com.kosmas.springit.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Data
@RequiredArgsConstructor
public class Comment extends Auditable {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private String body;

    @NonNull
    @ManyToOne
    private Link link;
}
