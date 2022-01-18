package nl.puneet.recipeapp.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;

import nl.puneet.recipeapp.model.Recipe;

@DataJpaTest
public class RecipeRepositoryTest {

	@Autowired
	private RecipeRepository repository;

	@Test
	@Order(4)
	void testFindAllRecipes() {
		List<Recipe> recipes = Lists.newArrayList(repository.findAll());
		Assertions.assertEquals(4, recipes.size(), "Expected 1 recipe in the database");
	}

	@Test
	@Order(3)
	void testFindRecipeById() {
		Optional<Recipe> recipe = repository.findById(1L);
		Assertions.assertNotNull(recipe.get(), "Expected 1 recipe for passed Id in the database");
		Assertions.assertEquals(1, recipe.get().getId().longValue(), "Recipe should have 1 as Id in the database");
	}

	@Test
	@Order(2)
	void testFindByIdNotFound_Success() {
		Optional<Recipe> recipe = repository.findById(8L);
		Assertions.assertFalse(recipe.isPresent(), "A recipe with ID 8 should not be found");
	}

	@Test
	@Order(1)
	void testCreateNewRecipe_Success() {
		Recipe recipeTopost1 = new Recipe(1L, "ImaginaryRecipe1", LocalDateTime.now(), Boolean.TRUE, 3L,
				List.of("imaginary brocalli", "imaginary carrot", "imaginary spinach", "imaginary lattuce"),
				"Build Castle in the air");
		repository.save(recipeTopost1);

		Optional<Recipe> recipe = repository.findById(1L);
		Assertions.assertNotNull(recipe.get());
	}

	@Test
	@Order(5)
	public void testDeleteRecipe() {
		Optional<Recipe> recipeBeforeDeletion = repository.findById(1L);
		Assertions.assertNotNull(recipeBeforeDeletion.get());

		repository.deleteById(1L);

		Optional<Recipe> recipePostDeletion = repository.findById(1L);
		Assertions.assertFalse(recipePostDeletion.isPresent());
	}

	@Test

	@Order(6)
	public void testDeleteAllRecipes() {
		repository.deleteAll();
		Assertions.assertEquals(0, repository.findAll().size());
	}

}
