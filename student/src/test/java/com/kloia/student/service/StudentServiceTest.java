package com.kloia.student.service;

import com.kloia.student.converter.StudentConverter;
import com.kloia.student.dto.StudentFindByClassroomIdDto;
import com.kloia.student.model.Student;
import com.kloia.student.repository.StudentRepository;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentConverter studentConverter;

    @Test
    public void shouldGetStudentByClassroomIds() {
        Student student = mockStudent();
        List<Student> students = new ArrayList<>();
        students.add(student);

        StudentFindByClassroomIdDto studentFindByClassroomIdDto = StudentFindByClassroomIdDto.builder()
                .classroomIds(Collections.singletonList(1))
                .build();
        when(studentRepository.findStudentsByClassroomIds(Collections.singletonList(1))).thenReturn(students);

        List<Student> actual = studentService.getStudentByClassroomIds(studentFindByClassroomIdDto);

        assertEquals(student.getClassroomId(), actual.get(0).getClassroomId());
    }

    @Test
    public void shouldSave() {
        Student student = mockStudent();

        when(studentRepository.save(student)).thenReturn(student);

        Student actual = studentService.save(student);
        assertThat(actual, equalTo(student));
    }

    @Test
    public void shouldDelete() {
        int id = studentService.delete(1);
        verify(studentRepository, times(1)).deleteById(1);
        assertThat(id, equalTo(1));
    }

    @Test
    public void shouldGetAllStudent() {
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(mockStudent()));
        List<Student> allStudent = studentService.getAllStudent();
        assertThat(allStudent, equalTo(Collections.singletonList(mockStudent())));
    }

    @Test
    @SneakyThrows
    public void shouldGetByStudentId() {
        Student student = mockStudent();
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        Student actual = studentService.getStudentById(1);
        assertThat(actual, equalTo(student));
    }

    @Test
    @SneakyThrows
    public void shouldUpdate() {

        Student newStudent = new Student();
        newStudent.setAge(2);
        newStudent.setClassroomId(1);
        newStudent.setEmail("asd");
        newStudent.setId(1);
        newStudent.setName("Ahmet");

        Student student = mockStudent();

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(studentConverter.convert(newStudent, student)).thenReturn(newStudent);
        when(studentRepository.save(newStudent)).thenReturn(newStudent);
        Student actual = studentService.update(newStudent, 1);
        assertThat(actual, equalTo(newStudent));
    }

    public Student mockStudent() {
        Student student = new Student();
        student.setAge(2);
        student.setClassroomId(1);
        student.setEmail("asd");
        student.setId(1);
        student.setName("Hikmet");
        return student;
    }
}