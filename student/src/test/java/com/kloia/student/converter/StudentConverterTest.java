package com.kloia.student.converter;

import com.kloia.student.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class StudentConverterTest {

    @InjectMocks
    private StudentConverter studentConverter;

    @Test
    public void shouldConverter() {
        Student student = mockStudent();
        Student newStudent = newStudent();

        Student actual = studentConverter.convert(newStudent, student);
        assertThat(actual, equalTo(newStudent));
    }

    @Test
    public void shouldConverterIfEmailIsNull() {
        Student student = mockStudent();
        Student newStudent = newStudent();
        newStudent.setEmail(null);
        Student actual = studentConverter.convert(newStudent, student);
        newStudent.setEmail("asd");
        assertThat(actual, equalTo(newStudent));
    }

    @Test
    public void shouldConverterIfClassroomIdIsNull() {
        Student student = mockStudent();
        Student newStudent = newStudent();
        newStudent.setClassroomId(null);

        Student actual = studentConverter.convert(newStudent, student);
        newStudent.setClassroomId(1);
        assertThat(actual, equalTo(newStudent));
    }

    @Test
    public void shouldConverterIfAgeIsNull() {
        Student student = mockStudent();
        Student newStudent = newStudent();
        newStudent.setAge(null);

        Student actual = studentConverter.convert(newStudent, student);
        newStudent.setAge(2);
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

    public Student newStudent() {
        Student newStudent = new Student();
        newStudent.setAge(2);
        newStudent.setClassroomId(1);
        newStudent.setEmail("asd");
        newStudent.setId(1);
        newStudent.setName("Ahmet");
        return newStudent;
    }
}