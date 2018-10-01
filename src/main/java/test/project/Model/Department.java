package test.project.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import test.project.Model.Abstract.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by i.saenko on 29.09.2018.
 */
@Data
@Table(name = "DEPARTMENTS")
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Department extends BaseEntity {

    @Column(nullable = false, unique = true, name = "name")
    private String name;
    @OneToMany(mappedBy = "workerGroup")
    private Set<Worker> workers;
}
