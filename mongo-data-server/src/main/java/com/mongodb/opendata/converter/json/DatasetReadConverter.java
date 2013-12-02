package com.mongodb.opendata.converter.json;

import org.springframework.core.convert.converter.Converter;

import com.google.dspl.model.Dataset;
import com.google.dspl.model.Info;
import com.google.dspl.model.Slice;
import com.google.dspl.model.Table;
import com.google.dspl.model.Table.Column;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


public class DatasetReadConverter implements Converter<DBObject, Dataset> {

    

    
    public DatasetReadConverter() {
        
    }
    
    @Override
    public Dataset convert(DBObject dbo) {
        Dataset dataset = new Dataset();
        
        dataset.setDatasetId((String)dbo.get("_id"));
        dataset.setName((String)dbo.get("name"));
        
        Info info = new Info();
        dataset.setInfo(info);
        info.setName((String)dbo.get("info.name"));
        info.setDescription((String)dbo.get("info.description"));
        info.setUrl((String)dbo.get("info.url"));
        

        
//        DBObject slices = new BasicDBObject();
//        dbo.put("slices", slices);
//        
//        for (Slice slice : dataset.getSlices()) {
//            DBObject sliceDbo = new BasicDBObject();
//            slices.put(slice.getName(), sliceDbo);
//            sliceDbo.put("table", slice.getTable());
//            sliceDbo.put("type", slice.getType());
//            
//            DBObject sliceInfo = new BasicDBObject();
//            sliceDbo.put("info", sliceInfo);
//            sliceInfo.put("name", slice.getInfo().getName());
//            sliceInfo.put("description", slice.getInfo().getDescription());
//            
//            
//            sliceDbo.put("dimensions", slice.getDimensions());
//            sliceDbo.put("metrics", slice.getMetrics());
//        }
//        
//        DBObject tablesDbo = new BasicDBObject();
//        dbo.put("tables", tablesDbo);
//        for (Table table : dataset.getTables()) {
//            DBObject tableDbo = new BasicDBObject();
//            tablesDbo.put("sources", table.getSources());
//            tablesDbo.put(table.getInfo().getName(), tableDbo);
//        
//            DBObject columnsDbo = new BasicDBObject();
//            tableDbo.put("columns", columnsDbo);
//            for (Column column : table.getColumns()) {
//                
//                
//                DBObject columnFields = new BasicDBObject();
//                columnsDbo.put(column.getId(), columnFields);
//                columnFields.put("name", column.getName());
//                columnFields.put("type", column.getType().name().toLowerCase());
//            }
//        }
        
        return dataset;
    }

}
