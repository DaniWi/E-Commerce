package data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Item")
public class Item {

	@Id
	@GeneratedValue
	private int id;
	@Lob
	private String title;
	@Lob
	private String description;
	@Lob
	private String author;
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDaten = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	private Date altertionDate = new Date();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getCreationDaten() {
		return creationDaten;
	}

	public void setCreationDaten(Date creationDaten) {
		this.creationDaten = creationDaten;
	}

	public Date getAltertionDate() {
		return altertionDate;
	}

	public void setAltertionDate(Date altertionDate) {
		this.altertionDate = altertionDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}