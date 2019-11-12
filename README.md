# HyTech-Logger - 11/11/2019

This application was written in Andriod Studio for Java.
The application was created for logging Georgia Tech HyTech Racing cells intially.
The HyTech Logger has been expanded to log anything the team needs.

To add a new item type to be logged, create a new subclass of the abstract ItemType class which represents your new item type.
This can be done easily because item types are built using combinations of Attributes and LocationConfigurations
Look at an existing ItemType to understand how this works.

You can mix and match existing Attributes and LocationConfigurations to make your item, or create new ones.
If you need a new Attribute or Locationconfiguration, you can declare it in the Attributes or LocationConfigurations class.
This is easy as well, each new Attribute or LocationConfiguration should be one line of code.
If you want a whole new type of Location you will have to create a new Location class to represent it.
This is slightly more work, but existing Locations should offer a good model for how to do this.

Once you have created your new ItemType, add it to ACTIVE_ITEMTYPES in GlobalVariables.
This will automatically add it to the app.
Additionally, you can easily modify existing ItemTypes, Attributes, LocationConfigurations and Locations and the app will update.

The application is currently set to use a default branch of the database, so you can use it freely without risk of contaminating the team's data, or you can change your database branch from the profile view, which can be reached by clicking the profile image button on the main view. If your branch to WUNDERMAN_DEMO you can see some example entries I have created.

If you have any intrest in what the app used to be like, that is stored in the old branch of the repository.
