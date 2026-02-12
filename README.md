# BilClubs Utilities

This is a repository for storing utilities and snippets that will be used later in the project.

---

### LoginVerifier
`LoginVerifier.verify(username, password)`: returns a boolean.

`username`: Student email address (<...>@<...>.bilkent.edu.tr)

`password`: Password for the email address

VERY IMPORTANT NOTE: This class' intended use is on the server-side. We can't just expect the client-side to perform the check by themselves. Any bad actor can memory-edit the program or recompile with custom verification logic to bypass this. Therefore it is mandatory that we do the verification on server-side and then, at the very least, return a login token and a user ID to the client-side. These credentials will be provided in each request to the server and the server will lookup the user ID entry in our own database to see if the token matches. We can preserve the login state (on client-side) but there should be an option to logout and the server should generate a new token on every login/logout request (in case the token is compromised, a new login with the credentials will invalidate the old authentication token).
