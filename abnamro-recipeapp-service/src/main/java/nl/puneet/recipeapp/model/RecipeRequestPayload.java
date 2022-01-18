package nl.puneet.recipeapp.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequestPayload {

	@NotBlank(message = "Name is mandatory")
	private String name;
	@NotNull(message = "Type of Recipe is mandatory")
	private Boolean vegetarian;
	private Long servesPeople;
	private List<String> ingredients;
	private String instructions;
}
