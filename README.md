# Trustworthy Product Reviews Application

This project is a Java-based application for managing product reviews, user-following relationships, and categorization of products. The application uses Jakarta Persistence (JPA) to map classes to a relational database.

## Designers
- Matthew Khoury, Student # 101188452  
- Evan Baldwin, Student # 101222276  
- Anabella Villeneuve, Student # 101182327   
- Sebastian Lionais, Student # 101157892

## Project Roadmap

### Milestone 1 

Timeline: Started 2024-11-04, Completed 2024-11-04

Functionality added:
- Github was established with functional Github Actions, Kanban Board, and Azure CI
- Spring configurations, dependencies, and classes added to project repository
- Created backend functionality for Products, Customers, and Reviews
- Added thorough JUnit testing for unit and http tests
- Created html pages for home screen and product page
- Created Product controller and repository
- Designed and implemented an in-memory database schema
- Created documentation of the project including a UML diagram

Objectives for next milestone:
- Select a persistent database and begin implementation with Azure
- Add TestContainer implementation for integration tests
- Add default values to avoid utilizing a 3rd party HTML program
- Add Customer and Review controllers with corresponding CRUD repositories
- Implement new html pages for information such as Customer info and reviews
- Add a Customer account system with login features and associated functionality
- Expand integration testing and add testing for new features

### Milestone 2

Timeline: Started 2024-11-11, Completed 2024-11-25

Functionality added:
- PostgreSQL was chosen as a suitable persistent database
- Implemented PostgreSQL as a remote database with Azure
- Established TestContainer functionality with new integration testing
- Added a functional login system with Customer accounts and passwords
- Added default values for users and products
- Added new html pages including functionality for viewing reviews and account info
- Added new controllers for Reviews and Customers
- Implemented some additional testing for new functionality

Objectives for next milestone:
- Finish review functionality and associated front-end features
- Add additional unit and integration testing for new methods
- Add functions for sorting reviews average rating
- Implement ability to list users by parameters such as most followers, following, and degree of separation
- Improve webpage visuals via functionality such as headers, footers, images, etc.

### Milestone 3

Timeline: Started 2024-11-26, Completed 2024-12-06

Functionality added:
- Review functionality was completed, users can leave persistent reviews
- Added ability to follow other users, an associated follower count, and corresponding html elements to view following
- Implemented buttons for sorting products by default, average star rating, and average star rating of users you follow
- Added sorting mechanisms for users and reviews to sort by defaults, jaccard distance (similarity), and most followers
- Degree of separation calculation is performed and displayed beside all users
- Added many new tests for individual classes, integration, and endpoints (POST)
- Implemented basic users, products, and reviews for testing purposes
- Fixed bugs associated with html pages and backend behaviour
- Completed all associated documentation including the database schema and UML diagram

## Package Structure

- **Customer**: Represents a user who can follow other users and leave reviews on products.
- **Product**: Represents a product available for review.
- **Review**: Represents a review of a product left by a customer, including a rating, text, and date.

## Class Descriptions

### Customer

Represents a user who can follow other users and leave reviews on products.

- **Fields**:
  - `id` (Long): Primary key, auto-generated.
  - `name` (String): Customer's name.
  - `jaccard_index` (double): Represents the jaccard index in relation to the current user of the system. Periodically updated.
  - `follower_count` (int): Follower count of the customer.
  - `email` (String): Unique non-null email utilized for the login credentials.
  - `password` (String): Non-null password associated with the customer login.
  - `following` (Set<Customer>): Set of customers that this customer is following.
  - `reviews` (Set<Review>): Set of reviews this customer has made.
- **Relationships**:
  - Many-to-many relationship with other `Customer` entities through a join table (`user_follows`) to represent following relationships.
  - One-to-many relationship with `Review` entities, where a `Customer` can have multiple reviews.

### Product

Represents a product available for review.

- **Fields**:
  - `id` (Long): Primary key, auto-generated.
  - `name` (String): Name of the product.
  - `category` (String): Product category.
  - `description` (String): Link to the website containing the product.
  - `stars` (String): Average rating of the product on the site for display purposes.
  - `reviews` (List<Review>): List of reviews associated with this product.
- **Relationships**:
  - One-to-many relationship with `Review` entities, where a `Product` can have multiple reviews.


### Review

Represents a review of a product left by a customer, including a rating, text, and date.

- **Fields**:
  - `id` (Long): Primary key, auto-generated.
  - `reviewer` (Customer): The customer who wrote the review.
  - `product` (Product): The product being reviewed.
  - `starRating` (int): The rating given to the product, on a scale (e.g., 1-5).
  - `reviewText` (String): Textual content of the review.
  - `reviewDate` (LocalDateTime): Timestamp of when the review was created.
  - `jaccard_index` (double): Represents the jaccard index in relation to the current user of the system for sorting purposes.
- **Relationships**:
  - Many-to-one relationship with `Customer` (each review has one customer).
  - Many-to-one relationship with `Product` (each review relates to one product).

## Database Schema

This database schema represents a persistent PostgreSQL database utilized to test this proof of concept. This iteration utilizes
a persistent database assocated with Azure that contains data related to products, customers, and reviews. The current iteration 
deletes added elements once a session has been finished for testing and storage purposes. 

Primary keys are represented by an underline, and foreign keys contain an arrow associated with their respective primary keys.

![Milestone3 Database Schema](https://github.com/user-attachments/assets/c5d2d752-9c8d-40f3-a3dd-5e873fb15e61)

## UML Diagram

This UML Diagram represents the application as of Milestone 3. The diagram represents an internal view of the system with arrows 
for class relations. This diagram models the internal functionality of the system and does not include html pages.

![Milestone3 UML Diagram](https://github.com/user-attachments/assets/6f85be45-26ad-4a24-89b2-270c20110dec)

