package com.zuhee.gw.api.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {

    @JsonProperty("성공")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean 성공;

    @JsonProperty("데이터")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T 데이터;

    @JsonProperty("메시지")
    @JsonInclude(JsonInclude.Include.ALWAYS) // Always include even if null
    private String 메시지;

    // For Error cases
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean success;

    @JsonInclude(JsonInclude.Include.ALWAYS) // Always include even if null
    private T data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ApiError error;

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder()
                .성공(true)
                .데이터(data)
                .메시지(null)
                .build();
    }

    public static ApiResponse<Void> fail(String code, String message) {
        return ApiResponse.<Void>builder()
                .success(false)
                .data(null)
                .error(new ApiError(code, message))
                .build();
    }

    @Getter
    public static class ApiError {
        private final String code;
        private final String message;

        public ApiError(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
