package com.zvm.task.services;

import com.zvm.clients.bug.BugClient;
import com.zvm.clients.bug.TaskBugCheckResponse;
import com.zvm.clients.feature.FeatureClient;
import com.zvm.clients.feature.FeatureNotFoundException;
import com.zvm.clients.feature.FeaturePresenceResponse;
import com.zvm.clients.task.FeatureTasksCompletionResponse;
import com.zvm.clients.task.TaskNotFoundException;
import com.zvm.clients.user.UserClient;
import com.zvm.clients.user.UserNotFoundException;
import com.zvm.clients.user.UserPresenceResponse;
import com.zvm.task.dto.TaskAssignmentRequest;
import com.zvm.task.dto.TaskCreationRequest;
import com.zvm.task.dto.TaskDto;
import com.zvm.task.entities.Task;
import com.zvm.task.enums.TaskState;
import com.zvm.task.mappers.TaskMapper;
import com.zvm.task.repositries.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserClient userClient;
    private final BugClient bugClient;
    private final FeatureClient featureClient;

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        log.info("Getting all tasks");
        return taskMapper.toTaskDto(tasks);
    }

    @Override
    public TaskDto getTaskById(Integer taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("No such task!", HttpStatus.BAD_REQUEST));
        log.info("Getting task by id {} {}", taskId, task);
        return taskMapper.toTaskDto(task);
    }

    @Override
    public Boolean isPresent(Integer taskId) {
        Boolean isPresent = taskRepository.existsById(taskId);
        log.info("Task taskId {} is present {}", taskId, isPresent);
        return isPresent;
    }

    @Override
    public List<TaskDto> getAllTasksOfDev(Integer devId) {
        UserPresenceResponse userPresenceResponse = userClient.isPresent(devId);
        if(!userPresenceResponse.isPresent()){
            throw new UserNotFoundException("No such user!", HttpStatus.BAD_REQUEST);
        }
        List<Task> tasks = taskRepository.getAllByDevId(devId);
        if(tasks.isEmpty()){
            throw new TaskNotFoundException("User does not have any task!", HttpStatus.BAD_REQUEST);
        }
        return taskMapper.toTaskDto(tasks);
    }

    @Override
    public List<TaskDto> getAllOpenTasksOfDev(Integer devId) {
        UserPresenceResponse userPresenceResponse = userClient.isPresent(devId);
        if(!userPresenceResponse.isPresent()){
            throw new UserNotFoundException("No such user!", HttpStatus.BAD_REQUEST);
        }
        List<Task> tasks = taskRepository.getAllByDevIdAndTaskStateEquals(devId, TaskState.OPEN);
        if(tasks.isEmpty()){
            throw new TaskNotFoundException("User does not have any open task!", HttpStatus.BAD_REQUEST);
        }
        return taskMapper.toTaskDto(tasks);
    }

    @Override
    public TaskDto assignToTester(TaskAssignmentRequest taskAssignmentRequest) {
        UserPresenceResponse userPresenceResponse =
                userClient.isPresent(Integer.valueOf(taskAssignmentRequest.testerId()));
        if(!userPresenceResponse.isPresent()){
            throw new UserNotFoundException("No such tester!", HttpStatus.BAD_REQUEST);
        }
        Task task = taskRepository.findById(Integer.valueOf(taskAssignmentRequest.taskId()))
                .orElseThrow(() -> new TaskNotFoundException("No such task!", HttpStatus.BAD_REQUEST));
        task.setTesterId(Integer.valueOf(taskAssignmentRequest.testerId()));
        task.setTaskState(TaskState.RESOLVED);
        taskRepository.saveAndFlush(task);
        return taskMapper.toTaskDto(task);
    }

    @Override
    public TaskDto resolve(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("No such task!", HttpStatus.BAD_REQUEST));
        task.setTaskState(TaskState.RESOLVED);
        taskRepository.saveAndFlush(task);
        return taskMapper.toTaskDto(task);
    }

    @Override
    public List<TaskDto> getAllResolvedTasks() {
        List<Task> tasks = taskRepository.getAllByTaskStateEquals(TaskState.RESOLVED);
        return taskMapper.toTaskDto(tasks);
    }

    @Override
    public TaskDto createTask(TaskCreationRequest taskCreationRequest) {
        FeaturePresenceResponse featurePresenceResponse =
                featureClient.isPresent(Integer.valueOf(taskCreationRequest.featureId()));
        if(!featurePresenceResponse.isPresent()){
            throw new FeatureNotFoundException("No such feature!", HttpStatus.BAD_REQUEST);
        }
        Task task = Task.builder()
                .createdByUserId(Integer.valueOf(taskCreationRequest.createdByUserId()))
                .name(taskCreationRequest.name())
                .description(taskCreationRequest.description())
                .taskState(TaskState.OPEN)
                .build();
        taskRepository.saveAndFlush(task);
        return taskMapper.toTaskDto(task);
    }

    @Override
    public TaskDto assignToDev(Integer devId, Integer taskId) {
        UserPresenceResponse userPresenceResponse = userClient.isPresent(devId);
        if(!userPresenceResponse.isPresent()) {
            throw new UserNotFoundException("No such user!", HttpStatus.BAD_REQUEST);
        }
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("No such task!", HttpStatus.BAD_REQUEST));

        task.setDevId(devId);
        task.setTaskState(TaskState.IN_PROGRESS);

        taskRepository.saveAndFlush(task);
        return taskMapper.toTaskDto(task);
    }

    @Override
    public TaskDto reject(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("No such task!", HttpStatus.BAD_REQUEST));
        task.setTaskState(TaskState.IN_PROGRESS);
        taskRepository.saveAndFlush(task);
        return taskMapper.toTaskDto(task);
    }

    @Override
    public TaskDto completeTask(Integer taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("No such task!", HttpStatus.BAD_REQUEST));
        TaskBugCheckResponse taskBugCheckResponse = bugClient.hasBug(taskId);
        if(taskBugCheckResponse.hasBug()){
            throw new IllegalStateException("Task has bugs to resolve!");
        }
        task.setTaskState(TaskState.COMPLETED);
        taskRepository.saveAndFlush(task);
        return taskMapper.toTaskDto(task);
    }

    @Override
    public FeatureTasksCompletionResponse isFeatureComplete(Integer featureId) {
        List<Task> tasks = taskRepository.getAllByFeatureId(featureId);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("Feature doesn't have any tasks!", HttpStatus.BAD_REQUEST);
        }
        Boolean isFeatureComplete = tasks.stream().allMatch(task -> task.getTaskState().equals(TaskState.COMPLETED));
        return new FeatureTasksCompletionResponse(isFeatureComplete);
    }
}
