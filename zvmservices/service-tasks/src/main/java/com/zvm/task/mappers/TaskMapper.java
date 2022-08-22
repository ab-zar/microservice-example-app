package com.zvm.task.mappers;

import com.zvm.task.dto.TaskDto;
import com.zvm.task.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "task.id", target = "id")
    @Mapping(source = "task.createdByUserId", target = "createdByUserId")
    @Mapping(source = "task.devId", target = "devId")
    @Mapping(source = "task.testerId", target = "testerId")
    @Mapping(source = "task.featureId", target = "featureId")
    @Mapping(source = "task.taskState", target = "taskState")
    @Mapping(source = "task.name", target = "name")
    @Mapping(source = "task.description", target = "description")
    TaskDto toTaskDto(Task task);
    List<TaskDto> toTaskDto(List<Task> tasks);
}
