package unibuc.SkillLink.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import unibuc.SkillLink.abstractions.Mediator;
import unibuc.SkillLink.commands.clients.GetClientCommand;
import unibuc.SkillLink.commands.invoices.GetInvoiceCommand;
import unibuc.SkillLink.commands.providers.GetProviderCommand;
import unibuc.SkillLink.models.Client;
import unibuc.SkillLink.models.Invoice;
import unibuc.SkillLink.models.Provider;

import java.util.HashSet;
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

    @MockitoBean
    private Mediator mediator;

    @Test
    @WithMockUser
    void shouldReturnCreateInvoiceForm() throws Exception {
        UUID providerId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();

        Provider provider = new Provider("Test", "Provider", "testprovider", 0, null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        provider.setId(providerId);

        Client client = new Client("Test", "Client", "testclient", null, new HashSet<>(), new HashSet<>());
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

        Provider provider = new Provider("Test", "Provider", "testprovider", 0, null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        provider.setId(providerId);

        Client mockClient = new Client("Test", "Client", "testclient", null, new HashSet<>(), new HashSet<>());
        mockClient.setId(UUID.randomUUID());

        Invoice invoice = new Invoice();
        invoice.setId(invoiceId);
        invoice.setProvider(provider);
        invoice.setClient(mockClient);
        Mockito.when(mediator.handle(Mockito.any(GetInvoiceCommand.class))).thenReturn(invoice);

        mockMvc.perform(get("/invoice/" + invoiceId))
                .andExpect(status().isOk())
                .andExpect(view().name("invoice/show"))
                .andExpect(model().attributeExists("invoice"));
    }
}

