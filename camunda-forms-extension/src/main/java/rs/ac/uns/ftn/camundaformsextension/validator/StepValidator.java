package rs.ac.uns.ftn.camundaformsextension.validator;

import org.camunda.bpm.engine.impl.form.validator.FormFieldValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class StepValidator extends AbstractValueValidator {

    @Override
    public boolean validate(Double submittedNum, Double configNum) {
        return configNum != 0 && findMod(submittedNum, configNum) == 0;
    }

    @Override
    public boolean validate(LocalDate submittedDate, LocalDate configDate) {
        throw new FormFieldValidationException(submittedDate, "Step validator can only be used with numeric values. Submitted value "
                + submittedDate + " and configuration " + configDate + " are of type LocalDate.");
    }

    @Override
    public boolean validate(LocalTime submittedTime, LocalTime configTime) {
        throw new FormFieldValidationException(submittedTime, "Step validator can only be used with numeric values. Submitted value "
                + submittedTime + " and configuration " + configTime + " are of type LocalTime.");
    }

    private static double findMod(double a, double b) {
        if (a < 0)
            a = -a;
        if (b < 0)
            b = -b;

        BigDecimal _mod = new BigDecimal(Double.toString(a)).stripTrailingZeros();
        BigDecimal _b = new BigDecimal(Double.toString(b)).stripTrailingZeros();
        while (_mod.compareTo(_b) >= 0)
            _mod = _mod.subtract(_b);

        double mod = _mod.doubleValue();

        if (a < 0)
            return -mod;

        return mod;
    }

}
