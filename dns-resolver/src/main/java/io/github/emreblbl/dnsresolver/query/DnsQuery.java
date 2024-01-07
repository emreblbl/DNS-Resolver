package io.github.emreblbl.dnsresolver.query;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

public class DnsQuery {
    private final String domain;
    private final int queryType;
    private final int queryClass;

    private Random random = new Random();

    public DnsQuery(String domain, int queryType, int queryClass) {
        this.domain = domain;
        this.queryType = queryType;
        this.queryClass = queryClass;
    }

    public byte[] generateQuery() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Header Section
        int id = random.nextInt(65536); // Random 16-bit number for ID
        outputStream.write((id >> 8) & 0xFF);
        outputStream.write(id & 0xFF);

        // Flags (Recursion Desired)
        outputStream.write(0x01);
        outputStream.write(0x00);

        // QDCOUNT, ANCOUNT, NSCOUNT, ARCOUNT
        outputStream.write(0x00); outputStream.write(0x01); // 1 question
        outputStream.write(0x00); outputStream.write(0x00); // 0 answers
        outputStream.write(0x00); outputStream.write(0x00); // 0 authority RRs
        outputStream.write(0x00); outputStream.write(0x00); // 0 additional RRs

        // Question Section
        for (String label : this.domain.split("\\.")) {
            outputStream.write(label.length());
            outputStream.write(label.getBytes());
        }
        outputStream.write(0x00); // End of domain name

        // QTYPE and QCLASS
        outputStream.write(0x00); outputStream.write(this.queryType);
        outputStream.write(0x00); outputStream.write(this.queryClass);

        return outputStream.toByteArray();
    }

    // Additional methods like getters and setters can be added as needed.
}

