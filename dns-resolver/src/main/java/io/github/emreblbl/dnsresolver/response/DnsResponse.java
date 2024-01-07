package io.github.emreblbl.dnsresolver.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DnsResponse {
    private DnsResponseHeader header;
    private List<ResourceRecord> answers;
    private List<ResourceRecord> authorities;
    private List<ResourceRecord> additionals;
}
