package tasks;

import common.stream.NettyInputOutputProvider;
import lombok.Getter;
import reactor.core.publisher.Mono;
import tasks.base.BaseTask;

public class ErrorTask extends BaseTask {
    public static ErrorTask createNewErrorTask() {
        ErrorTask task = new ErrorTask();
        task.setTaskData(new ErrorData());

        return task;
    }


    @Override
    public Mono<Void> execute(NettyInputOutputProvider provider) {
        return null;
    }



    public static class ErrorData {
        @Getter
        private boolean error = true;
    }
}
