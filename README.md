# Trustworthy Product Reviews Application

This project is a Java-based application for managing product reviews, user-following relationships, and categorization of products. The application uses Jakarta Persistence (JPA) to map classes to a relational database.

## Designers
-Matthew Khoury, 101188452
-Evan Baldwin,
-Anabella, Villeneuve,
-Sebastian, Lionais,




## Package Structure

- **Customer**: Represents a user who can follow other users and leave reviews on products.
- **Product**: Represents a product available for review.
- **Review**: Represents a review of a product left by a customer, including a rating, text, and date.

## Class Descriptions

### Customer

Represents a user who can follow other users and leave reviews on products.

- **Fields**:
  - `id` (Long): Primary key, auto-generated.
  - `userId` (String): Unique identifier for each user.
  - `name` (String): User's name.
  - `following` (Set<Customer>): Set of customers that this customer is following.
  - `reviews` (Set<Review>): Set of reviews this customer has made.
- **Relationships**:
  - Many-to-many relationship with other `Customer` entities through a join table (`user_follows`) to represent following relationships.
  - One-to-many relationship with `Review` entities, where a `Customer` can have multiple reviews.
- **Methods**:
  - `addFollowing(Customer customer)`: Adds a customer to the following set.
  - `removeFollowing(Customer customer)`: Removes a customer from the following set.
  - `addReview(Review review)`: Associates a review with the customer.
  - `removeReview(Review review)`: Disassociates a review from the customer.

### Product

Represents a product available for review.

- **Fields**:
  - `id` (Long): Primary key, auto-generated.
  - `productId` (String): Unique identifier for each product.
  - `name` (String): Name of the product.
  - `category` (String): Product category.
  - `reviews` (List<Review>): List of reviews associated with this product.
- **Relationships**:
  - One-to-many relationship with `Review` entities, where a `Product` can have multiple reviews.
- **Methods**:
  - `addReview(Review review)`: Associates a review with the product.
  - `removeReview(Review review)`: Disassociates a review from the product.

### Review

Represents a review of a product left by a customer, including a rating, text, and date.

- **Fields**:
  - `id` (Long): Primary key, auto-generated.
  - `reviewer` (Customer): The customer who wrote the review.
  - `product` (Product): The product being reviewed.
  - `starRating` (int): The rating given to the product, on a scale (e.g., 1-5).
  - `reviewText` (String): Textual content of the review.
  - `reviewDate` (LocalDateTime): Timestamp of when the review was created.
- **Relationships**:
  - Many-to-one relationship with `Customer` (each review has one customer).
  - Many-to-one relationship with `Product` (each review relates to one product).
