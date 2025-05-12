package org.example.vtils.fastexcel;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class DataListener extends AnalysisEventListener<VxMpModel> {

    @Override
    public void invoke(VxMpModel data, AnalysisContext analysisContext) {
        log.info("{}", JsonUtils.object2Json(data));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
