package com.sharework.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentNumberJsonb {
	@Schema(description = "residentnumberFront",defaultValue ="20210101" )
	@JsonProperty("front")
	private String front;

	@Schema(description = "residentnumberRear",defaultValue ="1" )
	@JsonProperty("rear")
	private String rear;

}