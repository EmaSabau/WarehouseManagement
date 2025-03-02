---
### TABLE OF CONTENT

1. Assignment Objective   
2. Problem Analysis, Modeling, Scenarios, Use Cases 
3. Design  
4. Implementation   
5. Results  
6. Conclusions 
7. Bibliography  

---

### 1. Assignment Objective

1.1. Main Objective  
The task was to design and implement an application for managing customer orders in a warehouse.

1.2. Secondary Objective
- Analyzed the problem and identified the requirements
- Designed the order management application
- Implemented the order management application
- Tested the order management application

---

### 2. Problem Analysis, Modeling, Scenarios, Use Cases

2.1. Functional Requirements  
The application should allow an employee to add a new customer.  
The application should allow an employee to add a new product.  
The application should allow an employee to create an order by selecting the customer and product the customer wishes to purchase.

2.2. Non-Functional Requirements  
The application should be intuitive and easy to use for the user.  
The application should help streamline the database.

---

### 3. Design

I used a layered architecture, which will be further developed in section 4.

Once the program runs, three windows will open: one for Clients, one for Products, and one for Orders.

![Screenshot 2025-03-02 130640](https://github.com/user-attachments/assets/3aceb134-a1b1-4543-b1cd-a397d9813874)


If we want to add a new product to the database, a new window will appear where we can fill in the fields for the new product.

![Screenshot 2025-03-02 130646](https://github.com/user-attachments/assets/140a34ea-eeb6-4a4c-81fc-015ae2579b78)


In the Orders window, an order can be made by selecting the client and the product the client wishes to purchase. If the selected quantity is no longer in stock, an error will appear; otherwise, the stock quantity will be decremented.

![Screenshot 2025-03-02 130652](https://github.com/user-attachments/assets/29b06a31-c5d2-4dec-8f41-700486dff4cc)

---

### 4. Implementation

4.1. BUSINESS LOGIC LAYER

Classes: ClientBLL, ProductBLL, OrderBLL, validators.  
These classes are used for the logic behind the operations performed in the application: add, edit, and delete. The validators interface is used for validating certain fields, such as ensuring the correct format of an email address or phone number.

4.2. DATA ACCESS

Classes: ClientDAO, ProductDAO, OrderDAO, ConnectionFactory  
This allows access to the database. The ConnectionFactory class manages the connection to the database, while the other classes access data from the tables in the database.

4.3. MODEL Classes: Client, Product, Order

These classes define the structure of the client, product, and order.

4.4. PRESENTATION Classes: View, Controller

These classes are used for creating the graphical interface and its functionality.

4.5. START  
Classes: Start, Reflection  
The Reflection class uses reflection techniques to create a method that receives a list of objects and generates the table header by extracting, through reflection, the properties of the object, and then populates the table with the values of the elements from the list.

---

### 5. Conclusions

The conclusions will present what was learned from the assignment, including possible future developments.

---

### 6. Bibliography

1. Simple layered project - gitlab.com  
2. Reflection in Java - jenkov.com/tutorials  
3. SQL dump file generation - dev.mysql.com  
4. Javadoc - [http://www.baeldung.com/javadoc](http://www.baeldung.com/javadoc)

---

