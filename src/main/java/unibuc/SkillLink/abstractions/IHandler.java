package unibuc.SkillLink.abstractions;

public interface IHandler<TCommand extends ICommand<TResponse>, TResponse> {
    public TResponse handle(TCommand command);
}
