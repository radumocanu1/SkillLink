package unibuc.SkillLink.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import unibuc.SkillLink.abstractions.IMediator;
import unibuc.SkillLink.commands.clients.GetClientCommand;
import unibuc.SkillLink.commands.invoices.CreateInvoiceCommand;
import unibuc.SkillLink.commands.invoices.GetInvoiceCommand;
import unibuc.SkillLink.commands.providers.GetProviderCommand;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.models.Invoice;
import unibuc.SkillLink.models.Provider;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "testprovider", roles = {"PROVIDER"})

public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private IMediator mediator;

    @Test
    @WithMockUser
    void shouldReturnCreateInvoiceForm() throws Exception {
        UUID providerId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();

        Provider provider = new Provider();
        provider.setId(providerId);

        Client client = new Client();
        client.setId(clientId);

        Mockito.when(mediator.handle(Mockito.any(GetProviderCommand.class))).thenReturn(provider);
        Mockito.when(mediator.handle(Mockito.any(GetClientCommand.class))).thenReturn(client);

        mockMvc.perform(get("/invoice/create")
                        .param("providerId", providerId.toString())
                        .param("clientId", clientId.toString())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("invoice/create"))
                .andExpect(model().attributeExists("invoice"));
    }

    @Test
    @WithMockUser
    void shouldReturnInvoiceDetails() throws Exception {
        UUID invoiceId = UUID.randomUUID();
        UUID providerId = UUID.randomUUID();

        Provider provider = new Provider();
        provider.setId(providerId);

        Invoice invoice = new Invoice();
        invoice.setId(invoiceId);
        invoice.setProvider(provider);
        Mockito.when(mediator.handle(Mockito.any(GetInvoiceCommand.class))).thenReturn(invoice);

        mockMvc.perform(get("/invoice/" + invoiceId))
                .andExpect(status().isOk())
                .andExpect(view().name("invoice/show"))
                .andExpect(model().attributeExists("invoice"));
    }
}
