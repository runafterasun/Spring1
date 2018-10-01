package test.project.Model.Abstract;
import lombok.Data;

import javax.persistence.*;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * Created by i.saenko on 29.09.2018.
 */

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = SEQUENCE, generator = "id_generator")
    @SequenceGenerator(initialValue = 3, name = "id_generator")
    private Long id;
}