package demo.upload.domain.transaction.reader;

import demo.upload.domain.common.DateTimeProvider;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
class TransactionCsvFileDataLineValidator {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DateTimeProvider dateTimeProvider;

    public TransactionCsvFileDataLineValidator(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    List<String> validate(TransactionCsvFileDataLine line) {
        var errors = new ArrayList<String>();
        errors.addAll(validateRelationships(line));

        errors.addAll(validateReceiver(line.getReceiver()));
        errors.addAll(validateSender(line.getSender()));
        errors.addAll(validateDate(line.getDate()));
        errors.addAll(validateAmount(line.getAmount()));
        return errors;
    }

    private List<String> validateRelationships(TransactionCsvFileDataLine line) {
        if (Objects.equals(line.getReceiver(), line.getSender())) {
            return List.of("Receiver and seder cannot be equal");
        }
        return List.of();
    }

    private List<String> validateReceiver(String receiver) {
        return validateTransactionSide(receiver, "Receiver");
    }

    private List<String> validateSender(String sender) {
        return validateTransactionSide(sender, "Sender");
    }

    private List<String> validateDate(String date) {
        if (date == null) {
            return buildCannotBeEmptyError("Date");
        }
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(date, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            return List.of("Invalid date format, should be: yyyy-MM-dd");
        }
        var today = dateTimeProvider.getNowLocalDate();
        if (parsedDate.isAfter(today)) {
            return List.of("Date cannot be in the future");
        }
        return List.of();
    }

    private List<String> validateAmount(String amount) {
        if (amount == null) {
            return buildCannotBeEmptyError("Amount");
        }
        BigDecimal parsedAmount;
        try {
            parsedAmount = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            return List.of("Invalid amount format");
        }
        var errors = new ArrayList<String>(2);
        if (parsedAmount.signum() < 0) {
            errors.add("Amount cannot be negative");
        }
        if (parsedAmount.scale() > 4) {
            errors.add("Amount cannot have more than 4 decimal places");
        }
        return errors;
    }

    private static List<String> validateTransactionSide(String side, String sideName) {
        if (side == null) {
            return buildCannotBeEmptyError(sideName);
        }
        var errors = new ArrayList<String>(2);
        if (side.length() != 5) {
            errors.add(sideName + " has to be 5 chars length");
        }
        if (!side.matches("[0-9A-Z]+")) {
            errors.add(sideName + " can contains only digits and capital letters");
        }
        return errors;
    }

    private static List<String> buildCannotBeEmptyError(String propertyName) {
        return List.of(propertyName + " cannot be empty");
    }
}
