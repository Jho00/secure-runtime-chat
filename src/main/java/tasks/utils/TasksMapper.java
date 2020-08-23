package tasks.utils;

import common.constants.IncomeActions;
import handler.dto.request.Request;
import reactor.core.publisher.Mono;
import tasks.PipeTask;
import tasks.base.BaseTask;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class TasksMapper {
    private static final HashMap<String, Class<? extends BaseTask>> tasksClasses;

    static {
        tasksClasses = new HashMap<>();

        tasksClasses.put(IncomeActions.PIPE_ACTION, PipeTask.class);
    }

    public static BaseTask map(Mono<Request> request) {
        Request req = request.block();
        Class<? extends BaseTask> target = tasksClasses.get(req.getAction());
        try {
            BaseTask task = target.getConstructor().newInstance();
            task.setTaskData(req.getData());
            return task;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            // TODO: handle it
            e.printStackTrace();
        }

        return null;
    }
}
