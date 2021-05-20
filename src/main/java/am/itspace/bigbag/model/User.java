package am.itspace.bigbag.model;

import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Name is Required")
    private String name;
    @NotEmpty(message = "Surname is Required")
    private String surname;
    @NotEmpty(message = "Email can not be null")
    @Email(message = "Email is not valid")
    private String email;
    @Size(min = 6,message = "Password should be between 6 and 9 ")
    private String password;
    @Enumerated(value = EnumType.STRING)
    private UserType userType = UserType.USER;
    private String picUrl;
    @Size(min = 9, message = "Phone should be 9 ")
    private String phone;
}