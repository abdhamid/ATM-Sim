package abdhamid.atm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private String accNumber;
    private String accPin;

    @Override
    public String toString() {
        return "LoginDto{" +
                "accNumber='" + accNumber + '\'' +
                '}';
    }
}
