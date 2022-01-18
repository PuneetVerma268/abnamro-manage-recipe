The ABNAmro Manage Recipe application has two services as below. The app has been docker containerized and can be executed by single command. 

1. abnamro-recipeapp-service : 
	Its Spring boot application that exposes REST API in order to manage Recipes. Application has been tested by Unit tests and integration tests.  
		Technology Stack
			-Java 8
			-Maven 3.8.1
			-Spring boot
			-Spring Data 
			-Spring Security
			-Junit5
			-Mockito
			-H2 inmemory DB
			-Docker 20.10.6
			-open API
			-Swagger - API Documentation 
				- http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/
	  				 APIs are secured by Spring Security and OpenAPI that ensures that only authenticated users can call endpoints available through Swagger UI and clients. 
	   				Cred: spring/spring to Authorize the swagger(UI already handled) in order to hit request. 

		REST API: 
			- addNewRecipe  - to create a New Recipe. 
			- getRecipeById - seach Recipe by recipe Id.
			- getAllRecipes - Search All recipes by name, vegetarian/non-vegetarian flag, and number of serves people.
			- updateRecipe - update any recipe by Id. 
			- deleteRecipe - delete recipe by Id
			- deleteAllRecipes  - Delete all Recipes. 


2. 	abnamro-recipeapp-UI : 
	Its a single web page application that consumes abnamro-recipeapp-service in order to operation on recipes from web application. 
		Technology Stack
			-Angular 13
			-Docker 20.10.6
		
		
HOW TO RUN..
1. extract the zip file in local. 
2. Go to Extracted folder location --> abnamro-recipeapp-service
3. run --> mvn clean install (To generate Spring boot services binaries and execute test) -OPTIONAL
4. Go to Extracted folder location (where docker-compose file is present) and execute command "docker-compose up --build"
5. go to Browser and hit http://localhost:4200/ With Title (ABN AMRO Recipes)
6. As a part of start 4 recipes are already loaded in the application. Bon Appetit :) 
7. Perfomrm UI operations to invode Spring Boot REST API.(Security settings are already added in the HEADER to invoce secured API.)		