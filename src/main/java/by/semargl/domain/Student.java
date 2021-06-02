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
public class Student {
    private Long id;

    private Long userId;

    private Long gradeId;

    private Long teachersUserId;

    private Date lastExamDate;

    private Integer numbersOfTrainingsDays;

    private Date startDate;

    private Boolean isDeleted;

    private Date created;

    private Date changed;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
