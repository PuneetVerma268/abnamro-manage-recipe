package nl.puneet.recipeapp.repository.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import nl.puneet.recipeapp.constant.SearchOperation;

@AllArgsConstructor
@Data
public class RecipeSearchCriteria {
	private String key;
	private Object value;
	private SearchOperation operation;

}
