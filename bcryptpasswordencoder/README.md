
This example illustrates the way how can Springboot security BcryptPasswordEncoder class be used to generate and store one way hashed password (using salt - a random data) in the Database and then use the stored hashed password later to authenticate the user using form based authentication via UI.

Components - 
1. A simple Bootstrap based GUI for --> asking user to first register using emailAddress and password and verifying user's credential especially the password.
2. SpringSecutrity backend for --> storing user's password in emebedded H2 database as one way hashed string using BcryptPasswordEncoder API and verifying the same when user tries to log into application. 
3. Embedded H2 database (I have enabled this by adding H2 along with jpa dependency in pom.xml)

We have basically two important rest endpoints in the application ( com.dev.bcryptpasswordencoder.controller.BcryptController.java )
1. /bcrypt/add --> This endpoint is responsible for taking user's email and password from the registration form of UI and storing the user in the DATABASE. (Password is stored in the form of hash using Spring BcryptPasswordEncoder)
2. /bcrypt/verify --> This endpoint is reponsible for verifying user's password submiited by user from the login form of UI.

 

		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
		import org.springframework.security.crypto.password.PasswordEncoder;
		import org.springframework.web.bind.annotation.PostMapping;
		import org.springframework.web.bind.annotation.RequestBody;
		import org.springframework.web.bind.annotation.RequestMapping;
		import org.springframework.web.bind.annotation.RestController;

		import com.dev.bcryptpasswordencoder.pojo.User;
		import com.dev.bcryptpasswordencoder.repository.DBRepository;

		@RestController
		@RequestMapping("/bcrypt")
		public class BcryptController {

			@Autowired
			DBRepository repository;
			
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			
			@PostMapping("/add")
			public String addUser(@RequestBody User user)
			{
				Optional<User> authenticatedUser = repository.findById(user.getUserEmail());
				if(authenticatedUser.isPresent())
				{
					return "User with email: " + user.getUserEmail() + " already exist";
				}
				String bcryptedPassword = encoder.encode(user.getUserPassword());
				user.setUserPassword(bcryptedPassword);
				repository.save(user);
				return "User: " + user.getUserEmail() + " is added";
			}
			
			@PostMapping("/verify")
			public String verifyUser(@RequestBody User user)
			{
				String message;
				Optional<User> authenticatedUser = repository.findById(user.getUserEmail());
				if(authenticatedUser.isPresent())
				{
					boolean isPasswordMatched = encoder.matches(user.getUserPassword(), 
                               authenticatedUser.get().getUserPassword());
					if(isPasswordMatched)
					{
						message = "Password matches";
					}
					else
					{
						message = "Password incorrect";
					}
				}
				else
				{
					message = "userName does not exist";
				}
				return message;
			}
			
		}


Two keypoints to note here 
 1. BcryptPasswordEncoder's encode(charSequence arg0) is used to generate salt based 128bit hash string (See addUser() endpoint)
 2. BcryptPasswordEncoder's matches(charSequence agr0, String hashedPassword) is used to match the supplied String password with stored hashed password.

How to run -
1. Just import this maven project in eclipse. This spring boot application is configured to run on port 8090 (see server.port in application.properties) 
2. I have given two addition sql files in the resources directory - schema.sql and data.sql. (also note I have disabled spring boot auto ddl creation by spring.jpa.hibernate.ddl-auto=none property because I want to tell spring to generate database schema and populate the table with some dummy data as per my requirement at the time of startup using schema.sql and data.sql files respectively). 
After startup, the table 'USER' will have one default entry with userEmail as 'admin@gmail.com' and userPassword as '@dmin'. So you can use this to directly verify the credential for admin user. (This is just for demo purpose, eventhough you can add as many users from UI)

3. Run the springboot application. And start the UI by hitting the below given url -
http://localhost:8090/bcrypt/index    (Change the port as per application.properties, otherwise default is set to 8090 in this project)

4. You will see two forms --> SignUp form and Login form. Use Sign up form to add a user to Database and then use the LoginForm to validate the same user's credentials.


Conclusion -
1. Using SignUp form, user is added to database with his/her password stored as one-way-hash.
2. Using Login form, user's password is validated against stored password which is a 128bit salt string.


