package by.semargl.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MartialArt {
    private Long id;

    private String name;

    private String type;

    private String origin;

    private Date foundationDate;

    private String description;

    private Boolean weaponTechnicsAvailable;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
