package nl.puneet.recipeapp.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import nl.puneet.recipeapp.model.Recipe;
import nl.puneet.recipeapp.repository.RecipeRepository;

@SpringBootTest
public class RecipeServiceTest {
	/**
	 * Autowire in the service we want to test
	 */
	@Autowired
	private RecipeService service;

	/**
	 * Create a mock implementation of the RecipeRepository
	 */
	@MockBean
	private RecipeRepository repository;

	@Test
	void testFindRecipeById() {
		// Setup our mock repository
		Recipe recipeToReturn = new Recipe(1L, "Salad", LocalDateTime.now(), Boolean.TRUE, 3L,
				List.of("brocalli", "carrot", "spinach", "lattuce"), "Just mix all the veggies");
		doReturn(Optional.of(recipeToReturn)).when(repository).findById(1L);

		// Execute the service call
		Optional<Recipe> returnedRecipe = service.findRecipeById(1l);

		// Assert the response
		Assertions.assertTrue(returnedRecipe.isPresent(), "Recipe was not found");
		Assertions.assertSame(returnedRecipe.get(), recipeToReturn, "Recipe returned was not the same as the mock");
	}

	@Test
	void testFindRecipeByIdNotFound() {
		// Setup our mock repository
		doReturn(Optional.empty()).when(repository).findById(1l);

		// Execute the service call
		Optional<Recipe> returnedRecipe = service.findRecipeById(1l);

		// Assert the response
		Assertions.assertFalse(returnedRecipe.isPresent(), "Recipe should not be found");
	}

	@Test
	void testFindAllRecipes() {
		// Setup our mock repository
		Recipe recipe1 = new Recipe(1L, "Salad", LocalDateTime.now(), Boolean.TRUE, 3L,
				List.of("brocalli", "carrot", "spinach", "lattuce"), "Just mix all the veggies");
		Recipe recipe2 = new Recipe(2L, "Biryani", LocalDateTime.now(), Boolean.FALSE, 5L,
				List.of("chicken", "rice", "spices"), "cook at slow flame for half an hour");
		doReturn(Arrays.asList(recipe1, recipe2)).when(repository).findAll();

		// Execute the service call
		List<Recipe> recipes = service.findAllRecipes();

		// Assert the response
		Assertions.assertEquals(2, recipes.size(), "findAll should return 2 Recipes");
	}

	@Test
	void testCreateRecipes() {
		// Setup our mock repository
		Recipe recipe = new Recipe(1L, "Salad", LocalDateTime.now(), Boolean.TRUE, 3L,
				List.of("brocalli", "carrot", "spinach", "lattuce"), "Just mix all the veggies");
		doReturn(recipe).when(repository).save(any());

		// Execute the service call
		Recipe returnedRecipe = service.saveRecipe(recipe);

		// Assert the response
		Assertions.assertNotNull(returnedRecipe, "The saved recipe should not be null");

	}

}
