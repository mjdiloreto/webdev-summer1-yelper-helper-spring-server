package app.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Photo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String src;
	
	private Integer likes;
	
	private Integer dislikes;
	
	@ManyToOne
	@JsonIgnore
	private Business business;
	
	@ManyToMany(mappedBy="likedPhotos")
	List<User> usersWhoLike;
	
//	@OneToMany
//	private Likes;
//	
//	@OneToMany
//	private Dislikes;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Integer getDislikes() {
		return dislikes;
	}

	public void setDislikes(Integer dislikes) {
		this.dislikes = dislikes;
	}

	public Business getBusiness() {
		return business;
	}

	public void setBusiness(Business business) {
		this.business = business;
	}

	public List<User> getUsersWhoLike() {
		return usersWhoLike;
	}

	public void setUsersWhoLike(List<User> usersWhoLike) {
		this.usersWhoLike = usersWhoLike;
	}	
}
