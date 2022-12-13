package esdp.crm.attractor.school.service;

import esdp.crm.attractor.school.entity.Task;
import esdp.crm.attractor.school.entity.TaskType;
import esdp.crm.attractor.school.entity.User;
import esdp.crm.attractor.school.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository repository;

    public List<Task> findAll() {
        return repository.findAll();
    }

    public Task assignUser(Task task, User user) {
        task.setEmployee(user);
        return repository.save(task);
    }

    public Task edit(Task task, String name, TaskType status) {
        task.setName(name);
        task.setType(status);
        return repository.save(task);
    }

}
