package test.project.Repository;


import org.springframework.data.repository.CrudRepository;
import test.project.Model.Worker;

import java.util.List;


/**
 * Created by i.saenko on 29.09.2018.
 */
public interface WorkerRepo extends CrudRepository<Worker, Long> {
    List<Worker> findByWorkerGroupId(Long id);
}
