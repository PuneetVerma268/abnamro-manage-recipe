package nl.puneet.recipeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nl.puneet.recipeapp.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {

	@Query("SELECT r FROM Recipe r WHERE LOWER(r.name) = LOWER(:name)")
	Recipe findByName(String name);

}
