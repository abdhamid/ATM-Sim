package abdhamid.atm.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoggerMessage {
    private String className;
    private String methodName;
    private String methodArgs;
    private Long elapsedTimeinMillis;
    private Object result;

    @SneakyThrows
    @Override
    public String toString() {
        return "LoggerMessage{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", methodArgs='" + methodArgs + '\'' +
                ", elapsedTimeinMillis=" + elapsedTimeinMillis +
                ", result=" + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this.result) +
                '}';
    }
}
