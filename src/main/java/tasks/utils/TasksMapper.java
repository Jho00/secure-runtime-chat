package tasks.utils;

import common.constants.IncomeActions;
import handler.dto.request.Request;
import tasks.ErrorTask;
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

    public static BaseTask map(Request request) {
        Class<? extends BaseTask> target = tasksClasses.get(request.getAction());
        try {
            BaseTask task = target.getConstructor().newInstance();
            task.setTaskData(request.getData());
            return task;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            // TODO: inject logger
            e.printStackTrace();

            return ErrorTask.createNewErrorTask();
        }
    }
}
