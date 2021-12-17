package rs.ac.uns.ftn.camundaformsextension.validator;

import java.time.LocalDate;
import java.time.LocalTime;

public class MinValueValidator extends AbstractValueValidator {

    @Override
    public boolean validate(Double submittedNum, Double configNum) {
        return submittedNum >= configNum;
    }

    @Override
    public boolean validate(LocalDate submittedDate, LocalDate configDate) {
        return !configDate.isAfter(submittedDate);
    }

    @Override
    public boolean validate(LocalTime submittedTime, LocalTime configTime) {
        return !configTime.isAfter(submittedTime);
    }

}
