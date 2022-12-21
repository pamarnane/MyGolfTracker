# MyGolfTracker MVVM

## Application Description:

MyGolfTracker is an Android application that allows a user to record their scores. Users select the course that they played at as well as date of the round.
An option to add an image per round is available.

The rounds recorded are listed when the user opens the application. From this list view, users can open a map to see the locations that they have rounds stored and they can also open an image gallery to see the images they have stored.

Courses that are available for the user to select are seeded on application startup if required.

This is the second iteration of the MyGolfTracker app. Due to the amount of refactoring to migrate from an MVC to MVVM architecture, this new repository was created.
The original repository is located here: [https://github.com/pamarnane/MyGolfTracker]

Other updates that have been made to the application are:
    - Navigation drawer 
    - Storing of user rounds in Google Firebase Realtime database
    - Storing of user round images in Google Firebase Storage
    - Login in with either email/password or Google account
    - Night mode
    - Swipe on Round List to delete/edit round


## TODO List:
- [ ] Use dialog box for round score entry
- [ ] Do not used stored value for rounds played but generate dynamically
- [ ] Zoom on image in gallery
- [ ] Image gallery allow deletion of image
- [ ] Nav Drawer/Fragment implementation (Create separate branch)
- [ ] Add par for each hole and preset score input for new records to be that value

## External References Used:
### Spinners
- https://www.tutorialspoint.com/how-to-use-arrayadapter-in-android-to-create-a-simple-listview-in-kotlin
- https://developer.android.com/develop/ui/views/components/spinner

### Image Gallery
- https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the

### Splash Screen
- https://www.geeksforgeeks.org/how-to-create-a-splash-screen-in-android-using-kotlin/

### Image Gallery Search/Filter
- https://developer.android.com/reference/kotlin/android/widget/SearchView#getquery
- https://www.geeksforgeeks.org/searchview-in-android-with-kotlin/
- https://www.geeksforgeeks.org/android-searchview-with-recyclerview-using-kotlin/
- https://www.tutorialspoint.com/how-to-use-searchview-in-android-kotlin
