package com.jsal.jeduApi.student.controller.request;

import com.jsal.jeduApi.common.annotation.PhoneNumber;
import com.jsal.jeduApi.common.annotation.TrimmedString;
import com.jsal.jeduApi.student.repository.type.StudentStatus;
import com.jsal.jeduApi.student.util.StudentMessage;
import jakarta.validation.constraints.NotNull;

/**
 * @param displayId   String
 * @param fullName    String
 * @param phoneNumber String
 * @param status      Student Status
 * @author Yozakura Minato
 */
public record UpdateStudentRequest(
        @NotNull(message = StudentMessage.DisplayId.NULL)
        @TrimmedString(message = StudentMessage.DisplayId.NOT_TRIMMED)
        String displayId,

        @NotNull(message = StudentMessage.FullName.NULL)
        @TrimmedString(message = StudentMessage.FullName.NOT_TRIMMED)
        String fullName,

        @PhoneNumber(message = StudentMessage.PhoneNumber.INVALID_FORMAT)
        String phoneNumber,

        @NotNull(message = StudentMessage.Status.NULL)
        StudentStatus status
) {
}
