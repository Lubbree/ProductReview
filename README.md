# Trustworthy Product Reviews Application

This project is a Java-based application for managing product reviews, user-following relationships, and categorization of products. The application uses Jakarta Persistence (JPA) to map classes to a relational database.

## Designers
- Matthew Khoury, Student # 101188452  
- Evan Baldwin, Student # 101222276  
- Anabella Villeneuve, Student # 101182327   
- Sebastian Lionais, Student # 101157892



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

## Database Schema

This database schema represents a temporary H2 Database utilized to test this proof of concept. Later iterations
will be switching to a persistent database provider and a new database schema will be created in association with 
the updated database.

Primary keys are represented by an underline, and foreign keys contain an arrow associated with their respective primary keys.

![SYSC4806 Database Schema M1](https://github.com/user-attachments/assets/ec2305fa-821b-4603-9c49-b2aa60672247)


## UML Diagram

This UML Diagram represents the application as of milestone 1. The diagram will be updated periodically to accound for new class's, attributes and methods


![UML drawio](https://github.com/user-attachments/assets/cdcea220-5253-4a85-b820-20ecbdc0eaae)


