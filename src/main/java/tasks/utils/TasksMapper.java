package tasks.utils;

import handler.dto.request.Request;
import tasks.base.BaseTask;

public interface TasksMapper {
    BaseTask map(Request request);
}
