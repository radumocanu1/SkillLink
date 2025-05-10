package unibuc.SkillLink.abstractions;

public interface IHandler<TCommand extends ICommand<TResponse>, TResponse> {
    TResponse handle(TCommand command);
}
