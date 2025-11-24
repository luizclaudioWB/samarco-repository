package br.com.wisebyte.samarco.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@SuperBuilder
public class BaseModel implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Setter( AccessLevel.NONE )
    Long id;

    @CreationTimestamp
    @Setter( AccessLevel.NONE )
    Instant createdDate;

    @UpdateTimestamp
    @Setter( AccessLevel.NONE )
    Instant lastUpdated;

    @Version
    @Setter( AccessLevel.NONE )
    Long version;

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass( ) != o.getClass( ) ) return false;
        BaseModel baseModel = (BaseModel) o;
        return Objects.equals( id, baseModel.id );
    }

    @Override
    public int hashCode( ) {
        return Objects.hash( id );
    }
}
