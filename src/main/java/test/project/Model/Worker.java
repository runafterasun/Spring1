package test.project.Model;

import lombok.*;
import test.project.Model.Abstract.BaseEntity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by i.saenko on 29.09.2018.
 */
@Data
@Table(name = "WORKERS")
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Worker extends BaseEntity {
    @NonNull
    @NotNull
    @Column(nullable = false, name = "firstName")
    protected String firstName;

    @NonNull
    @NotNull
    @Column(nullable = false, name = "lastName")
    protected String lastName;

    @Column(name = "phone")
    protected String phone;

    @ManyToOne
    @JoinColumn(name = "WorkerGroup")
    private Department workerGroup;
}
