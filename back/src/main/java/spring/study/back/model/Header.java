package spring.study.back.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Header<T> {
    private LocalDateTime time;
    private String resultCode;
    private String description;
    private T data;
    private Pagination pagination;


    public static <T> Header<T> OK() {
        return Header.<T>builder()
                .time(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .build();
    }

    //DATA OK
    public static <T> Header<T> OK(T data) {
        return Header.<T>builder()
                .time(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)  // ??
                .build();
    }
    public static <T> Header<T> OK(T data,Pagination pagination) {
        return Header.<T>builder()
                .time(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)  // ??
                .pagination(pagination)  // ??
                .build();
    }
    public static <T> Header<T> ERROR(String description) {
        return Header.<T>builder()
                .time(LocalDateTime.now())
                .resultCode("FUCK")
                .description(description)  // ??
                .build();
    }
}
