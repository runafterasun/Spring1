package test.project.RestControllers.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import test.project.Model.Worker;

import javax.validation.constraints.NotNull;

/**
 * Created by i.saenko on 29.09.2018.
 */

@Data
@AllArgsConstructor
public class WorkerDTO {

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    private String phone;

    private String workerGroupName;
    @NotNull
    private Long workerGroupId;

    public WorkerDTO(Worker worker){
        this.firstName = worker.getFirstName();
        this.lastName = worker.getLastName();
        this.phone = worker.getPhone();
        this.workerGroupName = worker.getWorkerGroup().getName();
        this.workerGroupId = worker.getWorkerGroup().getId();
    }

    public WorkerDTO(){}
}
