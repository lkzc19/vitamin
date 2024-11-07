package org.example.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JacksonXmlRootElement(localName = "transaction")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TestXmlModel {
    private String mobile;
    private String smscontent;
}
