# MyGolfTracker MVVM

## Application Description:

MyGolfTracker is an Android application that allows a user to record their scores. Users select the course that they played at as well as date of the round.
An option to add an image per round is available.

The rounds recorded are listed when the user opens the application. From this list view, users can open a map to see the locations that they have rounds stored and they can also open an image gallery to see the images they have stored.

Courses that are available for the user to select are seeded on application startup if required.

This is the second iteration of the MyGolfTracker app. Due to the amount of refactoring to migrate from an MVC to MVVM architecture, this new branch was created.
The original application is still present as the master branch, and is located here: [https://github.com/pamarnane/MyGolfTracker]

Other updates that have been made to the application are:
    - Navigation drawer 
    - Storing of user rounds in Google Firebase Realtime database
    - Storing of user round images in Google Firebase Storage
    - Login in with either email/password or Google account
    - Night mode
    - Swipe on Round List to delete/edit round


## TODO List
This has been carried forward from the MVC version of the application.
Due to the amouint of time taken to refactor the application, only a few items were implemnted.
- [ ] Use dialog box for round score entry
- [X] Do not used stored value for rounds played but generate dynamically
- [ ] Zoom on image in gallery
- [ ] Image gallery allow deletion of image
- [X] Nav Drawer/Fragment implementation (Create separate branch)
- [ ] Add par for each hole and preset score input for new records to be that value

## UML & Class Diagram
![MyGolfTracker UML Diagram](https://github.com/pamarnane/MyGolfTrackerMVVM/blob/master/MyGolfTrackerUML.png)

## UX/DX Approach
The main focus of the UX/DX of this was towards simplicity and using the standard Android Material UI elements. This helped keep a consistent approach while allowing focus to be more on the MVVM Design pattern and integration of Firebase services.

## Git Approach
Methodolgy applied to the use of Git and GitHub was dictated by the workflow and targets for the project. I had targeted features and worked through those in a linear fashion which is reflected in the commit history


## Personal Statement
The reasoning to create MyGolfTracker was down to my playing of golf. 

If you are a member of a golf club here in Ireland, you are a member of Golf Ireland who have a mobile application that stores your rounds that are recorded for handicap purposes. Some golf courses also provide other systems which enable this feature, but what happens if you are not a member of a golf course and play casually and consistently and would like to record rounds.

There are a large number of applications available, but these are either Paid or if free, come with a large number of ads. Hence the creation of MyGolfTracker.

The first iteration of MyGolfTracker so the basic functionlaity of creating, updating and deleting rounds as well as the ability to add images for them. As can be seen the ToDo list above, I felt that I could improve on a number of the UX/DX elements.

However, after exposure to the MVVM (Model-View-ViewModel) Design Pattern, I made the decision to not focus some of the nice to have features recorded in that list for the second iteration. Focus was now moved towards the back-end and getting an understanding of MVVM patter and it's use of LiveData. 

This would also benefit me with a current project that I am working on with my employer,  a Windows Desktop WPF(Windows Presentation Foundation) which implements the MVVM pattern.


## External References Used:
### Spinners
- https://www.tutorialspoint.com/how-to-use-arrayadapter-in-android-to-create-a-simple-listview-in-kotlin
- https://developer.android.com/develop/ui/views/components/spinner

### Image Gallery
- https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the

### Creation of Google Maps Fragment
- [https://www.youtube.com/watch?v=ZAOfYRY8zJk]

### Dark Mode Button
- [https://github.com/manuelernesto/darkmode_app_with_kotlin]
