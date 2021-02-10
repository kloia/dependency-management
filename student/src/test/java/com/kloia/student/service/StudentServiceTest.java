package com.kloia.student.service;

import com.kloia.student.dto.StudentFindByClassroomIdDto;
import com.kloia.student.model.Student;
import com.kloia.student.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Test
    public void shouldGetStudentByClassroomIds() {
        Student student = new Student();
        student.setAge(2);
        student.setClassroomId(1);
        student.setEmail("asd");
        student.setId(1);
        student.setName("Hikmet");
        List<Student> students = new ArrayList<>();
        students.add(student);

        StudentFindByClassroomIdDto studentFindByClassroomIdDto = StudentFindByClassroomIdDto.builder()
                .classroomIds(Collections.singletonList(1))
                .build();
        when(studentRepository.findStudentsByClassroomIds(Collections.singletonList(1))).thenReturn(students);

        List<Student> actual = studentService.getStudentByClassroomIds(studentFindByClassroomIdDto);

        assertEquals(student.getClassroomId(), actual.get(0).getClassroomId());
    }
}