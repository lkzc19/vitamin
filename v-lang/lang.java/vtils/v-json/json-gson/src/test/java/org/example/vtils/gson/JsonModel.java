package org.example.vtils.gson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JsonModel {
    String field1;
    int field2;
    List<String> field3;
}