# README

Homework (2p)

Create a Spring Boot Order Management System that handles Customers, Orders, and Discounts. Different clients (companies using your system) require different discount policies:

✅ - Loyal customers get a percentage discount;  
✅ - Orders with a large value get a fixed amount discount;  
✅ - No discount (default).  

Design a pluggable discount mechanism where Spring selects the appropriate DiscountService implementation depending on the client.  
✅ Define the domain model: Customer, Order, DiscountService and a simple in-memory repository of customers.  
✅ Create the implementations for the discount policies.  
✅ Use Spring Profiles or a custom configuration property to decide which discount strategy to inject.  
✅ Create the OrderService which applies discounts using the injected DiscountService.  
✅ Every time a discount is applied, log the method name, customer name, and discount amount.  
✅ Create a decorator (using aspects) that, before applying a discount, checks if the customer exists and is eligible to get a discount. If not, throw a custom exception.  
✅ Publish an event whenever a discount exceeds a certain amount and implement a listener for that type of event.