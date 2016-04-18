# JustEatSearch

## Coding Test

JUST EAT has a public API available at [https://public.je-apis.com/](https://public.je-apis.com/) that you can use to get restaurant information, including restaurant details and delivery information.

As an example, [https://public.je-apis.com/restaurants?q=se19](https://public.je-apis.com/restaurants?q=se19) returns a list of restaurants that deliver to the outcode SE19, including some basic restaurant information.

The API requires you specify a set of valid request headers.

    Accept-Tenant: uk
    Accept-Language: en-GB
    Authorization: Basic VGVjaFRlc3RBUEk6dXNlcjI=
    Host: public.je-apis.com

The task is to create an application that accepts an outcode as a parameter. The application should then display the following information about each restaurant that delivers to that outcode.

- Name
- Rating
- Types of food for the restaurant

### Platform Choice

You can create the application as either a command line application, web application or mobile application in any of the following platforms

- .NET or Ruby for command line applications
- .NET, PHP, Ruby or JavaScript for web applications
- iOS, Andriod or Windows Mobile for mobile applications

Think about the type of work you would like to do at JUST EAT and **choose an appropriate application type and platform**.

### Task requirements

Feel free to spend as much or as little time on the exercise as you like as long as the following requirements have been met.  

- Please complete the user story below.
- Your code should compile and run in one step.
- Feel free to use whatever frameworks / libraries / packages you like.

### User Story

As a **user running the application**  
I can **view a list of restaurants in a user submitted outcode (ex. SE19) **  
So that **I know which restaurants are currently available**

If you have chosen a native mobile application platform please also include the following:

As a **user running the application**  
I can **view the the restaurant logo along side restaurant information**  
So that **I know exactly which restaurants are currently available**

As a **user running the application**  
I can **use GPS to find my current postcode to retrieve restaurant results**  
So that **I dont need to type it in**

#### Acceptance criteria

- For the known outcode se19, results are returned
- The Name, Cuisine Types and Rating of the restaurant are displayed
