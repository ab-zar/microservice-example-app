package com.zvm.bug.mappers;

import com.zvm.bug.dto.BugDto;
import com.zvm.bug.entities.Bug;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BugMapper {
    @Mapping(source = "bug.id", target = "id")
    @Mapping(source = "bug.taskId", target = "taskId")
    @Mapping(source = "bug.createdByUserId", target = "createdByUserId")
    @Mapping(source = "bug.name", target = "name")
    @Mapping(source = "bug.description", target = "description")
    @Mapping(source = "bug.bugState", target = "state")

    BugDto toBugDto(Bug bug);
    List<BugDto> toBugDto(List<Bug> bugs);

}
