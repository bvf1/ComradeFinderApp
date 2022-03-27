# ComradeFinderApp

## Activities
Activities are not connected yet (except for some fragments) and so to test them you‘ll have to edit configurations to specify which activity to run. 

Activites that are runnable:
 - HomeActivity
 - LoginActivity
 - MakeAdvertisementActivity
 - MakeApplicationActivity
 - RegisterActivity

### Activity descriptions
MakeAdvertisementActivity 
  - býr til advertisement

MakeApplicationActivity 
  - býr til application

HomeActivity
  - Tengt við AdsHome og Ad entity
  - Þetta er aðal síðan sem að notandinn verður á eftir loggin
  - Birtir ads (og seinna verður með onclick til að sýna ads details)

LoginActivity
  - Basic login. Virkar fyrir bæði venjuleg Users og Company Users

RegisterActivity
  - Toggle fyrir User og Company User Registration.


### LoginStatusFragment
  - Sést efst á skjánum á MakeAdvertisementActivity, MakeApplicationActivity og HomeActivity
  - Birtir notendanafn og logout takka ef þú ert loggaður inn, annars 'Not logged in' og Login takka.
  - Takkinn er ekki með virkni eins og er.


### Backend

As of right now, our backend from last semester hasn‘t been changed to RESTful and isn‘t being used yet. Here‘s a link to the github anyways: https://github.com/bvf1/ComradeFinderApp/CodeReviewBranch

Some things in our backend needs to be restructured a bit since we can‘t pass a reference to an object in memory through a JSON and instead need to pass an ID or Primary Key.
