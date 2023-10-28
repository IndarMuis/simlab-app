package simlabapp.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import simlabapp.entity.enums.Gender;
import simlabapp.entity.enums.Major;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreateAssistantRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    private String phone;

    private String address;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String placeOfBirth;

    @NotNull
    private Date dateOfBirth;

    @NotNull
    private Major major;

    @NotNull
    private Gender gender;

    private String imageProfile;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String confirmPassword;

}
