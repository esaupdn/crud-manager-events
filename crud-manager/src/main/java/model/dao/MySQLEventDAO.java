package model.dao;

import java.util.ArrayList;
import java.util.List;

import model.Company;
import model.Event;
import model.ModelException;

// SQL TABELA TB_EVENTS
////CREATE TABLE IF NOT EXISTS `tb_events` (
//`id` INT(11) AUTO_INCREMENT PRIMARY KEY,
//`name` VARCHAR(128) NOT NULL,
//`location` VARCHAR(128) NOT NULL,
//`description` VARCHAR(128) NOT NULL, 
//`email` VARCHAR(128) NOT NULL,
//`company_id` INT NOT NULL,
//FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)
//);

public class MySQLEventDAO implements EventDAO {

	@Override
	public boolean save(Event event) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlInsert = "INSERT INTO tb_events VALUES (DEFAULT, ?, ?, ?, ?, ?);";

		db.prepareStatement(sqlInsert);

		db.setString(1, event.getName());
		db.setString(2, event.getLocation());
		db.setString(3, event.getDescription());
		db.setString(4, event.getEmail());
		db.setInt(5, event.getCompany().getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean update(Event event) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlUpdate = "UPDATE tb_events SET name = ?, location = ?, description = ?, email = ?, company_id = ? where id = ?";

		db.prepareStatement(sqlUpdate);

		db.setString(1, event.getName());
		db.setString(2, event.getLocation());
		db.setString(3, event.getDescription());
		db.setString(4, event.getEmail());
		db.setInt(5, event.getCompany().getId());
		db.setInt(6, event.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public boolean delete(Event event) throws ModelException {
		DBHandler db = new DBHandler();

		String sqlDelete = "DELETE FROM tb_events WHERE id = ?";

		db.prepareStatement(sqlDelete);

		db.setInt(1, event.getId());

		return db.executeUpdate() > 0;
	}

	@Override
	public Event findById(int id) throws ModelException {
		DBHandler db = new DBHandler();

		String sql = "SELECT * FROM tb_events WHERE id = ?";

		db.prepareStatement(sql);

		db.setInt(1, id);
		db.executeQuery();

		Event event = null;

		while (db.next()) {
			event = createEvent(db);
			break;
		}

		return event;
	}

	@Override
	public List<Event> listAll() throws ModelException {
		DBHandler db = new DBHandler();

		List<Event> events = new ArrayList<>();

		String sqlQuery = "SELECT tb.`id`, tb.`name`, tb.`location`, tb.`description`, tb.`email`, tb.`company_id` FROM `tb_events` AS tb;";

		db.createStatement();

		db.executeQuery(sqlQuery);

		while (db.next()) {
			Event event = createEvent(db);

			events.add(event);
		}

		System.out.println(events);
		return events;
	}

	private Event createEvent(DBHandler db) throws ModelException {
		Event event = new Event(db.getInt("id"));
		event.setName(db.getString("name"));
		event.setLocation(db.getString("location"));
		event.setDescription(db.getString("description"));
		event.setEmail(db.getString("email"));

		CompanyDAO companyDAO = DAOFactory.createDAO(CompanyDAO.class);

		Company company = companyDAO.findById(db.getInt("company_id"));

		event.setCompany(company);

		return event;
	}

}
