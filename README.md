# Inventory-Tracker
An Inventory Management tool built using Java


File Overview:
src\InventorySystem

  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\Controller - Contains matching .java and .fxml files that are used to implement the user interface as well as add the functionality to the page. </br>
  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\Model - Contains the means of organizing the parts and products into java objects to be used by the ineventory system. </br>
  
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\Main.java - Main application file. The code sets the primary stage (MainScreen.fxml) then manually adds data to be used in the application. </br>
  
  
  Main Screen </br>
  ![alt text](https://github.com/Aaron-Artz/Inventory-Tracker/blob/main/READMEpictures/MainScreen.PNG?raw=true)
  As the user enteres the program the screen will be populated with products and parts. The parts listed on the left are composed of an ID number, name, inventory level and price. Products on the right are composed of one or more parts. Products will have an ID, name, inventory level, and price. the price will have a minimume based on the price of the parts that make up the product. Users can search for parts or products by their respected ID or names which will limit th4e display on the tables based on the string searched.
  
  Add Part Screen </br>
  ![alt text](https://github.com/Aaron-Artz/Inventory-Tracker/blob/main/READMEpictures/AddPartScreen.PNG?raw=true)
    The add part screen is accessed through the Add button on the parts table of the main screen. From this screen users can select from two radio buttons if the part is created in house or is outsourced. The part field is disabled as the value will be auto generated to avoid duplicate part ID's. After the user fills in the remaining information they can select the cancle button to avoid creating the part, or the save button which will add the part to the parts object and display the part in the table on the main screen.
  
  Modify Part Screen </br>
  ![alt text](https://github.com/Aaron-Artz/Inventory-Tracker/blob/main/READMEpictures/ModifyPartScreen.PNG?raw=true)
    The modify part screen will be similar to the add part screen, it is accessed through selecting a part in the parts table of the main screen and selecting modify. This will auto populate the fields of the modify part screen with the parts information and allow the user to adjust the information in all fields exept for the ID field. The user can then save to update the part object or cancel the transaction.
  
  Add Product Screen </br>
  ![alt text](https://github.com/Aaron-Artz/Inventory-Tracker/blob/main/READMEpictures/AddProductScreen.PNG?raw=true)
    The add product screen will allow users to create a new product object. This will have an auto generated ID like the parts object and will require the price to be at least the minimume of the price of all the selected parts. The top right table is populated will all possible parts to be selected and added to the product object and the bottom right table is populated with the parts currently assigned to the product. users can then add or delete parts from their respective tables. 
  
  Modify Product Screen </br>
  ![alt text](https://github.com/Aaron-Artz/Inventory-Tracker/blob/main/READMEpictures/ModifyProductScreen.PNG?raw=true)
    The modify product screen is the same as the add product screen only it is auto populated with information based on the product selected from the main screen. users can then add or delete parts or adjust the product information.
    
    
  
