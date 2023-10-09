package org.sahan.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.sahan.dto.AddressDTO;
import org.sahan.dto.BookDetailDTO;
import org.sahan.dto.FundsDTO;
import org.sahan.entity.BookDetail;
import org.sahan.repo.BookDetailRepo;
import org.sahan.service.BookDetailService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookDetailServiceImpl implements BookDetailService {
    private final BookDetailRepo repo;

    private final ModelMapper mapper;

    public BookDetailServiceImpl(BookDetailRepo repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    String jsonString = "[\n" +
            "  {\n" +
            "    \"date\": \"May 09, 2023\",\n" +
            "    \"toAddress\": \"Ms. Thamali Rodrigo\\nPartner, KPMG, P.O. Box 186,\\nNo:32, Sir Mohamed Macan Markar Mawatha,\\nColombo 03\",\n" +
            "    \"clientName\": \"SENKADAGALA FINANCE PLC\",\n" +
            "    \"clientNumber\": \"1024712-01\",\n" +
            "    \"funds\": [\n" +
            "      {\n" +
            "        \"fundName\": \"NDB WEALTH MONEY FUND\",\n" +
            "        \"NoOfUnits\": 0,\n" +
            "        \"unitPrice\": 0,\n" +
            "        \"marketValue\": 0,\n" +
            "        \"gains\": 0\n" +
            "      },\n" +
            "      {\n" +
            "        \"fundName\": \"NDB WEALTH MONEY FUND\",\n" +
            "        \"NoOfUnits\": 0,\n" +
            "        \"unitPrice\": 0,\n" +
            "        \"marketValue\": 0,\n" +
            "        \"gains\": 0\n" +
            "      }\n" +
            "    ],\n" +
            "    \"referenceName\": \"NDB WEALTH MANAGEMENT LIMITED\",\n" +
            "    \"ccAddress\": \"SENKADAGALA FINANCE PLC\\nNO 267, GALLE ROAD\\nCOLOMBO 03\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"date\": \"May 09, 2023\",\n" +
            "    \"toAddress\": \"Mr.Sahan Nimesha\\nPartner, KPMG, P.O. Box 186,\\nNo:32, Sir Mohamed Macan Markar Mawatha,\\nColombo 03\",\n" +
            "    \"clientName\": \"SAHAN NIMESHA PLC\",\n" +
            "    \"clientNumber\": \"199999\",\n" +
            "    \"funds\": [\n" +
            "      {\n" +
            "        \"fundName\": \"NDB WEALTH MONEY FUND\",\n" +
            "        \"NoOfUnits\": 0,\n" +
            "        \"unitPrice\": 0,\n" +
            "        \"marketValue\": 0,\n" +
            "        \"gains\": 0\n" +
            "      },\n" +
            "      {\n" +
            "        \"fundName\": \"NDB WEALTH MONEY FUND\",\n" +
            "        \"NoOfUnits\": 0,\n" +
            "        \"unitPrice\": 0,\n" +
            "        \"marketValue\": 0,\n" +
            "        \"gains\": 0\n" +
            "      }\n" +
            "    ],\n" +
            "    \"referenceName\": \"NDB WEALTH MANAGEMENT LIMITED\",\n" +
            "    \"ccAddress\": \"SENKADAGALA FINANCE PLC\\nNO 267, GALLE ROAD\\nCOLOMBO 03\"\n" +
            "  }\n" +
            "]";

    @Override
    public void saveBookDetail(BookDetailDTO dto) {
        if (!repo.existsById(dto.getIsbn())) {
            BookDetail c = mapper.map(dto, BookDetail.class);
            repo.save(c);
        } else {
            throw new RuntimeException("Book already exist..!");
        }
    }

    @Override
    public void updateBookDetail(BookDetailDTO dto) {
        if (repo.existsById(dto.getIsbn())) {
            BookDetail c = mapper.map(dto, BookDetail.class);
            repo.save(c);
        } else {
            throw new RuntimeException("No such book for update..!");
        }
    }

    @Override
    public BookDetailDTO searchBookDetail(Integer isbn) {
        Optional<BookDetail> bookDetail = repo.findById(isbn);
        if (bookDetail.isPresent()) {
            return mapper.map(bookDetail.get(), BookDetailDTO.class);
        } else {
            throw new RuntimeException("No book for id: " + isbn);
        }
    }

    @Override
    public void deleteBookDetail(Integer isbn) {
        if (repo.existsById(isbn)) {
            repo.deleteById(isbn);
        } else {
            throw new RuntimeException("No customer for delete ID: " + isbn);
        }
    }

    @Override
    public List<BookDetailDTO> getAllBookDetail() {
        List<BookDetail> all = repo.findAll();
        return mapper.map(all, new TypeToken<List<BookDetailDTO>>() {
        }.getType());
    }

    @Override
    public List<AddressDTO> getAllClients() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<AddressDTO> addressList = new ArrayList<>();

        try {
            // Parse the JSON string into a JsonNode
            JsonNode rootNode = objectMapper.readTree(jsonString);

            // Iterate through the JSON array
            for (JsonNode node : rootNode) {
                AddressDTO addressDto = new AddressDTO();
                addressDto.setDate(node.get("date").asText());
                addressDto.setClientName(node.get("clientName").asText());
                addressDto.setReferenceName(node.get("referenceName").asText());
                addressDto.setToAddress(node.get("toAddress").asText());
                addressDto.setCcAddress(node.get("ccAddress").asText());
                addressDto.setClientNumber(node.get("clientNumber").asText());

                List<FundsDTO> funds = new ArrayList<>();
                JsonNode fundsNode = node.get("funds");
                for (JsonNode fundNode : fundsNode) {
                    FundsDTO fundDTO = new FundsDTO();
                    fundDTO.setFundName(fundNode.get("fundName").asText());
                    fundDTO.setNoOfUnits(fundNode.get("NoOfUnits").asInt());
                    fundDTO.setUnitPrice(fundNode.get("unitPrice").asInt());
                    fundDTO.setMarketValue(fundNode.get("marketValue").asInt());
                    fundDTO.setGains(fundNode.get("gains").asInt());
                    funds.add(fundDTO);
                }
                addressDto.setFunds(funds);

                addressList.add(addressDto);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while parsing clients");
        }

        return addressList;
    }


    @Override
    public AddressDTO searchClient(String clientNumber) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parse the JSON string into a JsonNode
            JsonNode rootNode = objectMapper.readTree(jsonString);

            // Iterate through the JSON array
            for (JsonNode node : rootNode) {
                String nodeClientNumber = node.get("clientNumber").asText();
                if (nodeClientNumber.equals(clientNumber)) {
                    AddressDTO addressDto = new AddressDTO();
                    addressDto.setDate(node.get("date").asText());
                    addressDto.setClientName(node.get("clientName").asText());
                    addressDto.setClientNumber(node.get("clientNumber").asText());
                    addressDto.setReferenceName(node.get("referenceName").asText());
                    addressDto.setToAddress(node.get("toAddress").asText());
                    addressDto.setCcAddress(node.get("ccAddress").asText());

                    // Create and set the FundsDTO list
                    List<FundsDTO> fundsList = new ArrayList<>();
                    JsonNode fundsNode = node.get("funds");
                    for (JsonNode fundNode : fundsNode) {
                        FundsDTO fundsDTO = new FundsDTO();
                        fundsDTO.setFundName(fundNode.get("fundName").asText());
                        fundsDTO.setNoOfUnits(fundNode.get("NoOfUnits").asInt());
                        fundsDTO.setUnitPrice(fundNode.get("unitPrice").asInt());
                        fundsDTO.setMarketValue(fundNode.get("marketValue").asInt());
                        fundsDTO.setGains(fundNode.get("gains").asInt());
                        fundsList.add(fundsDTO);
                    }
                    addressDto.setFunds(fundsList);

                    return addressDto;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while parsing clients");
        }
        throw new RuntimeException("No customers found");
    }
}



