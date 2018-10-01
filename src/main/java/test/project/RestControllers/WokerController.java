package test.project.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import test.project.Model.Worker;
import test.project.Repository.DepartmentRepo;
import test.project.Repository.WorkerRepo;
import test.project.RestControllers.DTO.WorkerDTO;
import test.project.RestControllers.Exception.DepartmentNotFoundExeption;
import test.project.RestControllers.Exception.WorkerNotFoundException;

import java.util.function.Supplier;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by i.saenko on 29.09.2018.
 */
@Transactional
@RestController
@RequestMapping("/api/workers")
public class WokerController {

    private final WorkerRepo workerRepo;

    private final DepartmentRepo departmentRepo;

    @Autowired
    public WokerController(WorkerRepo workerRepo, DepartmentRepo departmentRepo){
        this.workerRepo = workerRepo;
        this.departmentRepo = departmentRepo;
    }

    @GetMapping
    public List<WorkerDTO> getWokers() {
        return StreamSupport.stream(workerRepo.findAll().spliterator(),false).map(WorkerDTO::new).collect(toList());
    }

    @GetMapping("/department/{id}")
    public List<WorkerDTO> getWokersByDepartmentId(@PathVariable Long id){
        return StreamSupport.stream(workerRepo.findByWorkerGroupId(id).spliterator(), false).map(WorkerDTO::new).collect(toList());
    }

    @GetMapping("/{id}")
    public List<WorkerDTO> getWokerById(@PathVariable Long id) throws WorkerNotFoundException {
        return Stream.of(workerRepo.findById(id).orElseThrow(notFoundWokers("Worker", id.toString()))).map(WorkerDTO::new).collect(toList());
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void createWoker(@Valid @RequestBody WorkerDTO workerDTO) {
            Worker worker = new Worker(workerDTO.getFirstName(),
                    workerDTO.getLastName(), workerDTO.getPhone(),
                    departmentRepo.findById(workerDTO.getWorkerGroupId()).orElse(departmentRepo.findById(0L).get()));
            workerRepo.save(worker);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public void updateWoker(@PathVariable Long id, @Valid @RequestBody WorkerDTO workerDTO) throws WorkerNotFoundException, DepartmentNotFoundExeption {
        Worker worker = workerRepo.findById(id).orElseThrow(notFoundWokers("Worker", workerDTO.getFirstName()));
        worker.setPhone(workerDTO.getPhone());
        worker.setWorkerGroup(departmentRepo.findById(workerDTO.getWorkerGroupId()).orElseThrow(notFoundDepartment("Department", workerDTO.getWorkerGroupName())));
        worker.setLastName(workerDTO.getLastName());
        worker.setFirstName(workerDTO.getFirstName());
        workerRepo.save(worker);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    public void deleteWoker (@PathVariable Long id) {
        workerRepo.deleteById(id);
    }


    private Supplier<WorkerNotFoundException> notFoundWokers(String name, String value) {
        return () -> new WorkerNotFoundException(format("%s %s not found", name, value));
    }

    private Supplier<DepartmentNotFoundExeption> notFoundDepartment(String name, String value) {
        return () -> new DepartmentNotFoundExeption(format("%s %s not found", name, value));
    }

}
