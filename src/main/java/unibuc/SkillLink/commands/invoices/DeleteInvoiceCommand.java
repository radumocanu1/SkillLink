package unibuc.SkillLink.commands.invoices;

import groovyjarjarpicocli.CommandLine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import unibuc.SkillLink.abstractions.ICommand;

import java.util.UUID;
@Getter
@AllArgsConstructor
public class DeleteInvoiceCommand implements ICommand<Void> {
    UUID id;
}
