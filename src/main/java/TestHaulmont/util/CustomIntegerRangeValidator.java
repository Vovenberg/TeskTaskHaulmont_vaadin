package TestHaulmont.util;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.IntegerRangeValidator;

/**
 * Created by Vladimir on 24.08.2016.
 */
public class CustomIntegerRangeValidator extends AbstractValidator<String> {

    private static final long serialVersionUID = 1L;

    private IntegerRangeValidator integerRangeValidator;

    public CustomIntegerRangeValidator(String errorMessage, Integer minValue, Integer maxValue) {
        super(errorMessage);
        this.integerRangeValidator = new IntegerRangeValidator(errorMessage, minValue, maxValue);
    }

    @Override
    protected boolean isValidValue(String value) {
        try {
            Integer result = Integer.parseInt(value);
            integerRangeValidator.validate(result);
            return true;
        } catch (NumberFormatException nfe) {
            // TODO: handle exception
            System.out.println("Cannot be parsed as Integer: " + nfe);
            return false;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Unknown exception: " + e);
            return false;
        }

    }

    @Override
    public Class<String> getType() {
        return String.class;
    }

}
