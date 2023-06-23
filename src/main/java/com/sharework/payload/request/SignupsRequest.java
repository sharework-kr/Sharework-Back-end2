package com.sharework.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Valid
public class SignupsRequest {


  @Schema(description = "email",defaultValue ="sharework@sharework.com" )
  @Email
  @NotNull
  private String email;

  @Schema(description = "name",defaultValue ="김쉐어" )
  @NotNull
  @Pattern(regexp = "^[가-힣]+$")
  private String name;

  @Schema(description = "phoneNumber",defaultValue ="01012345678" )
  @NotNull
  @Pattern(regexp = "^[0-9]*$")
  @Size(max = 11, min = 9)
  private String phoneNumber;

  @Schema(description = "residentnumberFront",defaultValue ="20210101" )
  @NotNull
  @Pattern(regexp = "^[0-9]*$")
  @Size(min = 6, max = 6)
  private String residentNumberFront;

  @Schema(description = "residentnumberRear",defaultValue ="1" )
  @NotNull
  @Pattern(regexp = "^[0-9]*$")
  @Size(min = 1, max = 1)
  private String residentNumberRear;

  private Set<String> role;

  // uid를 넣어야한다. 필수값
}
