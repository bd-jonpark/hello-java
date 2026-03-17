package com.demo.app.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.io.ByteArrayInputStream;

// A05:2021 - XML External Entity (XXE)
// Attack: POST /api/xml with body:
// <?xml version="1.0"?><!DOCTYPE foo [<!ENTITY xxe SYSTEM "file:///etc/passwd">]><data>&xxe;</data>
@RestController
public class XxeController {

    @PostMapping(value = "/api/xml", consumes = MediaType.APPLICATION_XML_VALUE)
    public String parseXml(@RequestBody String xml) throws Exception {
        // VULNERABLE: XML parser allows external entities
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // Intentionally NOT disabling external entities
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));

        NodeList nodes = doc.getElementsByTagName("data");
        if (nodes.getLength() > 0) {
            return "Parsed: " + nodes.item(0).getTextContent();
        }
        return "No <data> element found";
    }
}
