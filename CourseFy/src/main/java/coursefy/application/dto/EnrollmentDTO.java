package coursefy.application.dto;

import coursefy.domain.Enrollment;
import coursefy.domain.EnrolmentId;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
public class EnrollmentDTO {
    private EnrolmentId id;

    private String dateEnrollment;
    private String dateUpdate;
    private float price;
    private CourseDTO courseDTO;
    private UserDTO userDTO;

    public EnrollmentDTO(Enrollment enrollment){
        id = enrollment.getId();
        price = enrollment.getPrice();

        dateEnrollment = updateDate(enrollment.getDateEnrollment());
        dateUpdate = updateDate(enrollment.getDateUpdate());

        courseDTO = new CourseDTO(enrollment.getCourse());
        userDTO = new UserDTO(enrollment.getUser());
    }

    public EnrollmentDTO(float price) {
        this.dateEnrollment = updateDate(LocalDate.now());
        this.dateUpdate = updateDate(LocalDate.now());
        this.price = price;
    }

    private String updateDate(LocalDate date) {
        if(date != null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return date.format(formatter);
        }
        return null;
    }
}
