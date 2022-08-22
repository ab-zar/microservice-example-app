package com.zvm.task.controllers;

import com.zvm.clients.bug.TaskBugCheckResponse;
import com.zvm.clients.task.FeatureTasksCompletionResponse;
import com.zvm.task.dto.TaskAssignmentRequest;
import com.zvm.task.dto.TaskCreationRequest;
import com.zvm.task.dto.TaskDto;
import com.zvm.clients.task.TaskPresenceResponse;
import com.zvm.task.dto.TaskResolutionRequest;
import com.zvm.task.services.TaskService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/tasks")
public record TaskController(TaskService taskService) {

    @GetMapping("/{taskId}/get")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable("taskId") Integer taskId) {
        log.info("Getting a task by id {}", taskId);
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        log.info("Getting all tasks");
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/dev/{devId}/getTasks")
    public ResponseEntity<List<TaskDto>> getAllTasksOfdDev(@PathVariable("devId") Integer devId) {
        log.info("Getting all tasks of Developer devId {}", devId);
        return ResponseEntity.ok(taskService.getAllTasksOfDev(devId));
    }

    @GetMapping("/dev/{devId}/openTasks")
    public ResponseEntity<List<TaskDto>> getAllOpenTasksOfDev(@PathVariable("devId") Integer devId) {
        log.info("Getting all open tasks of Developer devId {}", devId);
        return ResponseEntity.ok(taskService.getAllOpenTasksOfDev(devId));
    }

    @GetMapping("/resolved")
    public ResponseEntity<List<TaskDto>> getAllResolvedTasks() {
        log.info("Getting all resolved tasks");
        return ResponseEntity.ok(taskService.getAllResolvedTasks());
    }

    @PostMapping("/tester/assign")
    public ResponseEntity<TaskDto> assignTask(@RequestBody TaskAssignmentRequest taskAssignmentRequest) {
        log.info("assigning task {} to tester {}", taskAssignmentRequest.taskId(), taskAssignmentRequest.testerId());
        return ResponseEntity.ok(taskService.assignToTester(taskAssignmentRequest));
    }

    @PostMapping("/{taskId}/resolve")
    public ResponseEntity<TaskDto> resolveTask(@PathVariable("taskId") Integer taskId) {
        log.info("trying to resolve task taskId {}", taskId);
        return ResponseEntity.ok(taskService.resolve(taskId));
    }

    @PutMapping("/create")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskCreationRequest taskCreationRequest) {
        log.info("Creating a new task {}", taskCreationRequest);
        return ResponseEntity.ok(taskService.createTask(taskCreationRequest));
    }

    @PostMapping("/{taskId}/assign/{devId}")
    public ResponseEntity<TaskDto> assignToDev(@PathVariable("devId") Integer devId,
                                               @PathVariable("taskId") Integer taskId) {
        log.info("Assigning task {} to Developer {}", taskId, devId);
        return ResponseEntity.ok(taskService.assignToDev(devId, taskId));
    }

    @PostMapping("/{taskId}/reject")
    public ResponseEntity<TaskDto> rejectTask(@PathVariable("taskId") Integer taskId) {
        log.info("Rejecting task {}", taskId);
        return ResponseEntity.ok(taskService.reject(taskId));
    }

    @PostMapping("/{taskId}/complete")
    public ResponseEntity<TaskDto> completeTask(@PathVariable("taskId") Integer taskId) {
        log.info("Trying to complete task {}", taskId);
        return ResponseEntity.ok(taskService.completeTask(taskId));
    }

    @GetMapping("/{taskId}/isPresent")
    public TaskPresenceResponse isPresent(@PathVariable("taskId") Integer taskId){
        log.info("Checking if task {} exists", taskId);
        return new TaskPresenceResponse(taskService.isPresent(taskId));
    }

    @GetMapping("/feature/{featureId}/check")
    public FeatureTasksCompletionResponse isFeatureComplete(@PathVariable("featureId") Integer featureId) {
        log.info("Checking if feature {} is complete", featureId);
        return taskService.isFeatureComplete(featureId);
    }
}
