package br.com.wisebyte.samarco.model.usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Usuario {

    @Id
    private String usuario;

    private String nome;

    @CreationTimestamp
    @Setter( AccessLevel.NONE )
    Instant createDate;

    @UpdateTimestamp
    @Setter( AccessLevel.NONE )
    Instant lastUpdate;

    @Version
    @Setter( AccessLevel.NONE )
    Long version;
}
