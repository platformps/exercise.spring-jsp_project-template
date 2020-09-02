package com.github.perscholas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by leon on 9/2/2020.
 */
@Service
public class PersonService implements PersonServiceInterface {

    private PersonRepository repository;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    private Logger getLogger() {
        return Logger.getLogger(getClass().getName());
    }
    private void info(String message, Object... args) {
        getLogger().log(Level.INFO, String.format(message, args));
    }

    @Override
    public Person create(Person person) {
        info( "Attempting to create the following person\n\t %s", person.toString());
        Person persistedPerson = repository.save(person);
        info( "Successfully created the following person\n\t %s", person.toString());
        return persistedPerson;
    }

    @Override
    public Person readById(Long id) {
        info("Attempting to find person with id [ %s ]", id);
        Person personInDatabase = repository.findById(id).get();
        info("Successfully found person with id [ %s ]\n\t %s", id, personInDatabase.toString());
        return personInDatabase;
    }

    @Override
    public List<Person> readAll() {
        info("Attempting to retrieve all persons from database");
        Iterable<Person> iterablePerson = repository.findAll();
        info("Successfully retrieved all persons from database");
        List<Person> personList = new ArrayList<>();
        iterablePerson.forEach(personList::add);
        return personList;
    }

    @Override
    public Person update(Long idOfPersonToUpdate, Person newData) {
        Person personInDatabase = readById(idOfPersonToUpdate);
        info("Attempting to update person with id [ %s ] from\n\t %s \nto\n\t %s",
                idOfPersonToUpdate,
                personInDatabase,
                newData);
        personInDatabase.setFirstName(newData.getFirstName());
        personInDatabase.setLastName(newData.getLastName());
        personInDatabase.setBirthDate(newData.getBirthDate());
        personInDatabase = repository.save(personInDatabase);
        info("Successfully updated person with id [ %s ] from\n\t %s \nto\n\t %s",
                idOfPersonToUpdate,
                personInDatabase,
                newData);
        return personInDatabase;
    }

    @Override
    public Person deleteById(Long idOfPersonToDelete) {
        Person personToBeDeleted = readById(idOfPersonToDelete);
        repository.delete(personToBeDeleted);
        return personToBeDeleted;
    }
}
