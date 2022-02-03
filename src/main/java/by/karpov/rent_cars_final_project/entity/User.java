package by.karpov.rent_cars_final_project.entity;

public class User implements BaseEntity {
    public enum UserStatus {
        ACTIVE, BANNED, CONFIRMATION_AWAITING
    }

    public enum UserRole {
        CLIENT, MANAGER, ADMIN
    }

    private Long id;
    private String firstName;
    private String lastName;
    private String emailLogin;
    private String phoneNumber;
    private UserRole role;
    private UserStatus userStatus;

    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmailLogin() {
        return this.emailLogin;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public UserRole getRole() {
        return this.role;
    }

    public UserStatus getUserStatus() {
        return this.userStatus;
    }


    public String toString() {
        return "User(id=" + this.getId() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", emailLogin=" + this.getEmailLogin() + ", phoneNumber=" + this.getPhoneNumber() + ", role=" + this.getRole() + ", userStatus=" + this.getUserStatus() +  ")";
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmailLogin(String emailLogin) {
        this.emailLogin = emailLogin;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    User(Long id, String firstName, String lastName, String emailLogin, String phoneNumber, UserRole role, UserStatus userStatus) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailLogin = emailLogin;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.userStatus = userStatus;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String emailLogin;
        private String phoneNumber;
        private UserRole role;
        private UserStatus userStatus;

        UserBuilder() {
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder emailLogin(String emailLogin) {
            this.emailLogin = emailLogin;
            return this;
        }

        public UserBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder role(UserRole role) {
            this.role = role;
            return this;
        }

        public UserBuilder userStatus(UserStatus userStatus) {
            this.userStatus = userStatus;
            return this;
        }

        public User build() {
            return new User(id, firstName, lastName, emailLogin, phoneNumber, role, userStatus);
        }

        public String toString() {
            return "User.UserBuilder(id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", emailLogin=" + this.emailLogin + ", phoneNumber=" + this.phoneNumber + ", role=" + this.role + ", userStatus=" + this.userStatus + ")";
        }
    }
}
