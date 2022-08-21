package com.example.springboot.dao;

import com.example.springboot.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {
    private static final List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personMaybe = selectPersonById(id);
        if (personMaybe.isEmpty()) {
            return 0;
        }

        DB.remove(personMaybe.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        Optional<Person> personToUpdateMaybe = DB.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (personToUpdateMaybe.isEmpty()) {
            return 0;
        }

        int indexOfPersonToUpdate = DB.indexOf(personToUpdateMaybe.get());
        DB.set(indexOfPersonToUpdate, new Person(id, person.getName()));
        return 1;
    }
}
