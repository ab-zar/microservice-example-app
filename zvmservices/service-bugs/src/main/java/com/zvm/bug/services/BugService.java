package com.zvm.bug.services;

import com.zvm.bug.dto.BugCreationRequest;
import com.zvm.bug.dto.BugDto;
import com.zvm.clients.bug.TaskBugCheckResponse;

import java.util.List;

public interface BugService {
    BugDto createBug(BugCreationRequest bugCreationRequest);

    BugDto resolveBug(Integer bugId);

    BugDto getBugById(Integer bugId);

    List<BugDto> getAllBugs();

    List<BugDto> getBugsOfTask(Integer taskId);

    boolean isResolved(Integer bugId);

    TaskBugCheckResponse hasBug(Integer taskId);
}
