package model.dao;

import java.util.List;

import model.Event;
import model.ModelException;

public interface EventDAO {

	boolean save(Event event) throws ModelException;

	boolean update(Event event) throws ModelException;

	boolean delete(Event event) throws ModelException;

	List<Event> listAll() throws ModelException;

	Event findById(int id) throws ModelException;

}