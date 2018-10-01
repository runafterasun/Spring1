package test.project.Repository;

import org.springframework.data.repository.CrudRepository;
import test.project.Model.Department;

/**
 * Created by i.saenko on 29.09.2018.
 */
public interface DepartmentRepo extends CrudRepository<Department, Long> {
}
