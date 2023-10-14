package org.sahan.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.sahan.dto.AuditConformationDTO;
import org.sahan.dto.BookDetailDTO;
import org.sahan.dto.FundsDTO;
import org.sahan.dto.TemplateResDTO;
import org.sahan.entity.AuditConformation;
import org.sahan.entity.BookDetail;
import org.sahan.repo.BookDetailRepo;
import org.sahan.repo.ReportGeneratorRepo;
import org.sahan.service.BookDetailService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookDetailServiceImpl implements BookDetailService {
    private final BookDetailRepo repo;
    private final ReportGeneratorRepo reportGeneratorRepo;

    private final ModelMapper mapper;

    public BookDetailServiceImpl(BookDetailRepo repo, ModelMapper mapper, ReportGeneratorRepo reportGeneratorRepo) {
        this.repo = repo;
        this.mapper = mapper;
        this.reportGeneratorRepo = reportGeneratorRepo;
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
    public void saveJson(AuditConformationDTO dto) {
        if (!reportGeneratorRepo.existsById(dto.getClientNumber())) {
            AuditConformation c = mapper.map(dto, AuditConformation.class);
            reportGeneratorRepo.save(c);
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
    public List<AuditConformationDTO> getAllClients() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<AuditConformationDTO> addressList = new ArrayList<>();

        try {
            // Parse the JSON string into a JsonNode
            JsonNode rootNode = objectMapper.readTree(jsonString);

            // Iterate through the JSON array
            for (JsonNode node : rootNode) {
                AuditConformationDTO addressDto = new AuditConformationDTO();
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
    public AuditConformationDTO searchClient(String clientNumber) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Parse the JSON string into a JsonNode
            JsonNode rootNode = objectMapper.readTree(jsonString);

            // Iterate through the JSON array
            for (JsonNode node : rootNode) {
                String nodeClientNumber = node.get("clientNumber").asText();
                if (nodeClientNumber.equals(clientNumber)) {
                    AuditConformationDTO addressDto = new AuditConformationDTO();
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

    @Override
    public String createTemplate(String clientNumber) {
        String htmlTemplate = """
                <!DOCTYPE html>
                <head>
                    <title>Confirmation of Unit Holding</title>
                </head>

                <body>
                <div id="audit_conformation">

                    <p>${date}</p><br>

                    <p><b>${toAddress}</b></p>

                    <p>Dear Sir/Madam,</p>
                    <p><u>${clientName} - ${clientNumber}</u></p>
                    <p>With reference to the request made by ${clientName}, we wish to confirm the following investments:
                        As at March 31, 2023, the total number of units held in the name of ${clientName}, in the Unit Capital
                        of the ${referenceName} Unit Trust Funds and the respective market values are as follows.</p>

                    <table border="1">
                        <tr>
                            <th>Fund Name</th>
                            <th>No. of Units</th>
                            <th>Unit Price (Rs.)</th>
                            <th>Market Value (Rs.)</th>
                            <th>Realized Gains/ Unrealized
                                Gains for the Period of 01.04.2022-31.03.2023 (Rs)
                            </th>
                        </tr>
                        <tr>
                            <td>${fundName}</td>
                            <td>${NoOfUnits}</td>
                            <td>${unitPrice}</td>
                            <td>${marketValue}</td>
                            <td>${gains}</td>
                        </tr>
                        <tr>
                            <td>Total</td>
                            <td>0.00</td>
                            <td>0.00</td>
                            <td>0.00</td>
                            <td><b> 14,097,580.94</b></td>
                        </tr>
                    </table>

                    <p>NDB Wealth Funds are licensed Unit Trust Funds Governed by Securities and Exchange Commission of Sri Lanka and
                        managed by NDB Wealth Management Limited.</p>

                    <p>We confirm that the market value of the fund is redeemable at any given time upon the request of the client.</p>

                    <p>Please note that the buying price (the price a client will receive when he sells a unit to cash out) per unit is
                        computed daily and subject to fluctuations.</p>

                    <p>This letter has been issued on the request of the above account holder without any liability on our part.</p>

                    <p>Yours faithfully.</p>
                    <p>NDB WEALTH MANAGEMENT LIMITED</p>

                    <p>Registrar</p>

                    <p>CC: SENKADAGALA FINANCE PLC NO 267, GALLE ROAD COLOMBO 03</p>
                </div>
                </body>""";

        AuditConformationDTO addressDto = searchClient(clientNumber);
        htmlTemplate = htmlTemplate.replace("${date}", addressDto.getDate());
        htmlTemplate = htmlTemplate.replace("${toAddress}", addressDto.getToAddress());
        htmlTemplate = htmlTemplate.replace("${clientName}", addressDto.getClientName());
        htmlTemplate = htmlTemplate.replace("${clientNumber}", addressDto.getClientNumber());
        htmlTemplate = htmlTemplate.replace("${referenceName}", addressDto.getReferenceName());
        htmlTemplate = htmlTemplate.replace("${fundName}", addressDto.getFunds().get(0).getFundName());
//        htmlTemplate = htmlTemplate.replace("${NoOfUnits}", addressDto.getFunds().get(0).getNoOfUnits());

        Document doc = Jsoup.parse(htmlTemplate);
        System.out.println(doc);
        return htmlTemplate;
    }

    @Override
    public String convertHtmlToBase64(String clientNumber) {
        createTemplate(clientNumber);

        // Convert the HTML string into a Jsoup Document
        Document doc = Jsoup.parse(createTemplate(clientNumber));
        String htmlString = doc.toString();

        // Print the HTML document
        System.out.println(doc);
        byte[] htmlBytes = htmlString.getBytes(StandardCharsets.UTF_8);

        return Base64.getEncoder().encodeToString(htmlBytes);
    }

}




