# Read Me

Code Challenge, for details see https://learnin.box.com/s/rj99fkzw62uhiiolnp9wxzc6ff62pnu7

# Code Challenge

### Setup
Create a slack workspace that you can use to do the test so you have full permissions.
https://slack.com/help/articles/206845317-Create-a-Slack-workspace
### Part 2
Important Note: You should not use the Slack SDK for the below work. We want you to create the API calls yourself.
Create a command, form or endpoint (depending on how you’ve chosen to build your application) that selects a random user from the available users in the workspace. It then chooses a random channel from the channels the user is a part of and displays up to 10 messages from that channel (it will display fewer if there are fewer available).
### Part 3
Create a command, form or endpoint (depending on how you’ve chosen to build your application) that when given a single name, uses the Incoming Webhooks feature of Slack to post related names to a channel in your Slack workspace. Your source for the related names will be Behind the Name API.


## Important setup notes:

- A Slack instance is need and the following permissions are needed for setup: users:read, channels:read,groups:read,mpim:read,im:read
- I used the webhook URL "curl -X POST -H 'Content-type: application/json' --data '{"text":"Hello, World!"}' https://hooks.slack.com/services/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"
- Token value is (but will likley need to be updated) xoxb-4442054110403-4435903376998-IFXvgbsaug8Esd5eja6ox29v

### Personal notes and thoughts

- This was an interesting and fun exercise.  Using the Slack integration was frictionless but complicated because of all the features and integration points.  I had some trouble with my personal machine setup as it's kinda old and not ready for development use.  Still I was able to complete all the steps in reasonable time, given more I would make the code more extendable using SOLID like principals.   

### Things that could use some love

- I did not add much in testing, only a few classes to get the development done.  Mock style tests should be added so services do not need to be present on refactors.
- Error handling, we need more done and logging as well
- An azure function might be a good place to post this actual kind of logic, I was going to try that as well.
- This was pretty rough and code could be cleaned up and broken out, but it does work and proves out the API can work for these cases

### To run

- Two ways to run, either:
  - Start the"SlackConsoleApplication" use a web browser to hit localhost:8080
    - Use URL 'http://localhost:8080/postRelatedNames/[NAME]' or 'http://localhost:8080/outputMessages'
  - Or use tests under test class 'SlackControllerTest'

### Video

- I will check in my video to the git repo https://drive.google.com/file/d/1x0CT_Vb1k7WliHh1xy1Ywt0Y8J4baRJs/view?usp=sharing
