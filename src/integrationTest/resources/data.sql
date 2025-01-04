INSERT INTO ingredients (name) VALUES
('Sugar'),
('Flour'),
('Egg'),
('Milk'),
('Butter'),
('Salt'),
('Baking Powder'),
('Vanilla Extract'),
('Chocolate'),
('Olive Oil'),
('Garlic');

INSERT INTO recipes (title, description, instructions, minutes, difficulty, vegetarian, servings, created, price_in_cents) VALUES
('Chocolate Cake', 'A rich and moist chocolate cake perfect for any occasion.',
    '1. Preheat oven to 350°F (175°C). 2. Mix dry ingredients. 3. Add wet ingredients and mix. 4. Bake for 30 minutes.',
    45, 'Medium', TRUE, 8, '2025-01-01', 5000),
('Garlic Butter Pasta', 'A quick and easy pasta dish with garlic butter sauce.',
    '1. Boil pasta. 2. Sauté garlic in butter. 3. Toss cooked pasta with garlic butter and season.',
    20, 'Easy', TRUE, 4, '2025-01-02', 2500);

INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity, unit) VALUES
(1, 1, 200, 'GRAMS'),
(1, 2, 250, 'GRAMS'),
(1, 3, 3, 'PIECE'),
(1, 4, 100, 'MILLILITER'),
(1, 5, 100, 'GRAMS'),
(1, 7, 1, 'TEASPOON'),
(1, 8, 1, 'TEASPOON'),
(1, 9, 150, 'GRAMS');

INSERT INTO recipe_ingredient (recipe_id, ingredient_id, quantity, unit) VALUES
(2, 11, 3, 'PIECE'),
(2, 5, 50, 'GRAMS'),
(2, 10, 30, 'MILLILITER'),
(2, 2, 200, 'GRAMS'),
(2, 6, 1, 'TEASPOON');

INSERT INTO cart (total_in_cents, created, enabled) VALUES (0, '2025-01-01', TRUE);