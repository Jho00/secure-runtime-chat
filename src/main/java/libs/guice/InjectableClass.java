package libs.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public abstract class InjectableClass {
    private static final AbstractModule module = new BasicModule();

    public static AbstractModule getModule() {
        return module;
    }

    public InjectableClass() {
        Injector injector = Guice.createInjector(getModule());
        injector.injectMembers(this);
    }
}
