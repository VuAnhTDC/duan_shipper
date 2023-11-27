package com.example.duangiaohang.Class;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValiDate {

    private static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean validPhone(String phone) {
        String strRegex = "^0[0-9]{9}$";
        Pattern pattern = Pattern.compile(strRegex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.find();
    }

    public static boolean validFullname(String fullname) {
        String strRegex = "^([a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]{8,70})$";
        Pattern pattern = Pattern.compile(strRegex);
        Matcher matcher = pattern.matcher(fullname);
        return matcher.find();
    }

    public static boolean validShipperName(String shopName) {
        String strRegex = "^([a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ0-9'\"\\s]{8,100})$";
        Pattern pattern = Pattern.compile(strRegex);
        Matcher matcher = pattern.matcher(shopName);
        return matcher.find();
    }

    public static boolean validShipperAddress(String shopAddress) {
        String strRegex = "^(?!.*[/\\\\.]{2})[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ0-9\\s./-]{8,300}$";
        Pattern pattern = Pattern.compile(strRegex);
        Matcher matcher = pattern.matcher(shopAddress);
        return matcher.find();
    }

    public static boolean validEmail(String email) {
        String strRegex = "^([a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)?@gmail\\.com)$";
        Pattern pattern = Pattern.compile(strRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static boolean validShipperPassword(String password) {
        String strRegex = "^((?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_-]).{8,40})$";
        Pattern pattern = Pattern.compile(strRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
    public static boolean validOTP(String otp) {
        String strRegex = "^\\d{6}$";
        Pattern pattern = Pattern.compile(strRegex);
        Matcher matcher = pattern.matcher(otp);
        return matcher.find();
    }

    public static boolean valiShipperdOTP(String otp) {
        String strRegex = "^\\d{6}$";
        return false;
    }
}
