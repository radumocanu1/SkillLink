package unibuc.SkillLink.abstractions;

import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Mediator implements IMediator {

    private final Map<Class<? extends ICommand<?>>, IHandler<?, ?>> handlers = new HashMap<>();

    public Mediator(List<IHandler<?, ?>> handlerList) {
        for (IHandler<?, ?> handler : handlerList) {
            Class<?> commandType = (Class<?>) ((ParameterizedType) handler.getClass()
                    .getGenericInterfaces()[0]).getActualTypeArguments()[0];

            handlers.put((Class<? extends ICommand<?>>) commandType, handler);
        }
    }
    @Override
    public <TResponse> TResponse handle(ICommand<TResponse> command){
        IHandler<ICommand<TResponse>, TResponse> handler = (IHandler<ICommand<TResponse>, TResponse>) handlers.get(command.getClass());

        if (handler == null) {
            throw new IllegalArgumentException("No handler found for command: " + command.getClass().getName());
        }

        return handler.handle(command);
    }
}

