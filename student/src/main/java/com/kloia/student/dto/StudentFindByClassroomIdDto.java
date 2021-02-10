package com.kloia.student.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentFindByClassroomIdDto {
    List<Integer> classroomIds;
}
