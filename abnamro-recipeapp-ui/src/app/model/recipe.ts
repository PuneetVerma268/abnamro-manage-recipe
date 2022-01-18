export class Recipe {
  id: string;
  name: string;
  created: Date;
  vegetarian: boolean
  servesPeople: number
  ingredients: string[] = [];
  instructions: string;
}
