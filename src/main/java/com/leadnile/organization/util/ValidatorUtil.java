package com.leadnile.organization.util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public interface ValidatorUtil {
	StringBuilder expression = new StringBuilder();
	String PIN_NUMBER_REGEX = "[1-9]{1}+[0-9]{5}";
	String USER_NAME_REGEX = "[a-zA-Z0-9]{3,15}";
	String CITY_NAME_REGEX = "[a-zA-Z]{3,15}";
	String CANADIAN_ZIP_CODE_REGEX = "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$";
	String UK_ZIP_CODE_REGEX = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";
	String US_ZIP_CODE_REGEX = "^[0-9]{5}$";
	String US_SSN_CODE_REGEX = "^(?!000|666)[0-8][0-9]{2}(?!00)[0-9]{2}(?!0000)[0-9]{4}$";
	String EIN_CODE_REGEX = "^[1-9]\\d?-\\d{7}$";
	String ONLY_DIGIT_REGEX = "^[0-9]{9}$";

	static boolean isValidVerfication(final String verficationCode) {
		boolean flagValid = false;
		flagValid = StringUtils.isNotBlank(verficationCode) && verficationCode.length() == 6;
		return flagValid;
	}

	static boolean isValidEmail(final String email) {
		boolean flagValid = false;
		if (StringUtils.isNotBlank(email)) {
			expression.replace(0, 200, "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
			final CharSequence inputStr = email;
			final Pattern pattern = Pattern.compile(expression.toString(), Pattern.CASE_INSENSITIVE);
			final Matcher matcher = pattern.matcher(inputStr);
			flagValid = matcher.matches();
		}
		return flagValid;
	}

	static boolean checkValidEmail(final String email) {
		final boolean flagValid = false;
		if (StringUtils.isNotBlank(email)) {
			final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			final CharSequence inputStr = email;
			final Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
			final Matcher matcher = pattern.matcher(inputStr);
			return matcher.matches();
		}
		return flagValid;
	}

static boolean isValid(final Object value) {
    // If the object is null, it's not valid
    if (value == null) {
        return false;
    }

    if (value instanceof String) {
        return !((String) value).isEmpty();
    }

    if (value instanceof Collection) {
        return !((Collection<?>) value).isEmpty();
    }
    if (value instanceof Map) {
        return !((Map<?, ?>) value).isEmpty();
    }
    return true;
}
	static boolean isValid(final double value) {
		return value > 0;
	}
	static boolean isValid(final Double value) {
		return value != null && value > 0;
	}

	static boolean isValid(final float value) {
		return value > 0;
	}

	static boolean isValid(final Float value) {
		return value != null && value > 0;
	}

	static boolean isValid(final BigDecimal value) {
		return value != null;
	}

	static boolean isValid(final Integer value) {
		return value != null && value > 0;
	}

	static boolean isValid(final Long value) {
		return value != null && value > 0;
	}

	static boolean isValid(final String value) {
		return StringUtils.isNotBlank(value) && !"0".equalsIgnoreCase(value) && !"null".equalsIgnoreCase(value);
	}

	static double getPercentage(final double value, final double percentage) {
		return value * percentage / 100;
	}

	static double round(final double valueToRound, final int numberOfDecimalPlaces) {
		final double multipicationFactor = Math.pow(10, numberOfDecimalPlaces);
		final double interestedInZeroDPs = valueToRound * multipicationFactor;
		return Math.round(interestedInZeroDPs) / multipicationFactor;
	}

	static String getProperDoubleValue(final String value) {
		final java.text.DecimalFormat dfSingleCarLoan = new java.text.DecimalFormat("#0.0");
		return dfSingleCarLoan.format(Double.valueOf(value) / 100000);
	}

	static boolean isValidMobile(final String mobno) {
		if (StringUtils.isNotBlank(mobno) && mobno.length() == 10
				&& (mobno.startsWith("7") || mobno.startsWith("8") || mobno.startsWith("9")))
			try {
				final Long mob = Long.parseLong(mobno);
				return mob > 0;
			} catch (final Exception e) {
				return false;
			}
		return false;
	}

	static boolean isValidNRIMobile(final String mobno) {
		if (StringUtils.isNotBlank(mobno) && mobno.length() > 1 && mobno.length() <= 12)
			try {
				final Long mob = Long.parseLong(mobno);
				return mob > 0;
			} catch (final Exception e) {
				return false;
			}
		return false;
	}

	static boolean isValidGender(final String gender) {
		if (StringUtils.isNotBlank(gender))
			return StringUtils.equalsAnyIgnoreCase(gender, "Male", "Female", "Other");
		return false;
	}

	static boolean isValid(final Date date) {
		return date!=null;
	}
}
