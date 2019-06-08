Created by Robert Schiersmann & Keenan Fichtler

# ITAS274_Final_Roller
Final Project for our Android App Development course; features slot machine rolling with Firebase database login and roll history.
Included are Activities for:
    1. intro_page.java; the initial login page 
        - This page connects to, and creates a database on Google Firebase
          - This database holds user credentials, and the roll results for each user.
        - Users log onto the app through here
    2. MainActivity.java; the slot machine function 
        - Users roll for cards here.  Uses switchcases to determine the roll result
    3. history.java; the area to view your previous 10 rolls
        - Users can view their previous 10 rolls here
        - connects to firebase to view the data results of the current user
          - Uses the data results in a switchcase to grab images for the user to see their results
