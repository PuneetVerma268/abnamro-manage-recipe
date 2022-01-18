package nl.puneet.recipeapp.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.puneet.recipeapp.model.Recipe;
import nl.puneet.recipeapp.model.RecipeRequestPayload;
import nl.puneet.recipeapp.service.RecipeService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RecipeControllerTest {
	ObjectMapper om = new ObjectMapper();
	@MockBean
	RecipeService recipeService;
	Map<String, Recipe> testData;
	@Autowired
	MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before()
	public void setup() {
		// Init MockMvc Object and build
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void whenPostRequestToRecipesAndValidRecipe_thenCorrectResponse() throws Exception {
		// Setup our mocked service
		RecipeRequestPayload recipeToPost = new RecipeRequestPayload("Salad", Boolean.TRUE, 3L,
				List.of("brocalli", "carrot", "spinach", "lattuce"), "Just mix all the veggies");

		Recipe recipeToReturn = new Recipe(1L, "Salad", LocalDateTime.now(), Boolean.TRUE, 3L,
				List.of("brocalli", "carrot", "spinach", "lattuce"), "Just mix all the veggies");
		doReturn(recipeToReturn).when(recipeService).saveRecipe(any());

		// Execute the POST request
		mockMvc.perform(
				post("/rest/managerecipe").contentType(MediaType.APPLICATION_JSON).content(asJsonString(recipeToPost)))

				// Validate the response code and content type
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))

				// Validate header
				.andExpect(header().string(HttpHeaders.LOCATION, "/rest/managerecipe/1"))

				// Validate the returned fields
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Salad")))
				.andExpect(jsonPath("$.vegetarian", is(Boolean.TRUE))).andExpect(jsonPath("$.servesPeople", is(3)))
				.andExpect(jsonPath("$.instructions", is("Just mix all the veggies")))
				.andExpect(jsonPath("$.ingredients", hasSize(4)));
	}

	@Test
	public void whenPostRequestToRecipesAndInValidRecipe_thenCorrectResponse() throws Exception {
		// test type buy
		RecipeRequestPayload recipeToPost = new RecipeRequestPayload(null, null, 3L,
				List.of("brocalli", "carrot", "spinach", "lattuce"), "Just mix all the veggies");

		mockMvc.perform(
				post("/rest/managerecipe").contentType("application/json").content(om.writeValueAsString(recipeToPost)))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	void whenGetRequestByIdAndFound_thenCorrectResponse() throws Exception {
		// Setup our mocked service
		Recipe recipeToReturn = new Recipe(1L, "Salad", LocalDateTime.now(), Boolean.TRUE, 3L,
				List.of("brocalli", "carrot", "spinach", "lattuce"), "Just mix all the veggies");
		doReturn(Optional.of(recipeToReturn)).when(recipeService).findRecipeById(1l);

		// Execute the GET request
		mockMvc.perform(get("/rest/managerecipe/{id}", 1L))
				// Validate the response code and content type
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))

				// Validate headers
				.andExpect(header().string(HttpHeaders.LOCATION, "/rest/managerecipe/1"))

				// Validate the returned fields
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Salad")))
				.andExpect(jsonPath("$.vegetarian", is(Boolean.TRUE))).andExpect(jsonPath("$.servesPeople", is(3)))
				.andExpect(jsonPath("$.instructions", is("Just mix all the veggies")))
				.andExpect(jsonPath("$.ingredients", hasSize(4)));
	}

	@Test
	void whenGetRequestByIdAndNotFound_thenCorrectResponse() throws Exception {
		// Setup our mocked service
		doReturn(Optional.empty()).when(recipeService).findRecipeById(1l);

		// Execute the GET request
		mockMvc.perform(get("/rest/managerecipe/{id}", 1L))
				// Validate the response code
				.andExpect(status().isNotFound());
	}

	@Test
	void whenGetRequestAllRecipeswithoutInputAndFound_thenCorrectResponse() throws Exception {
		// Setup our mocked service
		Recipe salad_veg_3 = new Recipe(1L, "Salad", LocalDateTime.now(), Boolean.TRUE, 3L,
				List.of("brocalli", "carrot", "spinach", "lattuce"), "Just mix all the veggies");

		Recipe biryani_nonveg_5 = new Recipe(2L, "Biryani", LocalDateTime.now(), Boolean.FALSE, 5L,
				List.of("chicken", "rice", "spices"), "cook at slow flame for half an hour");

		doReturn(List.of(salad_veg_3, biryani_nonveg_5)).when(recipeService).findAllRecipes();

		// Execute the GET request
		mockMvc.perform(get("/rest/managerecipe"))
				// Validate the response code and content type
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))

				// Validate the returned fields
				.andExpect(jsonPath("$", hasSize(2)))

				.andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].name", is("Salad")))
				.andExpect(jsonPath("$[0].vegetarian", is(Boolean.TRUE)))
				.andExpect(jsonPath("$[0].servesPeople", is(3)))
				.andExpect(jsonPath("$[0].instructions", is("Just mix all the veggies")))
				.andExpect(jsonPath("$[0].ingredients", hasSize(4)))

				.andExpect(jsonPath("$[1].id", is(2))).andExpect(jsonPath("$[1].name", is("Biryani")))
				.andExpect(jsonPath("$[1].vegetarian", is(Boolean.FALSE)))
				.andExpect(jsonPath("$[1].servesPeople", is(5)))
				.andExpect(jsonPath("$[1].instructions", is("cook at slow flame for half an hour")))
				.andExpect(jsonPath("$[1].ingredients", hasSize(3)));
	}

	@Test
	void whenGetRequestAllRecipeswithInputAndFound_thenCorrectResponse() throws Exception {
		// Setup our mocked service
		Recipe salad_veg_3 = new Recipe(1L, "Salad", LocalDateTime.now(), Boolean.TRUE, 5L,
				List.of("brocalli", "carrot", "spinach", "lattuce"), "Just mix all the veggies");

		doReturn(List.of(salad_veg_3)).when(recipeService).findRecipeWithPredicate(any(), any(), any());

		// Execute the GET request
		mockMvc.perform(get("/rest/managerecipe?name=Salad&vegetarian=true&servesPeople=5"))
				// Validate the response code and content type
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))

				// Validate the returned fields
				.andExpect(jsonPath("$", hasSize(1)))

				.andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].name", is("Salad")))
				.andExpect(jsonPath("$[0].vegetarian", is(Boolean.TRUE)))
				.andExpect(jsonPath("$[0].servesPeople", is(5)))
				.andExpect(jsonPath("$[0].instructions", is("Just mix all the veggies")))
				.andExpect(jsonPath("$[0].ingredients", hasSize(4)));
	}

	@Test
	void whenUpdateRecipeAndSuccess_thenCorrectResponse() throws Exception {
		// Setup our mocked service

		Recipe recipeToReturnFindBy = new Recipe(1L, "Biryani", LocalDateTime.now(), Boolean.TRUE, 5L,
				List.of("chicken", "rice", "spices", "curd"), "cook at slow flame for half an hour");

		Recipe recipeToReturnSave = new Recipe(1L, "Biryani", LocalDateTime.now(), Boolean.FALSE, 6L,
				List.of("chicken", "rice", "spices"), "cook at slow flame for half an hour");

		doReturn(Optional.of(recipeToReturnFindBy)).when(recipeService).findRecipeById(1L);
		doReturn(recipeToReturnSave).when(recipeService).saveRecipe(any());

		// Execute the POST request
		mockMvc.perform(put("/rest/managerecipe/{id}", 1l).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(recipeToReturnSave)))

				// Validate the response code and content type
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))

				// Validate headers
				.andExpect(header().string(HttpHeaders.LOCATION, "/rest/managerecipe/1"))

				// Validate the returned fields
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Biryani")))
				.andExpect(jsonPath("$.vegetarian", is(Boolean.FALSE))).andExpect(jsonPath("$.servesPeople", is(6)))
				.andExpect(jsonPath("$.instructions", is("cook at slow flame for half an hour")))
				.andExpect(jsonPath("$.ingredients", hasSize(3)));
	}

	@Test
	void whenUpdateRecipeAndNotFound_thenCorrectResponse() throws Exception {
		// Setup our mocked service
		Recipe recipeToUpdate = new Recipe(1L, "Biryani", LocalDateTime.now(), Boolean.FALSE, 6L,
				List.of("chicken", "rice", "spices", "curd"), "cook at slow flame for half an hour");
		doReturn(Optional.empty()).when(recipeService).findRecipeById(1L);

		// Execute the POST request
		mockMvc.perform(put("/rest/managerecipe/{id}", 1l).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(recipeToUpdate)))

				// Validate the response code and content type
				.andExpect(status().isNotFound());
	}

	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
