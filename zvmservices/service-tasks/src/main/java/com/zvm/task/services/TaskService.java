package com.zvm.task.services;

import com.zvm.clients.task.FeatureTasksCompletionResponse;
import com.zvm.task.dto.TaskAssignmentRequest;
import com.zvm.task.dto.TaskCreationRequest;
import com.zvm.task.dto.TaskDto;

import java.util.List;

public interface TaskService {
    List<TaskDto> getAllTasks();

    TaskDto getTaskById(Integer taskId);

    Boolean isPresent(Integer taskId);

    List<TaskDto> getAllTasksOfDev(Integer devId);

    List<TaskDto> getAllOpenTasksOfDev(Integer devId);

    TaskDto assignToTester(TaskAssignmentRequest taskAssignmentRequest);

    TaskDto resolve(Integer taskId);

    List<TaskDto> getAllResolvedTasks();

    TaskDto createTask(TaskCreationRequest taskCreationRequest);

    TaskDto assignToDev(Integer devId, Integer taskId);

    TaskDto reject(Integer taskId);

    TaskDto completeTask(Integer taskId);

    FeatureTasksCompletionResponse isFeatureComplete(Integer featureId);
}
