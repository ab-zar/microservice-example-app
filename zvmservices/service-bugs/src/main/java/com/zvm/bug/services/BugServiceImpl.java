package com.zvm.bug.services;

import com.zvm.bug.dto.BugCreationRequest;
import com.zvm.bug.dto.BugDto;
import com.zvm.bug.entities.Bug;
import com.zvm.bug.enums.BugState;
import com.zvm.bug.mappers.BugMapper;
import com.zvm.bug.repositories.BugRepository;
import com.zvm.clients.bug.BugNotFoundException;
import com.zvm.clients.bug.TaskBugCheckResponse;
import com.zvm.clients.task.TaskClient;
import com.zvm.clients.task.TaskNotFoundException;
import com.zvm.clients.task.TaskPresenceResponse;
import com.zvm.clients.user.UserClient;
import com.zvm.clients.user.UserNotFoundException;
import com.zvm.clients.user.UserPresenceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BugServiceImpl implements BugService {

    private final BugRepository bugRepository;
    private final BugMapper mapper;
    private final TaskClient taskClient;
    private final UserClient userClient;

    @Override
    public BugDto createBug(BugCreationRequest bugCreationRequest) {
        TaskPresenceResponse taskPresenceResponse = taskClient.isPresent(Integer.valueOf(bugCreationRequest.taskId()));
        if(!taskPresenceResponse.isPresent()){
            throw new TaskNotFoundException("No such task!", HttpStatus.NOT_FOUND);
        }
        UserPresenceResponse userPresenceResponse = userClient.isPresent(Integer.valueOf(bugCreationRequest.createdByUserId()));
        if(!userPresenceResponse.isPresent()){
            throw new UserNotFoundException("No such user!", HttpStatus.NOT_FOUND);
        }
        Bug bug = Bug.builder()
                .taskId(Integer.valueOf(bugCreationRequest.taskId()))
                .createdByUserId(Integer.valueOf(bugCreationRequest.createdByUserId()))
                .name(bugCreationRequest.bugName())
                .description(bugCreationRequest.description())
                .bugState(BugState.NOT_RESOLVED)
                .build();

        bugRepository.saveAndFlush(bug);
        return mapper.toBugDto(bug);
    }

    @Override
    public BugDto resolveBug(Integer bugId) {
        Bug bug = bugRepository.findById(bugId)
                .orElseThrow(() -> new BugNotFoundException("No such bug!", HttpStatus.NOT_FOUND));
        bug.setBugState(BugState.RESOLVED);
        bugRepository.saveAndFlush(bug);
        return mapper.toBugDto(bug);
    }

    @Override
    public BugDto getBugById(Integer bugId) {
        Bug bug = bugRepository.findById(bugId)
                .orElseThrow(() -> new BugNotFoundException("No such bug!", HttpStatus.NOT_FOUND));
        return mapper.toBugDto(bug);
    }

    @Override
    public List<BugDto> getAllBugs() {
        List<Bug> bugs = bugRepository.findAll();
        return mapper.toBugDto(bugs);
    }

    @Override
    public List<BugDto> getBugsOfTask(Integer taskId) {
        List<Bug> bugs = bugRepository.getBugsByTaskId(taskId);
        return mapper.toBugDto(bugs);
    }

    @Override
    public boolean isResolved(Integer bugId) {
        boolean isResolved = false;
        Bug bug = bugRepository.findById(bugId)
                .orElseThrow(() -> new BugNotFoundException("No such bug!", HttpStatus.NOT_FOUND));
        if(bug.getBugState().equals(BugState.RESOLVED)){
            isResolved = true;
        }
        return isResolved;
    }

    @Override
    public TaskBugCheckResponse hasBug(Integer taskId) {
        Boolean hasBug = false;
        hasBug = bugRepository.existsBugsByTaskId(taskId);
        if(hasBug) {
            List<Bug> bugs = bugRepository.getBugsByTaskId(taskId);
            hasBug = bugs.stream().anyMatch(bug -> bug.getBugState().equals(BugState.NOT_RESOLVED));
        }
        return new TaskBugCheckResponse(hasBug);
    }
}
