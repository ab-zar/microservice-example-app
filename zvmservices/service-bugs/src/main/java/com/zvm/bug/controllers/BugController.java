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
        return ResponseEntity.ok(bugService.getAllBugs());
    }

    @GetMapping("/{bugId}")
    public ResponseEntity<BugDto> getBug(@PathVariable("bugId") Integer bugId) {
        return ResponseEntity.ok(bugService.getBugById(bugId));
    }

    @GetMapping("/isResolved/{bugId}")
    public BugCheckResponse isResolved(@PathVariable("bugId") Integer bugId) {
        boolean isResolved = bugService.isResolved(bugId);
        return new BugCheckResponse(isResolved);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<BugDto>> getBugsOfTask(@PathVariable("taskId") Integer taskId) {
        return ResponseEntity.ok(bugService.getBugsOfTask(taskId));
    }

    @GetMapping("/task/check/{taskId}")
    public TaskBugCheckResponse hasBug(@PathVariable("taskId") Integer taskId) {
        return bugService.hasBug(taskId);
    }

    @PostMapping("/create")
    public ResponseEntity<BugDto> createBug(@RequestBody BugCreationRequest bugCreationRequest) {
        return ResponseEntity.ok(bugService.createBug(bugCreationRequest));
    }

    @PostMapping("/resolve")
    public ResponseEntity<BugDto> resolveBug(@RequestBody BugResolutionRequest bugResolutionRequest) {
        return ResponseEntity.ok(bugService.resolveBug(bugResolutionRequest));
    }
}
