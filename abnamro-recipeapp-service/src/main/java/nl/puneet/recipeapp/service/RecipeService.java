package nl.puneet.recipeapp.service;

import java.util.List;
import java.util.Optional;

import nl.puneet.recipeapp.model.Recipe;

public interface RecipeService {

	List<Recipe> findRecipeWithPredicate(String name, Boolean isVegetarian, Long ServesPeople);

	Recipe saveRecipe(Recipe recipe);

	Optional<Recipe> findRecipeById(Long id);

	List<Recipe> findAllRecipes();

	void deleteRecipeById(Long id);

	void deleteAllRecipes();

}
