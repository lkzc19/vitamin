package org.example.vtils.httpclient4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HttpbinModel {
//    private Map<String, Object>  args;
//    private Map<String, Object> headers;
    private String origin;
    private String url;
}
