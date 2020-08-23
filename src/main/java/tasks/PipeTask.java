package tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.stream.InputOutputController;
import lombok.SneakyThrows;
import reactor.core.publisher.Mono;
import tasks.base.BaseTask;

public class PipeTask extends BaseTask {
    @SneakyThrows
    @Override
    public Mono<Void> execute() {
        InputOutputController controller = InputOutputController.getController();
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(this.taskData);
        Mono<String> data = Mono.just(json);
        return controller.sendData(data);
    }
}
