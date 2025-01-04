CREATE SCHEMA recipes;

CREATE TABLE recipes.recipes(
    id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title  VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    instructions TEXT NOT NULL,
    minutes INT NOT NULL,
    difficulty VARCHAR(255) NOT NULL,
    vegetarian BOOLEAN NOT NULL,
    servings INT NOT NULL,
    created DATE NOT NULL,
    price_in_cents INT NOT NULL
);

CREATE TABLE recipes.ingredients (
    id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL
);


CREATE TABLE recipes.recipe_ingredient (
    id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    recipe_id INT NOT NULL,
    ingredient_id INT NOT NULL,
    quantity INT NOT NULL,
    unit VARCHAR(50) NOT NULL,
    FOREIGN KEY (recipe_id) REFERENCES recipes.recipes(id) ON DELETE CASCADE,
    FOREIGN KEY (ingredient_id) REFERENCES recipes.ingredients(id) ON DELETE CASCADE
);

INSERT INTO recipes.ingredients (name) VALUES
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

-- Insert recipes
INSERT INTO recipes.recipes (title, description, instructions, minutes, difficulty, vegetarian, servings, created, price_in_cents) VALUES
('Chocolate Cake', 'A rich and moist chocolate cake perfect for any occasion.',
    '1. Preheat oven to 350°F (175°C). 2. Mix dry ingredients. 3. Add wet ingredients and mix. 4. Bake for 30 minutes.',
    45, 'Medium', TRUE, 8, '2025-01-01', 5000),
('Garlic Butter Pasta', 'A quick and easy pasta dish with garlic butter sauce.',
    '1. Boil pasta. 2. Sauté garlic in butter. 3. Toss cooked pasta with garlic butter and season.',
    20, 'Easy', TRUE, 4, '2025-01-02', 2500);

-- Insert recipe ingredients for Chocolate Cake
INSERT INTO recipes.recipe_ingredient (recipe_id, ingredient_id, quantity, unit) VALUES
(1, 1, 200, 'GRAMS'),  -- Sugar
(1, 2, 250, 'GRAMS'),  -- Flour
(1, 3, 3, 'PIECE'),   -- Egg
(1, 4, 100, 'MILLILITER'),     -- Milk
(1, 5, 100, 'GRAMS'),  -- Butter
(1, 7, 1, 'TEASPOON'), -- Baking Powder
(1, 8, 1, 'TEASPOON'), -- Vanilla Extract
(1, 9, 150, 'GRAMS');  -- Chocolate

-- Insert recipe ingredients for Garlic Butter Pasta
INSERT INTO recipes.recipe_ingredient (recipe_id, ingredient_id, quantity, unit) VALUES
(2, 11, 3, 'PIECE'),  -- Garlic
(2, 5, 50, 'GRAMS'),   -- Butter
(2, 10, 30, 'MILLILITER'),     -- Olive Oil
(2, 2, 200, 'GRAMS'),  -- Pasta (Flour)
(2, 6, 1, 'TEASPOON'); -- Salt


CREATE TABLE recipes.cart (
    id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    total_in_cents INT NOT NULL,
    created DATE NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE recipes.cart_recipe (
    id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    recipe_id INT NOT NULL,
    cart_id INT NOT NULL,
    FOREIGN KEY (recipe_id) REFERENCES recipes.recipes(id) ON DELETE CASCADE,
    FOREIGN KEY (cart_id) REFERENCES recipes.cart(id) ON DELETE CASCADE
);

ALTER TABLE recipes.cart_recipe
ADD CONSTRAINT unique_cart_recipe UNIQUE (cart_id, recipe_id);

insert into cart (total_in_cents, created, enabled) values (0, '2025-01-01', true);
