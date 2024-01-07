package io.github.emreblbl.dnsresolver.parser;

import io.github.emreblbl.dnsresolver.response.DnsResponse;
import io.github.emreblbl.dnsresolver.response.DnsResponseHeader;
import io.github.emreblbl.dnsresolver.response.ResourceRecord;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DnsResponseParser {

    public DnsResponse parseResponse(byte[] response) {
        ByteBuffer buffer = ByteBuffer.wrap(response);

        // Parse Header
        DnsResponse dnsResponse = new DnsResponse();
        DnsResponseHeader header = DnsResponseHeader.parseHeader(buffer);
        dnsResponse.setHeader(header);

        ResourceRecord record = ResourceRecord.parseRecord(buffer);
        List<ResourceRecord> recordList =Arrays.asList(record);



        return dnsResponse;
    }

    private void skipQuestions(ByteBuffer buffer, int qdCount) {
        for (int i = 0; i < qdCount; i++) {
            skipName(buffer);
            buffer.getShort(); // TYPE
            buffer.getShort(); // CLASS
        }
    }

    private ResourceRecord[] parseResourceRecords(ByteBuffer buffer, int count) {
        ResourceRecord[] records = new ResourceRecord[count];
        for (int i = 0; i < count; i++) {
            records[i] = parseResourceRecord(buffer);
        }
        return records;
    }

    private ResourceRecord parseResourceRecord(ByteBuffer buffer) {
        ResourceRecord record = new ResourceRecord();
        record.setName(parseName(buffer));
        record.setType(Short.toUnsignedInt(buffer.getShort()));
        record.setRrClass(Short.toUnsignedInt(buffer.getShort()));
        record.setTtl(Integer.toUnsignedLong(buffer.getInt()));
        int rdLength = Short.toUnsignedInt(buffer.getShort());
        byte[] rData = new byte[rdLength];
        buffer.get(rData);
        record.setRData(rData);
        return record;
    }

    private String parseName(ByteBuffer buffer) {
        // Implement DNS name parsing with compression handling
        // Placeholder
        return "";
    }

    private void skipName(ByteBuffer buffer) {
        // Implementation to skip over a compressed name
    }

    // Additional utility methods and classes like DnsResponse, ResourceRecord, etc.
    // ...
}
