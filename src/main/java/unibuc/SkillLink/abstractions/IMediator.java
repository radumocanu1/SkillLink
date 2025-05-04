package unibuc.SkillLink.abstractions;

public interface IMediator {
    <TResponse> TResponse handle(ICommand<TResponse> command);
}
