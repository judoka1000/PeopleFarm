package com.amazone.peoplefarm.services;

import com.amazone.peoplefarm.models.Person;
import com.amazone.peoplefarm.models.Status;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonServiceTest {

    private static PersonService mockedPersonService;
    private static Person person1;
    private static Person person2;
    private static Person person3;
    private static Person person4;

    @BeforeClass
    public static void setUp(){
        mockedPersonService = mock(PersonService.class);

        List<Person> personList = new ArrayList<>();
        personList.add(person1 = new Person());
        personList.add(person1 = new Person());
        personList.add(person1 = new Person());
        personList.add(person1 = new Person());
        personList.add(person1 = new Person());
        personList.add(person1 = new Person());

        when(mockedPersonService.findAll()).thenReturn(personList);
        when(mockedPersonService.findOne(anyInt())).thenReturn(person1);
        when(mockedPersonService.save(any(Person.class))).thenReturn(person1);
        when(mockedPersonService.exists(anyInt())).thenReturn(true);
    }

    @Test
    public void testFindOne(){
        int id = 5;

        Person person = mockedPersonService.findOne(id);

        assertNotNull(person);
//        assertEquals(Person.Gender.MALE, person.getGender());
//        assertEquals(5, person.getStatus().getAge());
//        assertEquals(Status.Health.HEALTHY, person.getStatus().getHealth());
    }

}
