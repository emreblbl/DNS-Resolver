package io.github.emreblbl.dnsresolver;

import io.github.emreblbl.dnsresolver.client.DnsClient;
import io.github.emreblbl.dnsresolver.parser.DnsResponseParser;
import io.github.emreblbl.dnsresolver.query.DnsQuery;
import io.github.emreblbl.dnsresolver.response.DnsResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.util.Base64;

@SpringBootApplication
public class DnsResolverApplication {

    public static void main(String[] args) {
        SpringApplication.run(DnsResolverApplication.class, args);

        try {
            DnsClient dnsClient = new DnsClient();
            String domain = "hepsiburada.com"; // Domain to query
            InetAddress dnsServer = InetAddress.getByName("8.8.8.8"); // Google DNS
            int dnsPort = 53; // Standard DNS port

            // Prepare your DNS query here
            //IN (1): The Internet class. This is the typical class used for ordinary DNS queries.
            //A (1): Requests the IPv4 address associated with a domain name.
            DnsQuery dnsQuery = new DnsQuery(domain, 1, 1);// Construct the DNS query byte array
            byte[] query = dnsQuery.generateQuery();

            byte[] response = dnsClient.sendUdpMessage(query, dnsServer, dnsPort);

            // Creating a parser
            DnsResponseParser dnsResponseParser = new DnsResponseParser();
            DnsResponse dnsResponse =  dnsResponseParser.parseResponse(response);

            // Process the response as needed
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}