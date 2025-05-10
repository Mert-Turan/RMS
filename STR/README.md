# STR – Login Test (Without GUI)

This folder contains test files created for the STR login functionality test.

## Why this test exists?

While testing the login screen in the real GUI, an issue occurred and the login could not be completed.  
Because of this, we created a simple way to test the login process *without GUI**, using printed messages in the console.

This test is used for functional checking of STR test case **T-SRS-REQ-001**.

## Files

- **ILoginView.java**  
  → An interface that allows testing without using the real GUI

- **FakeLoginView.java**  
  → A class that shows login messages in the console

- **LoginTest.java**  
  → The main test file. It runs 3 different login scenarios:
    1. Entering userID
    2. Login with correct credentials
    3. Login with wrong credentials
## Note

This method is not a full unit test or JUnit test.  
It is a quick and simple way to simulate login behavior when GUI cannot be used during testing.
