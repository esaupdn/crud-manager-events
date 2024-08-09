package model;

public class Event {

	private int id;
	private String name, location, email, description;
	private Company company;

	public Event() {
		this(0);
	}

	public Event(int id) {
		this.id = id;
		setName("");
		setEmail("");
		setLocation("");
		setDescription("");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", name=" + name + ", location=" + location + ", email=" + email + ", description="
				+ description + ", company=" + company + "]";
	}

}
