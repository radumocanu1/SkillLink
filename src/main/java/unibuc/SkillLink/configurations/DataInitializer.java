package unibuc.SkillLink.configurations;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import unibuc.SkillLink.models.Provider;
import unibuc.SkillLink.repositories.ProvidersRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner
{
    @Autowired
    private ProvidersRepository providersRepository;

    @Override
    @Transactional
    public void run(String... args){
        long count = providersRepository.count();

        if (count == 0) {
            insertProvidersInitialData();
        }
    }
    protected void insertProvidersInitialData() {
        var csvFilePath = "src/main/resources/db-seed/providers.csv";

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            // skip first csv line
            csvReader.readNext();
            List<String[]> records = csvReader.readAll();
            for (String[] record : records) {
                String firstName = record[0];
                String lastName = record[1];

                Provider provider = new Provider();
                provider.setFirstName(firstName);
                provider.setLastName(lastName);
                providersRepository.save(provider);
            }
            System.out.println("Data from CSV inserted into Provider table.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }
    }
}