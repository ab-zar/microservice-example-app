package com.zvm.task.controllers;

import com.zvm.clients.bug.TaskBugCheckResponse;
import com.zvm.clients.task.FeatureTasksCompletionResponse;
import com.zvm.task.dto.TaskAssignmentRequest;
import com.zvm.task.dto.TaskCreationRequest;
import com.zvm.task.dto.TaskDto;
import com.zvm.clients.task.TaskPresenceResponse;
import com.zvm.task.dto.TaskResolutionRequest;
import com.zvm.task.services.TaskService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tasks")
public record TaskController(TaskService taskService) {

    @GetMapping("/get/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable("taskId") Integer taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/dev/{devId}")
    public ResponseEntity<List<TaskDto>> getAllTasksOfdDev(@PathVariable("devId") Integer devId) {
        return ResponseEntity.ok(taskService.getAllTasksOfDev(devId));
    }

    @GetMapping("/dev/openTasks/{devId}")
    public ResponseEntity<List<TaskDto>> getAllOpenTasksOfDev(@PathVariable("devId") Integer devId) {
        return ResponseEntity.ok(taskService.getAllOpenTasksOfDev(devId));
    }

    @GetMapping("/resolved")
    public ResponseEntity<List<TaskDto>> getAllResolvedTasks() {
        return ResponseEntity.ok(taskService.getAllResolvedTasks());
    }

    @PostMapping("/assign/tester")
    public ResponseEntity<TaskDto> assignTask(@RequestBody TaskAssignmentRequest taskAssignmentRequest) {
        return ResponseEntity.ok(taskService.assignToTester(taskAssignmentRequest));
    }

    @PostMapping("/resolve")
    public ResponseEntity<TaskDto> resolveTask(@RequestBody TaskResolutionRequest taskResolutionRequest) {
        return ResponseEntity.ok(taskService.resolve(taskResolutionRequest));
    }

    @PutMapping("/create")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskCreationRequest taskCreationRequest) {
        return ResponseEntity.ok(taskService.createTask(taskCreationRequest));
    }

    @PostMapping("/assign/{devId}")
    public ResponseEntity<TaskDto> assignToDev(@PathVariable("devId") Integer devId,
                                               @RequestHeader("taskId") Integer taskId) {
        return ResponseEntity.ok(taskService.assignToDev(devId, taskId));
    }

    @PostMapping("/reject/{taskId}")
    public ResponseEntity<TaskDto> rejectTask(@PathVariable("taskId") Integer taskId) {
        return ResponseEntity.ok(taskService.reject(taskId));
    }

    @PostMapping("/complete/{taskId}")
    public ResponseEntity<TaskDto> completeTask(@PathVariable("taskId") Integer taskId) {
        return ResponseEntity.ok(taskService.completeTask(taskId));
    }

    @GetMapping("/isPresent/{taskId}")
    public TaskPresenceResponse isPresent(@PathVariable("taskId") Integer taskId){
        return new TaskPresenceResponse(taskService.isPresent(taskId));
    }

    @GetMapping("/check/feature/{featureId}")
    public FeatureTasksCompletionResponse isFeatureComplete(@PathVariable("featureId") Integer featureId) {
        return taskService.isFeatureComplete(featureId);
    }
}
