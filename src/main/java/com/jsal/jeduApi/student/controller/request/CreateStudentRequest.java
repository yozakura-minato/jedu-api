package com.jsal.jeduApi.student.controller.request;

import com.jsal.jeduApi.common.annotation.PhoneNumber;
import com.jsal.jeduApi.common.annotation.StrongPassword;
import com.jsal.jeduApi.common.annotation.TrimmedString;
import com.jsal.jeduApi.student.util.StudentMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * @param displayId   String
 * @param fullName    String
 * @param email       String
 * @param phoneNumber String
 * @param password    String
 * @author Yozakura Minato
 */
public record CreateStudentRequest(
        @NotNull(message = StudentMessage.DisplayId.NULL)
        @TrimmedString(message = StudentMessage.DisplayId.NOT_TRIMMED)
        String displayId,

        @NotNull(message = StudentMessage.FullName.NULL)
        @TrimmedString(message = StudentMessage.FullName.NOT_TRIMMED)
        String fullName,

        @NotNull(message = StudentMessage.Email.NULL)
        @Email(message = StudentMessage.Email.INVALID_FORMAT)
        String email,

        @PhoneNumber(message = StudentMessage.PhoneNumber.INVALID_FORMAT)
        String phoneNumber,

        @NotNull(message = StudentMessage.Password.NULL)
        @TrimmedString(message = StudentMessage.Password.NOT_TRIMMED)
        @StrongPassword(message = StudentMessage.Password.WEAK)
        String password
) {
}
