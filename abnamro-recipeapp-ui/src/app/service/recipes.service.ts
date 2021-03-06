import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Recipe} from "../model/recipe";

@Injectable({providedIn: 'root'})
export class RecipesService {

  private url = environment.api + environment.resourceRecipes

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa('spring:spring')
    })  
  }

  constructor(private http: HttpClient) {
  }

  getRecipes(): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(this.url,this.httpOptions)
  }

  getRecipe(id: string): Observable<Recipe> {
    return this.http.get<Recipe>(`${this.url}/${id}`,this.httpOptions)
  }

  createRecipe(recipe: Recipe): Observable<Recipe> {
    return this.http.post<Recipe>(this.url, recipe, this.httpOptions)
  }

  updateRecipe(recipe: Recipe): Observable<Recipe> {
    return this.http.put<Recipe>(`${this.url}/${recipe.id}`, recipe, this.httpOptions)
  }

  deleteRecipe(id: string): Observable<Recipe> {
    return this.http.delete<Recipe>(`${this.url}/${id}`,this.httpOptions)
  }

}
