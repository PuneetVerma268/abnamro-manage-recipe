package nl.puneet.recipeapp.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5429991389283209219L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private LocalDateTime created;
	private Boolean vegetarian;
	private Long servesPeople;
	@ElementCollection
	@CollectionTable(name = "ingredients", joinColumns = @JoinColumn(name = "id"))
	@Column(name = "ingredient")
	private List<String> ingredients;
	private String instructions;

	public Recipe(String name, LocalDateTime created, Boolean vegan, Long servesPeople, List<String> ingredients,
			String instructions) {
		this.name = name;
		this.created = created;
		this.vegetarian = vegan;
		this.servesPeople = servesPeople;
		this.ingredients = ingredients;
		this.instructions = instructions;
	}
}
