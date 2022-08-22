package com.zvm.bug.controllers;

import com.zvm.bug.dto.BugCreationRequest;
import com.zvm.bug.dto.BugDto;
import com.zvm.bug.dto.BugResolutionRequest;
import com.zvm.bug.services.BugService;
import com.zvm.clients.bug.BugCheckResponse;
import com.zvm.clients.bug.TaskBugCheckResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/bugs")
public record BugController(BugService bugService) {

    @GetMapping
    public ResponseEntity<List<BugDto>> getAllBugs() {
        log.info("Getting all bugs");
        return ResponseEntity.ok(bugService.getAllBugs());
    }

    @GetMapping("/{bugId}/get")
    public ResponseEntity<BugDto> getBug(@PathVariable("bugId") Integer bugId) {
        log.info("Getting a bug by id {}", bugId);
        return ResponseEntity.ok(bugService.getBugById(bugId));
    }

    @GetMapping("/{bugId}/isResolved")
    public BugCheckResponse isResolved(@PathVariable("bugId") Integer bugId) {
        log.info("Checking is bug {} is resolved", bugId);
        return new BugCheckResponse(bugService.isResolved(bugId));
    }

    @GetMapping("/task/{taskId}/getBugs")
    public ResponseEntity<List<BugDto>> getBugsOfTask(@PathVariable("taskId") Integer taskId) {
        log.info("Getting all bugs of task {}", taskId);
        return ResponseEntity.ok(bugService.getBugsOfTask(taskId));
    }

    @GetMapping("/task/{taskId}/check")
    public TaskBugCheckResponse hasBug(@PathVariable("taskId") Integer taskId) {
        log.info("Checking if task {} has bugs", taskId);
        return bugService.hasBug(taskId);
    }

    @PostMapping("/create")
    public ResponseEntity<BugDto> createBug(@RequestBody BugCreationRequest bugCreationRequest) {
        log.info("Creating a bug {}", bugCreationRequest);
        return ResponseEntity.ok(bugService.createBug(bugCreationRequest));
    }

    @PostMapping("/{bugId}/resolve")
    public ResponseEntity<BugDto> resolveBug(@PathVariable("bugId") Integer bugId) {
        log.info("Resolving a bug {}", bugId);
        return ResponseEntity.ok(bugService.resolveBug(bugId));
    }
}
