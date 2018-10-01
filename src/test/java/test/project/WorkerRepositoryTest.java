package test.project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import test.project.Model.Worker;
import test.project.Repository.DepartmentRepo;
import test.project.Repository.WorkerRepo;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by i.saenko on 29.09.2018.
 */


@RunWith(SpringRunner.class)
@DataJpaTest
public class WorkerRepositoryTest {

    @Autowired
    private WorkerRepo workerRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Test
    public void whenFindByWorkersGroupId_thenReturnWorkers(){
        Worker worker = new Worker("whenFindByWorkersGroupId","set","12345",departmentRepo.findById(1L).orElseThrow(notFoundEntity("Department", "1L")));
        Worker worker1 = workerRepo.findById(1L).orElseThrow(notFoundEntity("Worker error", ""));
        workerRepo.save(worker);

        List<Worker> found = workerRepo.findByWorkerGroupId(1L);
        assertThat(found).hasSize(2).extracting(Worker::getFirstName).containsOnly(worker.getFirstName(), worker1.getFirstName());
    }

    @Test
    public void whenFindByWorkerId_thenReturnWorker(){
        Worker worker = new Worker("whenFindByWorkerId","set","12345",departmentRepo.findById(1L).orElseThrow(notFoundEntity("Department", "1L")));
        workerRepo.save(worker);

        Worker found = workerRepo.findById(worker.getId()).orElseThrow(notFoundEntity("Worker in Entity with id ", ""));
        assertThat(found.getFirstName()).isEqualTo(worker.getFirstName());
    }

    @Test
    public void whenFindAllWorkers_thenReturnAllWorkers(){
        Worker worker1 = workerRepo.findById(1L).orElseThrow(notFoundEntity("Worker1 error", ""));
        Worker worker2 = workerRepo.findById(2L).orElseThrow(notFoundEntity("Worker2 error", ""));

        List<Worker> found = StreamSupport.stream(workerRepo.findAll().spliterator(),false).collect(toList());
        assertThat(found).hasSize(2).extracting(Worker::getFirstName).containsOnly(worker1.getFirstName(), worker2.getFirstName());
    }

    @Test
    public void whenInvalidName_thenReturnNull() {
        Worker fromDb = workerRepo.findById(3L).orElse(null);
        assertThat(fromDb).isNull();
    }

    private Supplier<EntityNotFoundException> notFoundEntity(String name, String value) {
        return () -> new EntityNotFoundException(format("%s %s not found", name, value));
    }

}
