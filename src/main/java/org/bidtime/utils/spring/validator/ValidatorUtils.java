package org.bidtime.utils.spring.validator;

import org.apache.commons.lang.StringUtils;
import org.bidtime.web.utils.LocalMsg;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by libing on 2015/11/30.
 * user-server
 */
public class ValidatorUtils {

    @Deprecated
    public static String getErrorMessage(BindingResult result) {
        StringBuffer sb = new StringBuffer();
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                String errorType = error.getCode();
                if (errorType.equalsIgnoreCase("typeMismatch")) {
                    sb.append(error.getField() + "格式错误\n");
                } else {
                    sb.append(error.getDefaultMessage() + "\n");
                }

            }
        }
        return sb.toString();

    }

    public static String getErrorMessage(BindingResult result, HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                String errorType = error.getCode();
                if (errorType.equalsIgnoreCase("typeMismatch")) {
                    String errorFiled = error.getField();
                    String i18nError = LocalMsg.getMessage(errorFiled, request);
                    if (StringUtils.isNotEmpty(i18nError)) {
                        sb.append(error.getField() + "格式错误 ");
                    } else {
                        sb.append(error.getField() + "格式错误 ");
                    }
                } else {
                    sb.append(error.getDefaultMessage() + " ");
                }

            }
        }
        return sb.toString();

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static String valid(Object obj, Class... calssT) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set s = validator.validate(obj, calssT);
        Iterator<ConstraintViolation<Object>> iter = s
                .iterator();
        StringBuffer stringBuffer = new StringBuffer();
        while (iter.hasNext()) {
            ConstraintViolation<Object> c = iter.next();
            String message = c.getMessage() + ";";
            stringBuffer.append(message);
        }
        return stringBuffer.toString();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static ValidatorResult validForRes(Object obj, Class... calssT) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set s = validator.validate(obj, calssT);
        Iterator<ConstraintViolation<Object>> iter = s
                .iterator();
        StringBuffer stringBuffer = new StringBuffer();
        ValidatorResult result = new ValidatorResult();
        result.setResult(true);
        while (iter.hasNext()) {
            ConstraintViolation<Object> c = iter.next();
            String message = c.getMessage() + ";";
            stringBuffer.append(message);
            result.setResult(false);
        }
        result.setMsg(stringBuffer.toString());
        return result;
    }

    @SuppressWarnings("rawtypes")
	public static String validateProperty(Object obj, String filed, Class... calssT) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> s = validator.validateProperty(obj, filed, calssT);
        Iterator<ConstraintViolation<Object>> iter = s
                .iterator();
        StringBuffer stringBuffer = new StringBuffer();
        while (iter.hasNext()) {
            ConstraintViolation<Object> c = iter.next();
            String message = c.getMessage() + ";";
            stringBuffer.append(message);
        }
        return stringBuffer.toString();
    }


}
