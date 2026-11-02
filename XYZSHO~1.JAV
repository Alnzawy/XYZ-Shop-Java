        import java.io.FileWriter;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Scanner;

        public class XYZShop {

            // Attributes
                private boolean loggedIn;
                private Admin admin;
                private User currentUser;
                UserCollections userCollections = UserCollections.getInstance();

                Scanner scanner = new Scanner(System.in);


            // constructor with admin as a parameter
        public XYZShop(Admin admin) {
                loggedIn = false;
                this.admin = admin;
        }


        public void ShopInterface() {
                System.out.println("");
                System.out.println("XYZ Shop:");
                System.out.println("==========================================");
                System.out.println("(1) Log in                                ");
                System.out.println("(2) Log out                               ");
                System.out.println("(3) Register                              ");
                System.out.println("(4) Exit                                  ");
                System.out.println("==========================================");
        }


            /* General Rules */
            // 1- If you logged in you can not register until you logged out
            // 2- Username and pass can not be blank
            // 3- you cant register with admin or any username used before
            // 4- the method call saveUserToFile which save user data in file with his type

        public void registerUser() {

                if (loggedIn) {
                    System.out.println("You are logged in");
                    return;
                }

                System.out.println("Choose registration type:");
                System.out.println("(1) Customer");
                System.out.println("(2) Seller");
                System.out.println("(3) Exit");

                String choice = scanner.nextLine();

                switch (choice) {

                    case "1":
                    case "2":
                        String USERNAME;

                        do {
                            System.out.print("Enter your USERNAME: ");
                            USERNAME = scanner.nextLine();

                            if (USERNAME.equals("")) {
                                System.out.println("Error: The USERNAME must not be blank!");
                            }
                                // search if the username math admin user or match in previous user
                            if (findUserByUsername(USERNAME) != null || USERNAME.equals(admin.getAdminUSERNAME())) {
                                System.out.println("Error: A user with this name already exists!");
                            }

                        }
                        while (USERNAME.equals("") || findUserByUsername(USERNAME) != null
                                || USERNAME.equals(admin.getAdminUSERNAME()));


                        String PASSWORD;

                        do {
                            System.out.print("Enter your PASSWORD: ");
                            PASSWORD = scanner.nextLine();

                            if (PASSWORD.equals("")) {
                                System.out.println("Error: The PASSWORD must not be blank!");
                            }

                        }
                        while (PASSWORD.equals(""));


                        User newUser;
                        String userType;

                        switch (choice) {
                            case "1":
                                // polymorphism
                                newUser = new Customer(USERNAME, PASSWORD);
                                userType = newUser.getUserType(); // for txt file

                                userCollections.addUser(newUser);

                                break;

                            case "2":

                                newUser = new Seller(USERNAME, PASSWORD);
                                userType = newUser.getUserType();

                                userCollections.addUser(newUser);

                                break;


                            default:
                                System.out.println("Invalid choice!");
                                System.out.println("");
                                registerUser();
                                return;
                        }

                        saveUserToFile(newUser, userType + ".txt");

                        System.out.println("Registration has been done successfully as a " + userType + " and with  "
                                            + USERNAME +"as a Username");

                        break;

                    case "3":
                        return;

                    default:
                        System.out.println("Invalid choice!");
                        System.out.println("");
                        registerUser();
                }

        }


            /* General Rules */
            // 1- the method save user data in file with his type
            // 2- its store his USERNAME,PASSWORD,ID,TYPE

            private void saveUserToFile(User user, String fileName) {
                try (FileWriter writer = new FileWriter(fileName, true)) {
                    writer.write(String.format("| %-10s | %-20s | %-15s | %-10s |\n", "USER ID", "USERNAME", "USER TYPE", "STATUS"));
                    writer.write("--------------------------------------------------------------------\n");
                    writer.write(String.format("| %-10s | %-20s | %-15s | %-10s |\n",
                            user.getUserID(),
                            user.getUSERNAME(),
                            user.getUserType(),
                            user.getStatus()));
                    writer.write("--------------------------------------------------------------------\n");
                    writer.write("\n");
                } catch (IOException e) {
                    System.out.println("Error saving user data: " + e.getMessage());
                }
            }



            /* General Rules */
            // 1- If you logged in with wrong username or wrong pass it tells you
            // 2- if you logged the user or the admin interface will work everytime you log in until you log out


        public void login() {

                if (loggedIn && (currentUser != null) && (currentUser.getUserType().equals("Customer")
                        || currentUser.getUserType().equals("Seller"))) {
                    currentUser.Interface();
                    return;
                }

                if (loggedIn ) {
                    admin.AdminInterface();
                    return;
                }
            String username;

            do {
                System.out.print("Enter your USERNAME: ");
                username = scanner.nextLine();

                if (username.equals("")) {
                    System.out.println("Error: The USERNAME must not be blank!");
                }


            }
            while (username.equals(""));

            String password;

            do {
                System.out.print("Enter your PASSWORD: ");
                password = scanner.nextLine();

                if (password.equals("")) {
                    System.out.println("Error: The PASSWORD must not be blank!");
                }


            }
            while (password.equals(""));



                // Check for admin login
                if (username.equals(admin.getAdminUSERNAME()) && password.equals(admin.getAdminPASSWORD())) {
                    System.out.println("Admin login successful!");
                    System.out.println("=======================");
                    admin.AdminInterface();
                    loggedIn = true;
                    return;
                }


                User user = findUserByUsername(username);

                if (user != null && user.getPASSWORD().equals(password)) {

                    loggedIn = true;
                    currentUser = user;

                    System.out.println("Login successful! ");
                    System.out.println("Welcome: "+currentUser.getUSERNAME());

                        currentUser.Interface();

                } else {
                    System.out.println("Login failed. Incorrect username or password.");
                }
        }


        // this method takes username and go in userCollections its return user or null
        private User findUserByUsername(String username) {

                for (User user : userCollections.getAllUsers()) {
                    if (user.getUSERNAME().equals(username)) {
                        return user;
                    }
                }
                return null;
        }



        public void logout() {
                if (!loggedIn) {
                    System.out.println("You are not logged in.");
                    return;
                }
                loggedIn = false;
                currentUser = null;
                System.out.println("You have successfully logged out.");
            }

            public void run() {
                boolean running = true;
                while (running) {
                    ShopInterface();
                    System.out.print("Enter your choice: ");
                    String choice = scanner.nextLine();

                    switch (choice) {
                        case "1":
                            login();
                            break;
                        case "2":
                            logout();
                            break;
                        case "3":
                            registerUser();
                            break;
                        case "4":
                            running = false;
                            System.out.println("Exiting XYZ Shop. Goodbye!");
                            break;

                        default:
                            System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                            break;
                    }
                }
            }

        }

