package nl.puneet.recipeapp.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.puneet.recipeapp.model.Recipe;
import nl.puneet.recipeapp.model.RecipeRequestPayload;
import nl.puneet.recipeapp.service.RecipeService;

@RestController
@RequestMapping(value = "/rest/managerecipe")
@CrossOrigin(origins = "http://localhost:4200/")
public class RecipeController {

	private static final Logger LOGGER = LogManager.getLogger(RecipeController.class);

	@Autowired
	private RecipeService recipeService;

	@PostMapping
	public ResponseEntity<Recipe> addNewRecipe(@Valid @RequestBody RecipeRequestPayload requestPayLoad) {
		LOGGER.info("Received recipe: " + requestPayLoad.toString());
		Recipe _recipe = recipeService.saveRecipe(new Recipe(requestPayLoad.getName(), LocalDateTime.now(),
				requestPayLoad.getVegetarian(), requestPayLoad.getServesPeople(), requestPayLoad.getIngredients(),
				requestPayLoad.getInstructions()));
		try {
			return ResponseEntity.created(new URI("/rest/managerecipe/" + _recipe.getId())).body(_recipe);
		} catch (URISyntaxException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getRecipeById(@PathVariable Long id) {
		return recipeService.findRecipeById(id).map(recipe -> {
			try {
				return ResponseEntity.ok().location(new URI("/rest/managerecipe/" + recipe.getId())).body(recipe);
			} catch (URISyntaxException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<Recipe>> getAllRecipes(@RequestParam(required = false) String name,
			@RequestParam(required = false) Boolean vegetarian, @RequestParam(required = false) Long servesPeople) {
		try {
			if (name == null && vegetarian == null && servesPeople == null) {
				return ResponseEntity.ok().location((new URI("/rest/managerecipe")))
						.body(recipeService.findAllRecipes());
			} else {
				return ResponseEntity.ok().location((new URI("/rest/managerecipe")))
						.body(recipeService.findRecipeWithPredicate(name, vegetarian, servesPeople));
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Recipe> updateRecipe(@PathVariable("id") Long id, @RequestBody RecipeRequestPayload recipe) {
		Optional<Recipe> _recipe = recipeService.findRecipeById(id);

		if (!_recipe.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			_recipe.get().setIngredients(recipe.getIngredients());
			_recipe.get().setInstructions(recipe.getInstructions());
			_recipe.get().setName(recipe.getName());
			_recipe.get().setServesPeople(recipe.getServesPeople());
			_recipe.get().setVegetarian(recipe.getVegetarian());
			Recipe updatedRecipe = recipeService.saveRecipe(_recipe.get());
			try {
				// Return a 200 response with the updated recipe
				return ResponseEntity.ok().location(new URI("/rest/managerecipe/" + updatedRecipe.getId()))
						.body(updatedRecipe);
			} catch (URISyntaxException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteRecipeById(@PathVariable("id") Long id) {
		try {
			recipeService.deleteRecipeById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping
	public ResponseEntity<HttpStatus> deleteAllRecipes() {
		try {
			recipeService.deleteAllRecipes();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
