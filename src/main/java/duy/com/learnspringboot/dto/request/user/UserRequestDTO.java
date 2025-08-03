package duy.com.learnspringboot.dto.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import duy.com.learnspringboot.utils.EnumPattern;
import duy.com.learnspringboot.utils.PhoneNumber;
import duy.com.learnspringboot.utils.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class UserRequestDTO implements Serializable {
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    private String lastName;

    @NotNull
    @NotBlank
    @Email(message = "Invalid email format")
    private String email;

    @NotNull
    @NotBlank
//    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @PhoneNumber
    private String phone;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date dateOfBirth;

    @EnumPattern(name = "status", regexp = "^(ACTIVE|INACTIVE|SUSPENDED)$", message = "Status must be one of: ACTIVE, INACTIVE, SUSPENDED")
    private UserStatus status;

    public UserRequestDTO(String firstName, String lastName, String email, String phone, Date dateOfBirth, UserStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
