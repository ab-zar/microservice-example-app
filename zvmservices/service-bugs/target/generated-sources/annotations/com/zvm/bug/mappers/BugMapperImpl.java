package com.zvm.bug.mappers;

import com.zvm.bug.dto.BugDto;
import com.zvm.bug.entities.Bug;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-11T21:51:52+0100",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class BugMapperImpl implements BugMapper {

    @Override
    public BugDto toBugDto(Bug bug) {
        if ( bug == null ) {
            return null;
        }

        Integer id = null;
        Integer taskId = null;
        Integer createdByUserId = null;
        String name = null;
        String description = null;
        String state = null;

        id = bug.getId();
        taskId = bug.getTaskId();
        createdByUserId = bug.getCreatedByUserId();
        name = bug.getName();
        description = bug.getDescription();
        if ( bug.getBugState() != null ) {
            state = bug.getBugState().name();
        }

        BugDto bugDto = new BugDto( id, taskId, createdByUserId, name, description, state );

        return bugDto;
    }

    @Override
    public List<BugDto> toBugDto(List<Bug> bugs) {
        if ( bugs == null ) {
            return null;
        }

        List<BugDto> list = new ArrayList<BugDto>( bugs.size() );
        for ( Bug bug : bugs ) {
            list.add( toBugDto( bug ) );
        }

        return list;
    }
}
