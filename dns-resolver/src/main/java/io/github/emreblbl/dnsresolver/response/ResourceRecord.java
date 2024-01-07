package io.github.emreblbl.dnsresolver.response;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceRecord {
    private String name;
    private int type;
    private int rrClass;
    private long ttl;
    private int rdLength;
    private byte[] rData;



    public static ResourceRecord parseRecord(ByteBuffer buffer) {
        ResourceRecord record = new ResourceRecord();

        // Parse NAME
        record.name = parseName(buffer);

        // Parse TYPE
        record.type = Short.toUnsignedInt(buffer.getShort());

        // Parse CLASS
        record.rrClass = Short.toUnsignedInt(buffer.getShort());

        // Parse TTL
        record.ttl = Integer.toUnsignedLong(buffer.getInt());

        // Parse RDLENGTH
        record.rdLength = Short.toUnsignedInt(buffer.getShort());

        // Parse RDATA
        byte[] rData = new byte[record.rdLength];
        buffer.get(rData);
        record.rData = rData;

        return record;
    }

    private static String parseName(ByteBuffer buffer) {
        StringBuilder name = new StringBuilder();
        while (true) {
            int len = Byte.toUnsignedInt(buffer.get());
            if (len == 0) {
                // End of name
                break;
            }
            if ((len & 0xC0) == 0xC0) {
                // This is a pointer
                int offset = ((len & 0x3F) << 8) | Byte.toUnsignedInt(buffer.get());
                ByteBuffer duplicate = buffer.duplicate();
                duplicate.position(offset);
                name.append(parseName(duplicate));
                break;
            } else {
                // This is a label
                byte[] labelBytes = new byte[len];
                buffer.get(labelBytes);
                name.append(new String(labelBytes, StandardCharsets.UTF_8)).append('.');
            }
        }
        if (name.length() > 0) {
            name.setLength(name.length() - 1);  // Remove the last dot
        }
        return name.toString();
    }


    @Override
    public String toString() {
        return "ResourceRecord{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", rrClass=" + rrClass +
                ", ttl=" + ttl +
                ", rdLength=" + rdLength +
                ", rData=" + Arrays.toString(rData) +
                '}';
    }
}

