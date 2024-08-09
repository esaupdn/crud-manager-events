package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Event;
import model.Company;
import model.ModelException;
import model.dao.EventDAO;
import model.dao.CompanyDAO;
import model.dao.DAOFactory;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/events", "/event/form", "/event/insert", "/event/update", "/event/delete" })
public class EventController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getRequestURI();

		switch (action) {
		case "/crud-manager/event/form": {
			loadCompanies(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-event.jsp");
			break;
		}
		case "/crud-manager/event/update": {
			loadEvent(req);
			loadCompanies(req);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-event.jsp");
			break;
		}
		default:
			listEvents(req);

			ControllerUtil.transferSessionMessagesToRequest(req);

			ControllerUtil.forward(req, resp, "/events.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String action = req.getRequestURI();

		switch (action) {
		case "/crud-manager/event/insert": {
			insertEvent(req);
			ControllerUtil.redirect(resp, req.getContextPath() + "/events");
			break;
		}
		case "/crud-manager/event/update": {
			updateEvent(req);
			ControllerUtil.redirect(resp, req.getContextPath() + "/events");
			break;
		}
		case "/crud-manager/event/delete": {
			deleteEvent(req);
			ControllerUtil.redirect(resp, req.getContextPath() + "/events");
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	private void deleteEvent(HttpServletRequest req) {
		String eventIdStr = req.getParameter("id");
		int eventId = Integer.parseInt(eventIdStr);

		String eventName = req.getParameter("entityName");

		EventDAO dao = DAOFactory.createDAO(EventDAO.class);

		try {
			if (dao.delete(new Event(eventId))) {
				ControllerUtil.sucessMessage(req, "Evento '" + eventName + "' excluído com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Evento '" + eventName + "' não pode ser excluído.");
			}
		} catch (ModelException e) {
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updateEvent(HttpServletRequest req) {
		String eventIdStr = req.getParameter("event_id");
		int eventId = Integer.parseInt(eventIdStr);

		Event event = createEvent(req, eventId);

		EventDAO dao = DAOFactory.createDAO(EventDAO.class);

		try {
			if (dao.update(event)) {
				ControllerUtil.sucessMessage(req, "Evento " + event.getName() + " alterado com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Evento " + event.getName() + " não pode ser alterado.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void insertEvent(HttpServletRequest req) {
		Event event = createEvent(req, 0);

		EventDAO dao = DAOFactory.createDAO(EventDAO.class);

		try {
			if (dao.save(event)) {
				ControllerUtil.sucessMessage(req, "Evento " + event.getName() + " salvo com sucesso.");
			} else {
				ControllerUtil.errorMessage(req, "Evento " + event.getName() + " não pode ser salvo.");
			}
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private Event createEvent(HttpServletRequest req, int eventId) {
		String eventName = req.getParameter("event_name");
		String eventLocation = req.getParameter("event_location");
		String eventDescription = req.getParameter("event_description");
		String eventEmail = req.getParameter("event_email");
		String eventCompany = req.getParameter("event_company");
		int eventCompanyId = Integer.parseInt(eventCompany);

		Event event;

		if (eventId == 0) {
			event = new Event();
		} else {
			event = new Event(eventId);
		}

		event.setName(eventName);
		event.setLocation(eventLocation);
		event.setDescription(eventDescription);
		event.setEmail(eventEmail);
		event.setCompany(new Company(eventCompanyId));

		return event;
	}

	private void loadEvent(HttpServletRequest req) {
		String eventIdStr = req.getParameter("eventId");
		int eventId = Integer.parseInt(eventIdStr);

		EventDAO dao = DAOFactory.createDAO(EventDAO.class);

		Event event = new Event(0);

		try {
			event = dao.findById(eventId);
		} catch (ModelException e) {
			ControllerUtil.errorMessage(req, "Erro ao carregar Eventos para edição.");
		}

		req.setAttribute("eventToEdit", event);
	}

	private void listEvents(HttpServletRequest req) {
		EventDAO dao = DAOFactory.createDAO(EventDAO.class);

		List<Event> events = new ArrayList<>();

		try {
			events = dao.listAll();
		} catch (ModelException e) {
			System.out.println(events);
			ControllerUtil.errorMessage(req, "Erro ao carregar os Eventos.");
		}

		req.setAttribute("events", events);
	}

	private void loadCompanies(HttpServletRequest req) {
		CompanyDAO dao = DAOFactory.createDAO(CompanyDAO.class);
		List<Company> companies = new ArrayList<>();
		try {
			companies = dao.listAll();
		} catch (ModelException e) {
			ControllerUtil.errorMessage(req, "Erro ao carregar as empresas.");
		}
		req.setAttribute("companies", companies);
	}
}
