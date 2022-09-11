package com.zvm.task.mappers;

import com.zvm.task.dto.TaskDto;
import com.zvm.task.entities.Task;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-11T21:51:16+0100",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto toTaskDto(Task task) {
        if ( task == null ) {
            return null;
        }

        Integer id = null;
        Integer createdByUserId = null;
        Integer devId = null;
        Integer testerId = null;
        Integer featureId = null;
        String taskState = null;
        String name = null;
        String description = null;

        id = task.getId();
        createdByUserId = task.getCreatedByUserId();
        devId = task.getDevId();
        testerId = task.getTesterId();
        featureId = task.getFeatureId();
        if ( task.getTaskState() != null ) {
            taskState = task.getTaskState().name();
        }
        name = task.getName();
        description = task.getDescription();

        TaskDto taskDto = new TaskDto( id, createdByUserId, devId, testerId, featureId, taskState, name, description );

        return taskDto;
    }

    @Override
    public List<TaskDto> toTaskDto(List<Task> tasks) {
        if ( tasks == null ) {
            return null;
        }

        List<TaskDto> list = new ArrayList<TaskDto>( tasks.size() );
        for ( Task task : tasks ) {
            list.add( toTaskDto( task ) );
        }

        return list;
    }
}
