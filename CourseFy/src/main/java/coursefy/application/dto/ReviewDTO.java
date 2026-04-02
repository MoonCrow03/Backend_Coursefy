package coursefy.application.dto;

import coursefy.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
public class ReviewDTO {
    private Long id;
    private String title;
    private String contents;
    private int satisfactionDegree;
    private String date;

    public ReviewDTO(Review review){
        id = review.getId();
        title = review.getTitle();
        contents = review.getContents();
        satisfactionDegree = review.getSatisfactionDegree();
        date = updateDate(review.getCreationDate());
    }

    public ReviewDTO(String title, String contents, int satisfactionDegree) {
        this.title = title;
        this.contents = contents;
        this.satisfactionDegree = satisfactionDegree;
        this.date = updateDate(LocalDate.now());
    }

    private String updateDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
