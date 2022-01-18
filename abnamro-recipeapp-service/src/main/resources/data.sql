---------------------------1--------------------------------------------------
insert into recipe (created, instructions, name, serves_people, vegetarian, id) 
    values (LOCALTIME(9), 'cook slowly forever', 'BIRYANI', 4,FALSE, 1);	
insert into ingredients (id, ingredient)  values (1, 'chicken');
insert into ingredients (id, ingredient)  values (1, 'rice');	
insert into ingredients (id, ingredient)  values (1, 'spices');	
insert into ingredients (id, ingredient)  values (1, 'curd');

---------------------------2--------------------------------------------------
insert into recipe (created, instructions, name, serves_people, vegetarian, id) 
    values (LOCALTIME(9), 'grill the buns, put chicekn patty and serve', 'BURGER', 4,FALSE, 2);
	
insert into ingredients (id, ingredient)  values (2, 'bun');
insert into ingredients (id, ingredient)  values (2, 'patty');	
---------------------------3--------------------------------------------------
insert into recipe (created, instructions, name, serves_people, vegetarian, id) 
    values (LOCALTIME(9), 'put lots of veggies', 'SALAD', 4,TRUE, 3);
	
insert into ingredients (id, ingredient)  values (3, 'brocalli');
insert into ingredients (id, ingredient)  values (3, 'carrot');
insert into ingredients (id, ingredient)  values (3, 'lattuce');	
insert into ingredients (id, ingredient)  values (3, 'spinach');
---------------------------4--------------------------------------------------	
insert into recipe (created, instructions, name, serves_people, vegetarian, id) 
    values (LOCALTIME(9), 'fry in pan with salt and pepper', 'OMELETTE', 5,TRUE, 4);
	
insert into ingredients (id, ingredient)  values (4, 'egg');
insert into ingredients (id, ingredient)  values (4, 'onion');	
insert into ingredients (id, ingredient)  values (4, 'pepper');	
insert into ingredients (id, ingredient)  values (4, 'spinach');