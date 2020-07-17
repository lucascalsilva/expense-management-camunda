package com.camunda.consulting.expense_mgt.bootstrap;

import com.camunda.consulting.expense_mgt.model.common.Approver;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.camunda.consulting.expense_mgt.util.ApplicationConstants.ERROR_DATALOADER;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final CrudRepository<Approver, Long> repository;

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData() {
        Optional<InputStream> is = Optional.of(DataLoader.class.getResourceAsStream("/data.json"));

        is.ifPresent(inputStream -> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                List<Approver> approverList = Arrays.asList(objectMapper.readValue(inputStream, Approver[].class));
                repository.saveAll(approverList);
            }
            catch(IOException e) {
                log.error(ERROR_DATALOADER, e);
            }
        });
    }


}