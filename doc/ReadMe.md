# Advanced Software Engineering Exam Project

student number: 5622763  
student name: Philipp Gehrig  
hand-in date: 2024-12-06

## Documentation

For the source code documentation of the project, please refer to the javadoc documentation in the `doc` folder.

## Running the project

To run the project make sure you have the correct libraries and dependencies installed:

- Java 11
- Junit 5.8.1
- JavaFX

## Importing Parameters

To import parameters for the calculator please use the following format in the `shippingCosts.csv` file:

```
length; width; height; weight; girth; price
```

The first line in the `shippingCosts.csv` also contains this formatting guide. 
Therefore, this line will be disregarded by the program. Should one of the parameters not have a girth requirement,
please leave the girth field empty.

```
10;10;10;10;;10
```

The program will then disregard any girth requirements for this specific entry.
