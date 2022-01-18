package nl.puneet.recipeapp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.puneet.recipeapp.constant.SearchOperation;
import nl.puneet.recipeapp.model.Recipe;
import nl.puneet.recipeapp.repository.RecipeRepository;
import nl.puneet.recipeapp.repository.criteria.GenericSpesification;
import nl.puneet.recipeapp.repository.criteria.RecipeSearchCriteria;
import nl.puneet.recipeapp.service.RecipeService;

@Service
public class RecipeServiceImpl implements RecipeService {

	private static final String NAME = "name";
	private static final String ISVEGETARION = "vegetarian";
	private static final String SERVESPEOPLE = "servesPeople";
	private static final String INGREDIENTS = "ingredients";

	@Autowired
	private RecipeRepository recipeRepository;

	@Override
	public Recipe saveRecipe(Recipe recipe) {
		return recipeRepository.save(recipe);
	}

	@Override
	public Optional<Recipe> findRecipeById(Long id) {
		return recipeRepository.findById(id);

	}

	@Override
	public List<Recipe> findAllRecipes() {
		return recipeRepository.findAll();
	}

	@Override
	public void deleteRecipeById(Long id) {
		recipeRepository.deleteById(id);

	}

	@Override
	public void deleteAllRecipes() {
		recipeRepository.deleteAll();
	}

	@Override
	public List<Recipe> findRecipeWithPredicate(String name, Boolean isVegetarian,
			Long ServesPeople/*
								 * , String ingredients
								 */) {
		GenericSpesification<Recipe> genericSpesification = new GenericSpesification<Recipe>();
		if (name != null && name.length() > 0)
			genericSpesification.add(new RecipeSearchCriteria(NAME, name, SearchOperation.MATCH));
		if (null != isVegetarian)
			genericSpesification.add(new RecipeSearchCriteria(ISVEGETARION, isVegetarian, SearchOperation.EQUAL));
		if (null != ServesPeople)
			genericSpesification
					.add(new RecipeSearchCriteria(SERVESPEOPLE, ServesPeople, SearchOperation.GREATER_THAN_EQUAL));
		/*
		 * if (null != ingredients && ingredients.length() > 0)
		 * genericSpesification.add(new RecipeSearchCriteria(INGREDIENTS, ingredients,
		 * SearchOperation.MATCH));
		 */
		return recipeRepository.findAll(genericSpesification);
	}
}
