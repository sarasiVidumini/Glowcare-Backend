package lk.ijse.glowcare_backend.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class APIResponse <T>{
    private int status;
    private String message;
    private T data;
}
