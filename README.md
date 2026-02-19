# BilClubs Utilities

This is a repository for storing utilities and snippets that will be used later in the project.

---

## Login Utilities

### `LoginVerifier`
Handles server side authentication for student accounts.

#### Constructors & Methods
| Element | Description |
| :--- | :--- |
| `verify(String username, String password)` | Returns a `boolean` indicating whether the provided student credentials are valid |

#### Parameters
| Parameter | Context | Description |
| :--- | :--- | :--- |
| `username` | `verify` method | The student email address (`___@ug.bilkent.edu.tr`) |
| `password` | `verify` method | The password associated with the provided email address |



**VERY IMPORTANT NOTE:** This class' intended use is strictly on the server side. We cannot expect the client side to perform the authentication check, as bad actors could memory edit the program or recompile it with custom logic to bypass verification. Therefore, it is mandatory to execute this verification on the server side. Upon successful login, the server must return a login token and a user ID to the client side. These credentials will be provided in subsequent requests, allowing the server to look up the user ID in the database and validate the token. While login state can be preserved on the client side, there must be a logout option. The server should generate a new token on every login/logout request so that if a token is compromised, a new login will invalidate the old authentication token.

---

<br>

## Email Utilities

### `Credentials`
The `Credentials` class is a simple data container for storing authentication details.

#### Constructors & Methods
| Element | Description |
| :--- | :--- |
| `Credentials(String username, String password)` | Instantiates the credentials object for authentication |
| `Credentials(String filename)` | Placeholder constructor for now, intended for future `.env` file implementation |
| `getUsername()` | Returns a `String` representing the stored username |
| `getPassword()` | Returns a `String` representing the stored password |

#### Parameters
| Parameter | Context | Description |
| :--- | :--- | :--- |
| `username` | `Credentials` constructor | The email address which the notifications will be sent from |
| `password` | `Credentials` constructor | The app password for the email address |
| `filename` | `Credentials` constructor | The path to the environment file containing the credentials |

<br>
<br>

### `HTMLTemplate`
Loads and formats HTML files for dynamic formatting.

#### Constructors & Methods
| Element | Description |
| :--- | :--- |
| `HTMLTemplate(String fileName)` | Loads an HTML file and stores its content as a string for formatting |
| `format(String key, String value)` | Mutates the template object by injecting the value wherever the `{{ key }}` placeholder is found |
| `format(HashMap<String, String> formatMap)` | Mutates the template object by replacing multiple placeholders based on the provided map |
| `formatted(String key, String value)` | Returns a cloned `HTMLTemplate` with the variable formatted, leaving the original template unchanged |
| `formatted(HashMap<String, String> formatMap)`| Returns a cloned `HTMLTemplate` with the map variables formatted in, leaving the original one unchanged |
| `toString()` | Returns the raw or formatted HTML content as a `String`. |

#### Parameters
| Parameter | Context | Description |
| :--- | :--- | :--- |
| `fileName` | `HTMLTemplate` constructor | The path to the target HTML template file |
| `key` | `format`, `formatted` methods | The string placeholder variable name (without the curly brackets) to be replaced in the HTML |
| `value` | `format`, `formatted` methods | The string content to inject into the template |
| `formatMap` | `format`, `formatted` methods | A `HashMap` containing multiple key and value pairs for bulk formatting |

<br>

### `MailMessage`
This class acts as a payload containing the content, subject, and recipients of the composed email.

#### Constructors & Methods
| Element | Description |
| :--- | :--- |
| `setContent(String content)` | Sets the plain text content of the email, should be used if the email is not templated |
| `setSubject(String subject)` | Sets the subject/title of the email |
| `useHTML()` | Flags the message content to be sent as HTML (`isHTML = true`) |
| `fromTemplate(HTMLTemplate template)` | Automatically sets the message content to the template's string and flags the email as HTML |
| `addRecipient(String recipient)` | Stores the provided email address in the list of recipients |

#### Parameters
| Parameter | Context | Description |
| :--- | :--- | :--- |
| `content` | `setContent` method | The raw string body of the email |
| `subject` | `setSubject` method | The subject/title text for the email |
| `template` | `fromTemplate` method | The `HTMLTemplate` object to be used as the email body |
| `recipient` | `addRecipient` method | The recipient email address string |

<br>

### `MailSession`
This class manages the connection to the email server (hardcoded, Gmail) and handles the actual transmission.

#### Constructors & Methods
| Element | Description |
| :--- | :--- |
| `MailSession(Credentials credentials)` | Configures connection properties that will be used when sending the emails |
| `send(MailMessage msg)` | Constructs a `MimeMessage` using the provided information and sends it to the recipients |

#### Parameters
| Parameter | Context | Description |
| :--- | :--- | :--- |
| `credentials` | `MailSession` constructor | The `Credentials` object containing the sender's account details |
| `msg` | `send` method | The configured `MailMessage` object containing the payload to be sent |


## Sample Code

```java
public class SimpleGmailSender {

    public static void main(String[] args) {

        final String username = "our_email_address@gmail.com";
        final String appPassword = "xxxx xxxx xxxx xxxx"; // 16 character app password

        Credentials credentials = new Credentials(username, appPassword);
        MailSession session = new MailSession(credentials);

        HTMLTemplate welcomeTemplate = new HTMLTemplate("templates/welcome.html"); // template
        welcomeTemplate.format("name", "Ahmet"); // the user's actual name

        MailMessage message = new MailMessage();
        message.setSubject("Welcome");
        message.fromTemplate(welcomeTemplate);
        message.addRecipient("recipient_email_1@gmail.com");
        message.addRecipient("recipient_email_1@ug.bilkent.edu.tr");

        session.send(message);

    }
}
```
