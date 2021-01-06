package tasks.utils;

import com.google.inject.Inject;
import common.constants.IncomeActions;
import handler.dto.request.Request;
import libs.guice.InjectableClass;
import org.apache.logging.log4j.Logger;
import tasks.ErrorTask;
import tasks.PipeTask;
import tasks.base.BaseTask;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class TaskMapperImpl extends InjectableClass implements TasksMapper {
    private final HashMap<String, Class<? extends BaseTask>> tasksClasses;

    @Inject
    public Logger logger;

    {
        tasksClasses = new HashMap<>();

        tasksClasses.put(IncomeActions.PIPE_ACTION, PipeTask.class);
    }

    public TaskMapperImpl() {
        super();
    }

    public BaseTask map(Request request) {
        Class<? extends BaseTask> target = tasksClasses.get(request.getAction());
        try {
            BaseTask task = target.getConstructor().newInstance();
            task.setTaskData(request.getData());
            return task;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.error(e.getStackTrace());
            return ErrorTask.createNewErrorTask();
        }
    }
}
