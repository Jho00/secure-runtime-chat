package libs.guice;

import com.google.inject.AbstractModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tasks.utils.TaskMapperImpl;
import tasks.utils.TasksMapper;

public class BasicModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Logger.class).toInstance(LogManager.getLogger());

        bind(TasksMapper.class).to(TaskMapperImpl.class);
    }
}
