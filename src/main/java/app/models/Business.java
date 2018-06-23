package app.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Business {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int DBid;
	
	// The id from the yelp object
	private String id;
	
	private String name;
	
	private String image_url;
	
	private String url;
	
	private Integer rating;
	
	private String price;
	
	private String display_price;
	
	@OneToMany(mappedBy="business")
	private List<Photo> photos;

	public int getDBid() {
		return DBid;
	}

	public void setDBid(int dBid) {
		DBid = dBid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDisplay_price() {
		return display_price;
	}

	public void setDisplay_price(String display_price) {
		this.display_price = display_price;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
}
	