## About
This is simple app that has the following key features:

- It consumes an API for displaying a list of currencies. 
- Each row on the list has an input field where a value can be entered and the app simultaneously updates the corresponding value for other currencies.
- When a currency is clicked that row slides to the top of the list and becomes the first responder.
- The app keeps fetching updated exchange rates every 1 second in the backend.

## App Structure
The app is built completely in Kotlin using Clean Architecture and MVP, with Dagger for dependency injection. 

## Unit Tests
There are 16 Unit Tests for presenters and business logics along with Mockk injections.

## UI Tests
Espresso testing is used to test the UI components. These tests are a bit flaky and fails sometimes if running all at a time.

## Screen

![screenshot_20190227-151813_opt](https://user-images.githubusercontent.com/14851874/53503397-e3f5d880-3aa7-11e9-826c-c1baa546543d.png)
