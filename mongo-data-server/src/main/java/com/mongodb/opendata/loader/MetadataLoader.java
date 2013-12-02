package com.mongodb.opendata.loader;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.dspl.model.DataType;
import com.google.dspl.model.Dataset;
import com.google.dspl.model.Info;
import com.google.dspl.model.Slice;
import com.google.dspl.model.Table;
import com.google.dspl.model.Table.Column;
import com.mongodb.opendata.repository.DatasetRepository;

@Service
public class MetadataLoader {
    
    @Autowired
    DatasetRepository datasetRepository;
    
    public void loadMetadata(Set<String> keySet, String name, String sliceName) {
        Dataset dataset = constructDataset(keySet, name, sliceName);
        datasetRepository.save(dataset);
    }
    
    private Dataset constructDataset(Set<String> keySet, String datasetName, String sliceName) {
        Dataset dataset = new Dataset();
        dataset.setDatasetId(datasetName);
        dataset.setName(datasetName);
        
        Info info = new Info();
        info.setName(datasetName);
        //info.setDescription("Test Dataset Description");
        //info.setUrl("http://test-dataset.com/test");
        dataset.setInfo(info);

        Info sliceInfo = new Info();
        sliceInfo.setName(sliceName);
        //sliceInfo.setDescription("Slice 1 description");
        Slice slice = new Slice();
        slice.setName(sliceName);
        slice.setType("table");
        slice.setTable(sliceName);
        slice.setInfo(sliceInfo);
        dataset.addSlice(slice);
        
        Table table = new Table();
        dataset.addTable(table);
        Info tableInfo = new Info();
        tableInfo.setName(sliceName);
        table.setInfo(tableInfo);
        table.addSource("FIXME");
        
        
        for (String key : keySet) {
            slice.addDimension(key);
            
            Column column = new Column();
            column.setId(key);
            column.setName(key);
            column.setType(DataType.STRING);
            table.addColumn(column);
        }
        
        
        return dataset;
      }

}
