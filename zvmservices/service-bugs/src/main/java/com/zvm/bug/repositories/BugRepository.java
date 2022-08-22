package com.zvm.bug.repositories;

import com.zvm.bug.entities.Bug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BugRepository extends JpaRepository<Bug, Integer> {

    List<Bug> getBugsByTaskId(Integer taskId);

    Boolean existsBugsByTaskId(Integer taskId);

}
