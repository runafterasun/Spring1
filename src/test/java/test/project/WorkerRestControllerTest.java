package test.project;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import test.project.RestControllers.DTO.WorkerDTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by i.saenko on 29.09.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Main.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class WorkerRestControllerTest {

    @Autowired
    private MockMvc mvc;

    private WorkerDTO workerDTO;

    @Before
    public void CreateWorkerDto(){
        this.workerDTO = new WorkerDTO();
        workerDTO.setFirstName("test");
        workerDTO.setLastName("test");
        workerDTO.setPhone("12334");
        workerDTO.setWorkerGroupId(1L);
    }

    @Test
    public void testCreateWorker() throws Exception {
        mvc.perform(post("/api/workers").contentType(MediaType.APPLICATION_JSON).content(toJson(workerDTO))).andExpect(status().isCreated());
    }

    @Test
    public void testGetWorkerSuccess() throws Exception {
        this.mvc.perform(get("/api/workers/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.[0].workerGroupId").value(1))
                .andExpect(jsonPath("$.[0].firstName").value("Игорь"));
    }

    @Test
    public void testGetWorkerNotFound() throws Exception {
        this.mvc.perform(get("/api/workers/-1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetDepartmentWorkerSuccess() throws Exception {
        this.mvc.perform(get("/api/workers/department/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.[0].workerGroupId").value(1))
                .andExpect(jsonPath("$.[0].firstName").value("Игорь"));
    }

    @Test
    public void testUpdateWorker() throws Exception {
        WorkerDTO worker = workerDTO;
        worker.setFirstName("тест");
        worker.setWorkerGroupId(2L);
        mvc.perform(put("/api/workers/2").contentType(MediaType.APPLICATION_JSON).content(toJson(worker))).andExpect(status().isOk());
    }

    @Test
    public void testUpdateErrorWorker() throws Exception {
        mvc.perform(put("/api/workers/-2").contentType(MediaType.APPLICATION_JSON).content(toJson(workerDTO))).andExpect(status().isNotFound());
    }


    private static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
