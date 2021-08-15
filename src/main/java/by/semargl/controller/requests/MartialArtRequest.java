package by.semargl.controller.requests;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import by.semargl.domain.enums.MartialArtType;

@ApiOperation("Class for creating and updating martial art entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MartialArtRequest {

    private String name;

    private MartialArtType martialArtType;

    private String origin;

    private LocalDateTime foundationDate;

    private String description;

    private Boolean weaponTechnicsAvailable;
}
