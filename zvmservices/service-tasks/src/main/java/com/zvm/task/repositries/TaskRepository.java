package com.zvm.task.repositries;

import com.zvm.task.entities.Task;
import com.zvm.task.enums.TaskState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> getAllByDevId(Integer userId);

    List<Task> getAllByDevIdAndTaskStateEquals(Integer userId, TaskState taskState);

    List<Task> getAllByTaskStateEquals(TaskState taskState);

    List<Task> getAllByTesterId(Integer testerId);

    List<Task> getAllByCreatedByUserId(Integer userId);

    List<Task> getAllByFeatureId(Integer featureId);
}
