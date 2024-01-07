package io.github.emreblbl.dnsresolver.response;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

@Getter
@Setter
public class DnsResponseHeader {
    private int id; // 16 bits
    private boolean qr; // 1 bit
    private int opcode; // 4 bits
    private boolean aa; // 1 bit
    private boolean tc; // 1 bit
    private boolean rd; // 1 bit
    private boolean ra; // 1 bit
    private int rcode; // 4 bits
    private int qdCount; // 16 bits
    private int anCount; // 16 bits
    private int nsCount; // 16 bits
    private int arCount; // 16 bits


    public static DnsResponseHeader parseHeader(ByteBuffer buffer) {
        DnsResponseHeader header = new DnsResponseHeader();

        header.id = Short.toUnsignedInt(buffer.getShort());
        int flags = Short.toUnsignedInt(buffer.getShort());
        header.qr = (flags & 0x8000) != 0;
        header.opcode = (flags >> 11) & 0xF;
        header.aa = (flags & 0x0400) != 0;
        header.tc = (flags & 0x0200) != 0;
        header.rd = (flags & 0x0100) != 0;
        header.ra = (flags & 0x0080) != 0;
        header.rcode = flags & 0xF;

        header.qdCount = Short.toUnsignedInt(buffer.getShort());
        header.anCount = Short.toUnsignedInt(buffer.getShort());
        header.nsCount = Short.toUnsignedInt(buffer.getShort());
        header.arCount = Short.toUnsignedInt(buffer.getShort());

        return header;
    }
}

